package common.DataModels.GameData;

/**
 * Created by Vibro on 2/28/2018.
 */

public class ClientGameData /*implements IGameData, Serializable*/{
    /*
    GameID id;
    List<Opponent> opponents;
    EdgeGraph gameboard;
    List<TrainCard> faceUp;
    History history;
    Chat chat;

    public ClientGameData(ServerGameData game){
        this.id = ServerGameData.getID();
        this.opponents = new ArrayList<Opponent>();
        for(User p: game.getUsers()){
            opponents.add(new Opponent(p);
        }
        this.gameboard = game.getGameboard();
        this.faceUp = game.getFaceUp();
        this.history = game.getHistory();
        this.chat = new Chat();
    }

    private Opponent getOpponent(String username){
        for(Opponent o: opponents){
            if(o.getUsername().equals(username))
                return o;
        }
        return null;
    }

    @Override
    public void deckDraw(String username, List<TrainCard> drawn) {
        Opponent o = getOpponent(username);
        if(o != null)
            o.addTrainCards(drawn.size();
    }

    @Override
    public void faceUpDraw(String username, List<TrainCard> drawn, List<TrainCard> replacements){
    ArrayList<int> toRemove = new ArrayList<int>();
        for(int i=0; i<drawn.size(); i++){
            for(int j=0; j<faceUp.size(); j++){
                if(faceUp.get(j).getColor() == drawn.get(i).getColor()){
                    faceUp.remove(j)
                    getOpponent(username).addTrainCard(1);
                    faceUp.add(replacements.get(i);
                }
            }
        }
    }

    @Override
    public void destinationDraw(Sting username, List<DestinationCard> drawn){
        getOpponent(username).addDestinationCards(drawn.size());
    }

    @Override
    public void addHistoryItem(GameEvent event){
        this.history.add(event);
    }

    @Override
    public void addChatMessage(Message m){
        this.chat.add(m);
    }
    */
}