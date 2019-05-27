package model;

public enum Direction {
    UP(0),
    DOWN(1),
    RIGHT(2),
    LEFT(3);

    private final int directionCode;

    public int getDirectionCode() {
        return directionCode;
    }

    Direction(int directionCode) {
        this.directionCode = directionCode;
    }
}


