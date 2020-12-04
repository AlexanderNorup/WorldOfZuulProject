package worldofzuul.DataLayer;

import java.util.ArrayList;

/**
 * Contains co2, happiness and the name of a playerType for a single game
 */
public class GameResultData {
    private double co2;
    private double happiness;
    private String playerTypeName;
    private ArrayList<String> itemsBought;

    public GameResultData(double co2, double happiness, String playerTypeName, ArrayList<String> itemsBought) {
        this.co2 = co2;
        this.happiness = happiness;
        this.playerTypeName = playerTypeName;
        this.itemsBought = itemsBought;
    }

    public double getCo2() {
        return co2;
    }

    public double getHappiness() {
        return happiness;
    }

    public String getPlayerTypeName() {
        return playerTypeName;
    }

    public ArrayList<String> getItemsBought() {
        return itemsBought;
    }
}
