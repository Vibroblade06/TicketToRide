package cs340.TicketClient.ASyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import common.DataModels.*;
import cs340.TicketClient.Communicator.ServerProxy;
import common.CommandParams;

public class JoinGameTask extends AsyncTask<Object, Void, Signal>
{
  private Context context;
  public JoinGameTask(Context context)
  {
    this.context = context;
  }

  @Override
  protected Signal doInBackground(Object... objects)
  {
    Player player = (Player) objects[0];
    GameID id = (GameID) objects[1];
    return ServerProxy.getInstance().joinGame(player, id);
  }

  @Override
  protected void onPostExecute(Signal signal)
  {
    if(signal.getSignalType() == SignalType.ERROR)
    {
      Toast.makeText(context, (String)signal.getObject(), Toast.LENGTH_SHORT).show();
    }
  }
}
