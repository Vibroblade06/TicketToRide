package common;

import java.util.ArrayList;
import java.util.List;

import common.DataModels.*;

public interface IServer
{
	/**
	 * checks the database for the username ans password and logs in if the username and password match one on the server
	 * @param username username of player attempting to login
	 * @param password password of player attempting to login
	 * @return Signal containing error or ok message and User object
	 */
	Signal login(String username, String password);

	/**
	 * registers a new player with the given credentials, rejects invalid or preexiting credentials
	 * @param username username of new player (primary key)
	 * @param password password of new player
	 * @param displayName name to be displayed for the public
	 * @return Signal containing error or ok message and User object
	 */
	Signal register(String username, String password, String displayName);

	/**
	 * adds game to the database
	 * @param gameName name of the game
	 * @param user what user owns the gain
	 * @return Signal containing error or ok message
	 */
	Signal addGame(String gameName, User user);

	/**
	 * joins a game in the lobby
	 * @param user user who is joining game
	 * @param id game the user wants to join
	 * @return Signal containing error or ok message
	 */
	Signal joinGame(User user, GameID id);

	/**
	 * starts the game
	 * @param id game to start
	 * @return Signal containing error or ok message
	 */
	Signal startGame(GameID id);

	/**
	 * Accesses game info from the serve
	 * @return Signal containing error or ok message and gameInfo object
	 */
	Signal getAvailableGameInfo();

	/**
	 * Debug method for pre-populating a server with data
	 */
	Signal populate();

	/**
	 * returns information on what destination cards the player picked and which to return to the deck.
	 * @param pickedCards list of destination cards the player picked (must be at least 2)
	 * @param returnCards list of destination cards the player didn't pick (can be 0)
	 * @return Signal with OK or ERROR status
	 */
	Signal returnDestinationCards(GameID id, Username name, List<DestinationCard> pickedCards, List<DestinationCard> returnCards);

	Signal send(ChatItem item);

	Signal drawFaceUp(Username user, TrainCard card);

	Signal drawDeck(Username user);

	Signal drawDestinationCards(Username user);

	Signal claimEdge(Username user, Edge edge);

}
