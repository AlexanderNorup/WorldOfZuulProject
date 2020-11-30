package worldofzuul.DataLayer;

/**
 * Contains co2, happiness and the name of a playerType for a single game
 */
public class GameResultData {
    private double co2;
    private double happiness;
    private String playerTypeName;

    public GameResultData(double co2, double happiness, String playerTypeName) {
        this.co2 = co2;
        this.happiness = happiness;
        this.playerTypeName = playerTypeName;
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
}
