package it.unibo.alchemist.models.myEnums;

public enum Directions {
    NORTH,
    NORTH_EAST,
    NORTH_WEST,
    SOUTH,
    SOUTH_EAST,
    SOUTH_WEST,
    ESAT,
    WEST,
    DEFAULT;
    public Directions getDirection(int number) {
        return switch (number) {
            case 0 -> NORTH;
            case 1 -> SOUTH;
            case 2 -> ESAT;
            case 3 -> WEST;
            case 4 -> NORTH_EAST;
            case 5 -> NORTH_WEST;
            case 6 -> SOUTH_EAST;
            case 7 -> SOUTH_WEST;
            default -> null;
        };
    }
}
