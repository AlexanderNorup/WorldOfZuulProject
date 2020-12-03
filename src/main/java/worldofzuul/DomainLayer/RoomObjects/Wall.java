package worldofzuul.DomainLayer.RoomObjects;

import worldofzuul.DomainLayer.Interfaces.IWall;

public class Wall implements IWall {


    private final int xPosition;
    private final int yPosition;
    public Wall(int xPosition, int yPosition) {
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
