package it.unibo.alchemist.models.myEnums;

public enum Directions implements DirectionInfo {
    NORTH {
        @Override
        public Double getX() {
            return 0.0;
        }

        @Override
        public Double getY() {
            return 1.0;
        }

        @Override
        public Directions getMyLeft() {
            return NORTH_WEST;
        }

        @Override
        public Directions getMyRight() {
            return NORTH_EAST;
        }
    },
    NORTH_EAST {
        @Override
        public Double getX() {
            return 1.0;
        }

        @Override
        public Double getY() {
            return 1.0;
        }

        @Override
        public Directions getMyLeft() {
            return NORTH;
        }

        @Override
        public Directions getMyRight() {
            return EAST;
        }
    },
    NORTH_WEST {
        @Override
        public Double getX() {
            return -1.0;
        }

        @Override
        public Double getY() {
            return 1.0;
        }

        @Override
        public Directions getMyLeft() {
            return WEST;
        }

        @Override
        public Directions getMyRight() {
            return NORTH;
        }
    },
    SOUTH {
        @Override
        public Double getX() {
            return 0.0;
        }

        @Override
        public Double getY() {
            return -1.0;
        }

        @Override
        public Directions getMyLeft() {
            return SOUTH_EAST;
        }

        @Override
        public Directions getMyRight() {
            return SOUTH_WEST;
        }
    },
    SOUTH_EAST {
        @Override
        public Double getX() {
            return 1.0;
        }

        @Override
        public Double getY() {
            return -1.0;
        }

        @Override
        public Directions getMyLeft() {
            return EAST;
        }

        @Override
        public Directions getMyRight() {
            return SOUTH;
        }
    },
    SOUTH_WEST {
        @Override
        public Double getX() {
            return -1.0;
        }

        @Override
        public Double getY() {
            return -1.0;
        }

        @Override
        public Directions getMyLeft() {
            return SOUTH;
        }

        @Override
        public Directions getMyRight() {
            return WEST;
        }
    },
    EAST {
        @Override
        public Double getX() {
            return 1.0;
        }

        @Override
        public Double getY() {
            return 0.0;
        }

        @Override
        public Directions getMyLeft() {
            return NORTH_EAST;
        }

        @Override
        public Directions getMyRight() {
            return SOUTH_WEST;
        }
    },
    WEST {
        @Override
        public Double getX() {
            return -1.0;
        }

        @Override
        public Double getY() {
            return 0.0;
        }

        @Override
        public Directions getMyLeft() {
            return SOUTH_WEST;
        }

        @Override
        public Directions getMyRight() {
            return NORTH_WEST;
        }
    },
    DEFAULT {
        @Override
        public Double getX() {
            return 0.0;
        }

        @Override
        public Double getY() {
            return 0.0;
        }

        @Override
        public Directions getMyLeft() {
            return null;
        }

        @Override
        public Directions getMyRight() {
            return null;
        }
    };

    public Directions getDirection(int number) {
        return switch (number) {
            case 0 -> NORTH;
            case 1 -> SOUTH;
            case 2 -> EAST;
            case 3 -> WEST;
            case 4 -> NORTH_EAST;
            case 5 -> NORTH_WEST;
            case 6 -> SOUTH_EAST;
            case 7 -> SOUTH_WEST;
            default -> null;
        };
    }
    @Override
    public abstract Double getX();
    @Override
    public abstract Double getY();
    @Override
    public abstract Directions getMyLeft();
    @Override
    public abstract Directions getMyRight();
    public Double getCorrespondingAngle(final Double angle, final Directions dir){
        return angle + switch (dir){
            case NORTH -> 90.0;
            case SOUTH -> 270.0;
            case EAST ->0.0;
            case WEST ->180.0;
            case NORTH_EAST ->45.0 ;
            case NORTH_WEST ->135.0;
            case SOUTH_EAST -> 315;
            case SOUTH_WEST ->225;
            default -> 0.0;
        };
    }
}
