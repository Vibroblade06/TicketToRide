package cs340.TicketClient.Lobby;

import java.util.List;

import common.DataModels.*;

public interface ILobbyPresenter
{
  /**
   * A method that queries the model to see if a game has enough players to start
   * @pre The game id must be for a valid game
   * @post will return true or false
   * @param id The game to be queried
   * @return true if the game has between 2 and 5 players, inclusive and false otherwise
   */
  boolean canStartGame(GameID id);

  /**
   * Gets a list of all the games in the model
   * @pre None
   * @post The values of all the games will be returned
   * @return A list of all games in the model, if the list is empty it will return an empty list
   */
  List<GameInfo> getAllGames();

  /**
   * Gets a list of all the games that have the search term in thier names
   * @pre None
   * @post The model will be searched through and any matching games will be put into the return list
   * @param search The phrase to be searched for
   * @return a List of all games with the search term in their names
   */
  List<GameInfo> searchGames(String search);

  void addGame(String newgame);
  void joinGame(GameID id);
  void startGame(GameID id);

}
