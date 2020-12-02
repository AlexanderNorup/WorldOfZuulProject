package worldofzuul.DomainLayer.RoomObjects;

import worldofzuul.DomainLayer.Interfaces.ICashier;
import worldofzuul.DomainLayer.Interfaces.IRoomObject;

public class Cashier implements IRoomObject, ICashier {

    private final int xPosition;
    private final int yPosition;
    public Cashier(int xPosition, int yPosition) {
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
