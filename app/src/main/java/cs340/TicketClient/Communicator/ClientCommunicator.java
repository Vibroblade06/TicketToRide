package cs340.TicketClient.Communicator;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import common.DataModels.Game;
import common.DataModels.GameInfo;
import common.DataModels.Signal;
import common.DataModels.SignalType;

/**
 * Created by Kavika F.
 */

public class ClientCommunicator
{
	private static ClientCommunicator SINGLETON = null;
	public static ClientCommunicator getSingleton()
	{
		if(SINGLETON == null)
		{
			SINGLETON = new ClientCommunicator();
		}
		return SINGLETON;
	}

	private ConnectionToServer server;
	private Socket socket;
	private LinkedBlockingQueue<Object> messages;
	private Signal signalFromServer = null;
	private static String SERVER_HOST = "localhost";

	public static void setSERVER_HOST(String ip)
	{
		SERVER_HOST = ip;
	}

	private Signal getSignalFromServer() { return signalFromServer; }

	private void setSignalFromServer(Signal signalFromServer) { this.signalFromServer = signalFromServer; }

	/**
	 * Initialize a ClientCommunicator object. Create a socket to communicate with the server.
	 * This socket will stay open throughout the program; therefore, the ClientCommunicator object
	 * must stay relevant throughout a client's entire game.
	 */
	private ClientCommunicator()
	{
		try
		{
			socket = new Socket(SERVER_HOST, 8080);
			messages = new LinkedBlockingQueue<>();
			server = new ConnectionToServer(socket);

			Thread receiver = new Thread()
			{
				public void run()
				{
					while(true)
					{
						try
						{
							Signal signal = (Signal) messages.take();

							if (signal.getSignalType() == SignalType.UPDATE &&
									signal.getObject() instanceof List)
							{
								// Hope that the List of type UPDATE has GameInfo Objects
								@SuppressWarnings("unchecked")
								List<GameInfo> infoList = (List<GameInfo>) signal.getObject();
								ClientFacade.getSingleton().updateGameList(infoList);
							}else if (signal.getSignalType() == SignalType.START_GAME){
								ClientFacade c = new ClientFacade();
								c.startGame(((Game)signal.getObject()).getId());
							}
							else // Should be a signal
							{
								setSignalFromServer(signal);
							}
						}
						catch (InterruptedException e)
						{
							System.out.println("InterruptedException when receiving data from server: " + e);
						}
					}
				}
			};

			receiver.setDaemon(true);
			receiver.start();
		}
		catch (IOException e)
		{
			System.out.println("IOException in ClientCommunicator: " + e);
			e.printStackTrace();
		}
	}

	private class ConnectionToServer
	{
		private Socket socket;
		private ObjectInputStream inputStream;
		private ObjectOutputStream outputStream;

		private ConnectionToServer(Socket socket) throws IOException
		{
			this.socket = socket;
			inputStream = new ObjectInputStream(this.socket.getInputStream());
			outputStream = new ObjectOutputStream(this.socket.getOutputStream());

			Thread read = new Thread()
			{
				public void run()
				{
					while(true)
					{
						try
						{
							Object object = inputStream.readObject();
							messages.put(object);
						}
						catch (IOException e)
						{
							System.out.println("IOException in read Thread: " + e);
							e.printStackTrace();
							// if SocketException, like a problem with Server, stop listening
							if (e instanceof SocketException || e instanceof EOFException)
							{
								closeSocket();
								break;
							}
						}
						catch (ClassNotFoundException e)
						{
							System.out.println("ClassNotFoundException in read Thread: " + e);
							e.printStackTrace();
						}
						catch (InterruptedException e)
						{
							System.out.println("InterruptedException in read Thread: " + e);
							e.printStackTrace();
						}
					}
					System.out.println("You have disconnected from the Server!");
				}
			};

			read.setDaemon(true);
			read.start();
		}

		private void write(Object object)
		{
			try
			{
				outputStream.writeObject(object);
			}
			catch (IOException e)
			{
				System.out.println("IOException when writing object to Server: " + e);
			}
		}
	}

	/**
	 * Sends an object from the client to the server.
	 * @param object The object to be sent to the server.
	 * @return Return a result object from the server. May or may not be an error object.
	 * @throws IOException Can throw an IOException if there is an issue with the input/output streams.
	 */
	public Object send(Object object) throws IOException
	{
		Signal result = null;
		server.write(object);
		while (result == null)
		{
			result = getSignalFromServer();
		}
		setSignalFromServer(null);
		return result;

	}

	private void closeSocket()
	{
		try
		{
			socket.close();
		}
		catch (IOException e)
		{
			System.out.println("Error closing clientSocket" + e);
			e.printStackTrace();
		}
	}
}
