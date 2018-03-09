package common.DataModels.GameData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import common.DataModels.ChatItem;
import common.DataModels.DestinationCard;
import common.DataModels.Edge;
import common.DataModels.EdgeGraph;
import common.DataModels.GameID;
import common.DataModels.HistoryItem;
import common.DataModels.TrainCard;
import common.DataModels.User;
import common.DataModels.Username;

public class ClientGameData implements IGameData, Serializable
{

    private GameID id;
    private Player player;
	private List<Opponent> opponents;
	private EdgeGraph gameboard;
	private List<TrainCard> faceUp;
	private List<HistoryItem> history;
	private List<ChatItem> chat;
	private TurnQueue turnQueue;

    public ClientGameData(ServerGameData game, Username username){
        this.id = game.getId();
        this.opponents = new ArrayList<>();
        for(Player p: game.getPlayers()){
        	if (p.getUser().getUsername().equals(username))
			{
				this.player = p;
			}
			else
			{
				opponents.add(new Opponent(p));
			}
        }
        this.gameboard = game.getGameBoard();
        this.faceUp = game.getFaceUpCards();
        this.history = game.getHistory();
        this.chat = new ArrayList<>();
        this.turnQueue = game.getTurnQueue();
    }

    public List<TrainCard> getFaceUp() { return faceUp; }

    public GameID getId() { return id; }

    public List<Opponent> getOpponents() { return opponents; }

    public List<ChatItem> getChat() { return chat; }

    public List<HistoryItem> getHistory() { return history; }

    public Player getPlayer() { return player; }

    public EdgeGraph getGameboard() { return gameboard; }

    public void edgeClaimed(Edge edge)
	{
		// TODO: implement
	}

    private Opponent getOpponent(Username username)
	{
    	for(Opponent o: opponents)
    	{
            if(o.getUsername().equals(username))
                return o;
        }
        return null; // NOTE: may return null, thus throwing NullPointerException
    }

    public Username whoseTurn() { return turnQueue.peek(); }

	public TurnQueue getTurnQueue() { return turnQueue; }

    @Override
    public void deckDraw(Username username, List<TrainCard> drawn) {
        Opponent o = getOpponent(username);
        if(o != null)
            o.addHandCard(drawn.size());
    }

    @Override
    public void faceUpDraw(Username username, List<TrainCard> drawn, List<TrainCard> replacements){
    ArrayList<Integer> toRemove = new ArrayList<>();
        for(int i=0; i<drawn.size(); i++){
            for(int j=0; j<faceUp.size(); j++){
                if(faceUp.get(j).getType() == drawn.get(i).getType()){
                    faceUp.remove(j);
                }
            }
        }
    }

    @Override
    public void destinationDraw(Username username, List<DestinationCard> drawn) {
        getOpponent(username).addDestinationCards(drawn.size());
    }

    @Override
    public void addHistoryItem(HistoryItem event) { this.history.add(event); }

    @Override
    public void addChatMessage(ChatItem m) { this.chat.add(m); }

}
