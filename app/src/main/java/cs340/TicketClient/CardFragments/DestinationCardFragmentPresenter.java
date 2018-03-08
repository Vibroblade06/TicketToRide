package cs340.TicketClient.CardFragments;

import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;

import common.DataModels.GameData.SendCardsRequest;
import common.DataModels.GameID;
import common.DataModels.HandDestinationCards;
import common.DataModels.Signal;
import common.DataModels.SignalType;
import common.DataModels.Username;
import cs340.TicketClient.Communicator.ServerProxy;
import cs340.TicketClient.Game.GameModel;

public class DestinationCardFragmentPresenter {

    private GameModel model;
    private DestinationCardFragment fragment;

    DestinationCardFragmentPresenter(DestinationCardFragment fragment)
    {
    	model = GameModel.getInstance();
        this.fragment = fragment;
    }


    HandDestinationCards getDCards()
    {
        HandDestinationCards cards = null;
        if (GameModel.getInstance().getInitialDCards() != null) {
            cards = model.getInitialDCards();
            model.clearDCards();
        }
        return cards;
    }

    void confirmDestinationCards()
    {
        HandDestinationCards selected = new HandDestinationCards();
        HandDestinationCards returned = new HandDestinationCards();
        if(fragment.card1Check.isChecked())
            selected.add(fragment.dCards.get(0));
        else
            returned.add(fragment.dCards.get(0));
        if(fragment.card2Check.isChecked())
            selected.add(fragment.dCards.get(1));
        else
            returned.add(fragment.dCards.get(1));
        if(fragment.card3Check.isChecked())
            selected.add(fragment.dCards.get(2));
        else
            returned.add(fragment.dCards.get(2));
        GameID id = model.getGameID();
        Username user = model.getPlayer().getUser().getUsername();
        GameModel.getInstance().getPlayer().getDestinationCards().addAll(selected);
        SendCardsRequest request= new SendCardsRequest(id, user, selected, returned);
        SendCardsTask task = new SendCardsTask(this);
        task.execute(request);
    }

    private static class SendCardsTask extends AsyncTask<SendCardsRequest,Integer ,Signal>
    {
    	DestinationCardFragmentPresenter presenter;

    	SendCardsTask(DestinationCardFragmentPresenter presenter) { this.presenter = presenter; }

        @Override
        protected Signal doInBackground(SendCardsRequest[] obj) {
            SendCardsRequest request = obj[0];
            return ServerProxy.getInstance().returnDestinationCards(request.getId(), request.getUser(), request.getSelected(), request.getReturned());
        }

        @Override
        protected void onPostExecute(Signal signal) {
            super.onPostExecute(signal);
            if (signal.getSignalType() == SignalType.OK)
			{
				FragmentManager fm = presenter.fragment.getActivity().getSupportFragmentManager();
				fm.popBackStack();
			}
        }
    }
}
