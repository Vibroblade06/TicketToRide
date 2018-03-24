package common.player_info;

import java.io.Serializable;
import java.util.*;

import common.cards.HandDestinationCards;
import common.cards.HandTrainCards;
import common.cards.TrainCard;
import common.cards.TrainColor;
import common.map.City;
import common.map.Edge;
import common.map.EdgeGraph;
import common.player_info.turn_state.ITurnState;
import common.player_info.turn_state.InitialDestinationCardDraw;

public class Player implements Serializable
{
    private User user;
    private HandTrainCards hand;
    private HandDestinationCards destinations;
    private PlayerColor color;
    private int score;
    private EdgeGraph claimedEdges;
    private TrainPieces pieces;
    private ITurnState turnState;

    public Player(User user, PlayerColor color)
    {
        this.user = user;
        this.hand = new HandTrainCards();
        this.destinations = new HandDestinationCards();
        this.color = color;
        this.score = 0;
        this.claimedEdges = new EdgeGraph();
        this.turnState = new InitialDestinationCardDraw();
    }

    public String getName() { return this.user.getStringUserName(); }

    public User getUser() { return this.user; }

    public HandTrainCards getHand()
    {
        return this.hand;
    }

    public void drewTrainCards(HandTrainCards cards)
    {
        this.hand.addAll(cards);
    }

    public void drewFaceUpCard(TrainCard trainCard) {  getTurnState().drawFaceUp(this, trainCard); }

    public void claimedEdge(Edge edge)
    {
        if (canClaimEdge(edge))
        {
            claimedEdges.addEdge(edge);
            ArrayList<TrainCard> toRemove = new ArrayList<>();
            for (int i = 0; i < edge.getLength(); i++)
            {
                for (TrainCard t : hand.getTrainCards())
                {
                    if (t.getType() == edge.getColor())
                    {
                        toRemove.add(t);
                        break;
                    }
                }
            }
            hand.getTrainCards().removeAll(toRemove);
        }
        //TODO if the newly claimed edge completed a destination card add points and remove the card
    }

	public void setTurnState(ITurnState turnState) { this.turnState = turnState; }

	public ITurnState getTurnState() { return turnState; }

    private boolean canClaimEdge(Edge edge)
    {
        return numCardsOfColor(edge.getColor()) >= edge.getLength();
    }

    private int numCardsOfColor(TrainColor color)
    {
        int sum = 0;
        for (TrainCard t : hand.getTrainCards())
        {
            if (t.getType() == color)
            {
                sum++;
            }
        }
        return sum;
    }

    public HandDestinationCards getDestinationCards() { return this.destinations; }

    public boolean drewDestinationCards(HandDestinationCards cards, boolean isMyTurn)
    {
        return getTurnState().drawDestinationCards(this, cards, isMyTurn);
    }

    public PlayerColor getColor()
    {
        return this.color;
    }

    public int getScore()
    {
        return this.score;
    }

    public void addPoints(int points)
    {
        this.score += points;
    }

    public EdgeGraph getClaimedEdges()
    {
        return this.claimedEdges;
    }

    public int getNumberTrainCards()
    {
        return hand.size();
    }

    public int getTrainPiecesRemaining()
    {
        return pieces.getNumTrainPieces();
    }

    public int computeLongestPath()
    {
        Set<Edge> unusedEdges = claimedEdges.getAllEdges();
        int longestPath = 0;
        List<City> endCities = Edge.findCitiesWithNumEdges(unusedEdges, 1);
        Set<Edge> usedEdges = new HashSet<>();
        for(City city : endCities)
        {
            Edge edge = Edge.findEdgesWithCity(unusedEdges, city).get(0);
            unusedEdges.remove(edge);
            if(!usedEdges.contains(edge))
            {
                usedEdges.add(edge);
            }
            int edgeLongestPath = edge.computeLongestPathOneDirection(unusedEdges, usedEdges);
            longestPath = longestPath < edgeLongestPath ? edgeLongestPath : longestPath;
        }
        unusedEdges.removeAll(usedEdges);
        //check if there are any circular paths that we missed and therefore need to check.
        if(unusedEdges.size() > 0)
        {
            for (Edge edge : unusedEdges)
            {
                Set<Edge> newEdges = new HashSet<>();
                newEdges.addAll(unusedEdges);
                newEdges.remove(edge);
                int newLongestPath = edge.computeLongestPathTwoDirections(newEdges);
                if (newLongestPath > longestPath)
                {
                    longestPath = newLongestPath;
                }
        }
        }
        return longestPath;
    }

}
