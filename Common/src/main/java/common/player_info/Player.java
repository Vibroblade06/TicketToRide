package common.player_info;

import java.io.Serializable;
import java.util.*;

import common.cards.HandDestinationCards;
import common.cards.HandTrainCards;
import common.cards.TrainCard;
import common.cards.TrainColor;
import common.map.Edge;
import common.map.EdgeGraph;

public class Player implements Serializable
{
    private User user;
    private HandTrainCards hand;
    private HandDestinationCards destinations;
    private PlayerColor color;
    private int score;
    private EdgeGraph claimedEdges;
    private TrainPieces pieces;

    public Player(User user, PlayerColor color)
    {
        this.user = user;
        this.hand = new HandTrainCards();
        this.destinations = new HandDestinationCards();
        this.color = color;
        this.score = 0;
        this.claimedEdges = new EdgeGraph();
    }

    public String getName()
    {
        return this.user.getStringUserName();
    }

    public User getUser()
    {
        return this.user;
    }

    public HandTrainCards getHand()
    {
        return this.hand;
    }

    public void drewTrainCards(HandTrainCards cards)
    {
        this.hand.addAll(cards);
    }

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

    public HandDestinationCards getDestinationCards()
    {
        return this.destinations;
    }

    public void drewDestinationCards(HandDestinationCards cards)
    {
        this.destinations.addAll(cards);
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
        for(Edge edge : unusedEdges)
        {
            unusedEdges.remove(edge);
            int newLongestPath = edge.computeLongestPath(unusedEdges);
            if(newLongestPath > longestPath)
            {
                longestPath = newLongestPath;
            }
        }
        return longestPath;
    }

}