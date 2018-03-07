package common.DataModels;

import java.io.Serializable;
import java.util.List;

import common.DataModels.GameData.ClientGameData;

public class StartGamePacket implements Serializable{

    private List<Object> initialDestinationCards;
    private ClientGameData clientGameData;

    public StartGamePacket(List<Object> initialDestinationCards, List<Object> initialTrainCards, ClientGameData clientGameData)
    {
        this.initialDestinationCards = initialDestinationCards;
        this.clientGameData = clientGameData;
    }

    public List<Object> getInitialDestinationCards() {
        return initialDestinationCards;
    }

    public ClientGameData getClientGameData() {
        return clientGameData;
    }

    public Username getUser()
    {
        return clientGameData.getPlayer().getUser().getUsername();
    }
}
