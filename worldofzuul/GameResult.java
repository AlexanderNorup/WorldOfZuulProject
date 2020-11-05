package worldofzuul;

/**
 * A simple class for storing the result of one game
 * contains co2, happiness and playerType of the concerned game
 */
public class GameResult {

    private double co2;
    private double happiness;
    private PlayerType playerType;

    public GameResult(double co2, double happiness, PlayerType playerType) {
        this.co2 = co2;
        this.happiness = happiness;
        this.playerType = playerType;
    }

    public double getCo2() {
        return co2;
    }

    public double getHappiness() {
        return happiness;
    }
}
