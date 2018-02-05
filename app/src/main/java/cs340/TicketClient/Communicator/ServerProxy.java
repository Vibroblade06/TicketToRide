package cs340.TicketClient.Communicator;

import cs340.TicketClient.common.Command;
import cs340.TicketClient.common.IServer;
import cs340.TicketClient.common.Signal;

/**
 * Created by Ben_D on 1/29/2018.
 */

public class ServerProxy implements IServer
{
    private static ServerProxy singleton;

    public static ServerProxy getInstance()
    {
        if (singleton == null)
            singleton = new ServerProxy();
        return singleton;
    }

    /**
     *
     * @param username username of player trying to login
     * @param password password of player trying to login
     * @return success or fail login Signal
     */
    @Override
    public Signal login(String username, String password) {

        String[] parameterTypes = {"String", "String"};
        Object[] parameters = {username, password};
        Command loginCommand = new Command("login", parameterTypes, parameters);
        //send to server
        Signal response = null;
        return response;
    }

    /**
     *
     * @param username username of player trying to register (must be unique)
     * @param password password of player trying to register
     * @param screenName screenname of player trying to register
     * @return success or fail register Signal
     */
    @Override
    public Signal register(String username, String password, String screenName) {
        String[] parameterTypes = {"String", "String", "String"};
        Object[] parameters = {username, password, screenName};
        Command registerCommand = new Command("register", parameterTypes, parameters);
        //send to server
        Signal response = null;
        return response;
    }

    @Override
    public void startGame() {

    }
}
