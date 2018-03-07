package cs340.TicketClient.Game;

import java.util.ArrayList;
import java.util.List;

import common.DataModels.ChatItem;
import common.DataModels.DestinationCard;
import common.DataModels.EdgeGraph;
import common.DataModels.GameData.ClientGameData;
import common.DataModels.GameData.Opponent;
import common.DataModels.HistoryItem;
import common.DataModels.TrainCard;

public class GameModel
{
	ClientGameData gameData;
	GameModel singleton;
	ArrayList<DestinationCard> initialDCards;

	public GameModel getInstance()
	{
		if (singleton == null)
			singleton = new GameModel();
		return singleton;
	}

	private GameModel()
	{

	}

	public void setGameData(ClientGameData gameData) {
		this.gameData = gameData;
	}

	public ClientGameData getGameData() {
		return gameData;
	}

	public void setInitialDCards(ArrayList<DestinationCard> initialDCards) {
		this.initialDCards = initialDCards;
	}

	public ArrayList<DestinationCard> getInitialDCards() {
		return initialDCards;
	}

	public void clearDCards()
	{
		initialDCards = null;
	}
}
