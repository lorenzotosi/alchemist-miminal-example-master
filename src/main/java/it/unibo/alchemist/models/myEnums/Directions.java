package it.unibo.alchemist.models.myEnums;

public enum Directions {
    NORTH {
        @Override
        public Double getX() {
            return 0.0;
        }

        @Override
        public Double getY() {
            return 1.0;
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

    public abstract Double getX();
    public abstract Double getY();
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
