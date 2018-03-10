package cs340.TicketClient.CardFragments;

import java.util.ArrayList;

import common.DataModels.HandDestinationCards;
import common.DataModels.TrainCard;

/**
 * Created by jhens on 3/9/2018.
 */

public interface IHandFragmentPresenter {

    public ArrayList<TrainCard> getPlayerTrainCards();

    public HandDestinationCards getPlayerDestinationCards();
}
