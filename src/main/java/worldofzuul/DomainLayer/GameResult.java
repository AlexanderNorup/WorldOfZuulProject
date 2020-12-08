package worldofzuul.DomainLayer;

import java.util.ArrayList;

/**
 * A simple class for storing the result of one game
 * contains co2, happiness and playerType of the concerned game
 */
public class GameResult {

    private double co2;
    private double happiness;
    private PlayerType playerType;
    private ArrayList<String> itemsBought;

    public GameResult(double co2, double happiness, PlayerType playerType, ArrayList<String> itemsBought) {
        this.co2 = co2;
        this.happiness = happiness;
        this.playerType = playerType;
        this.itemsBought = itemsBought;
    }

    public double getCo2() {
        return co2;
    }

    public double getHappiness() {
        return happiness;
    }

    public PlayerType getPlayerType() { return playerType; }

    public ArrayList<String> getItemsBought() {
        return itemsBought;
    }
}
