package common.DataModels.GameData.Decks;

import java.util.List;

import common.DataModels.DestinationCard;

public interface IDestinationCardDeck
{
  List<DestinationCard> draw();
  void shuffle();

}