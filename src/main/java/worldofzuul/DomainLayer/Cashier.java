package worldofzuul.DomainLayer;

import worldofzuul.DomainLayer.Interfaces.ICashier;

public class Cashier implements ICashier {

    private final int xPosition;
    private final int yPosition;
    Cashier(int xPosition, int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    @Override
    public int getXPosition() {
        return xPosition;
    }

    @Override
    public int getYPosition() {
        return yPosition;
    }
}
