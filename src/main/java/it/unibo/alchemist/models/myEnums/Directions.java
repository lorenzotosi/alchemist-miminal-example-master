package it.unibo.alchemist.models.myEnums;

public enum Directions {
    NORTH,
    SOUTH,
    ESAT,
    WEST,
    DEFAULT;
    public Directions getDirection(int number) {
        return switch (number) {
            case 0 -> NORTH;
            case 1 -> SOUTH;
            case 2 -> ESAT;
            case 3 -> WEST;
            default -> null;
        };
    }
}
