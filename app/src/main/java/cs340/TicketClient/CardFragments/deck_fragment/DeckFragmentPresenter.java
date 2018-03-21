package cs340.TicketClient.CardFragments.deck_fragment;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

import common.DataModels.DrawDeckRequest;
import common.DataModels.DrawFaceUpRequest;
import common.DataModels.GameID;
import common.DataModels.Signal;
import common.DataModels.TrainCard;
import cs340.TicketClient.Communicator.ServerProxy;
import cs340.TicketClient.Game.GameModel;
import cs340.TicketClient.R;

import static common.DataModels.SignalType.OK;

/**
 * Created by jhens on 3/1/2018.
 */

public class DeckFragmentPresenter implements IDeckFragmentPresenter
{

    private DeckFragment fragment;
    private GameModel model;

    public DeckFragmentPresenter(DeckFragment fragment)
    {
        this.fragment = fragment;
        this.model = null;
    }

    @Override
    public ArrayList<Integer> getFaceUpCards()
    {
        ArrayList<TrainCard> faceUp =
                (ArrayList<TrainCard>) GameModel.getInstance().getGameData().getFaceUp();
        ArrayList<Integer> graphics = new ArrayList<>();
        for (TrainCard t : faceUp)
        {
            graphics.add(getTrainDrawable(t));
        }
        return graphics;
    }

    @Override
    public void DrawDeck()
    {
        DrawDeckRequest request = new DrawDeckRequest(GameModel.getInstance().getGameID(), GameModel.getInstance().getPlayer().getUser().getUsername());
        DrawDeckTask task = new DrawDeckTask();
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request);

        //change turn info
    }

    Integer getTrainDrawable(TrainCard card)
    {
        switch (card.getType())
        {
            case RED:
                return R.drawable.traincard_red;
            case BLUE:
                return R.drawable.traincard_blue;
            case GRAY:
                return R.drawable.traincard_locomotive;
            case PINK:
                return R.drawable.traincard_pink;
            case BLACK:
                return R.drawable.traincard_black;
            case GREEN:
                return R.drawable.traincard_green;
            case WHITE:
                return R.drawable.traincard_white;
            case ORANGE:
                return R.drawable.traincard_orange;
            case YELLOW:
                return R.drawable.traincard_yellow;
            default:
                System.out.println("No TrainCardImage for that Color");
                return 0;
        }
    }

    @Override
    public void DrawFaceUp(int cardNumber)
    {
        ArrayList<TrainCard> currFaceUp = (ArrayList<TrainCard>) GameModel.getInstance().getGameData().getFaceUp();
        TrainCard pickedCard = currFaceUp.get(cardNumber);
        GameModel.getInstance().getPlayer().getHand().add(pickedCard);
        DrawFaceUpRequest request = new DrawFaceUpRequest(GameModel.getInstance().getGameID(), GameModel.getInstance().getPlayer().getUser().getUsername(), cardNumber);
        DrawFaceUpTask task = new DrawFaceUpTask(cardNumber, this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request);

        //change turn info
    }

    public void replaceTrainCard(int index, TrainCard card)
    {
        Integer drawable = getTrainDrawable(card);
        switch (index)
        {
            case 0:
                fragment.tCard1.setImageResource(drawable);
                break;
            case 1:
                fragment.tCard2.setImageResource(drawable);
                break;
            case 2:
                fragment.tCard3.setImageResource(drawable);
                break;
            case 3:
                fragment.tCard4.setImageResource(drawable);
                break;
            case 4:
                fragment.tCard5.setImageResource(drawable);
                break;
            default:
                Log.d("Error", "replaceTrainCard: Bad Index");
                break;
        }
    }



    class DrawFaceUpTask extends AsyncTask<DrawFaceUpRequest, Integer, Signal>
    {
        int index;
        DeckFragmentPresenter presenter;

        public DrawFaceUpTask(int index, DeckFragmentPresenter presenter)
        {
         this.index = index;
         this.presenter = presenter;
        }
        @Override
        protected Signal doInBackground(DrawFaceUpRequest... drawFaceUpRequests)
        {
            return ServerProxy.getInstance().drawFaceUp(drawFaceUpRequests[0].getId(), drawFaceUpRequests[0].getUsername(), drawFaceUpRequests[0].getIndex());
        }

        @Override
        protected void onPostExecute(Signal response)
        {
            super.onPostExecute(response);
            if (response.getSignalType() == OK)
            {
                TrainCard card = (TrainCard)response.getObject();
                GameModel.getInstance().replaceFaceUp(index, card);
                presenter.replaceTrainCard(index, card);

            } else
            {
                Log.d("ERROR","onPostExecute: Error signal on draw face up");
            }
        }
    }

    class DrawDeckTask extends AsyncTask<DrawDeckRequest, Integer, Signal>
    {
        @Override
        protected Signal doInBackground(DrawDeckRequest... drawDeckRequests)
        {
            return ServerProxy.getInstance().drawDeck(drawDeckRequests[0].getId(), drawDeckRequests[0].getUsername());
        }

        @Override
        protected void onPostExecute(Signal signal)
        {
            super.onPostExecute(signal);
            if (signal.getSignalType() == OK)
            {
                TrainCard card  = (TrainCard)signal.getObject();
                GameModel.getInstance().addTrainCard(card.getType());

            } else
            {
                Log.d("ERROR","onPostExecute: Error Signal on draw face up");
            }
        }
    }
}
