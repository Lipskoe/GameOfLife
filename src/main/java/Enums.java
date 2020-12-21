enum MoveDirection {
    FORWARD,
    BACKWARD,
    RIGHT,
    LEFT
}
enum MapDirection {
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;

    public MapDirection next(){
        switch(this){
            case NORTH: return NORTHEAST;
            case SOUTH: return SOUTHWEST;
            case EAST: return SOUTHEAST;
            case WEST: return NORTHWEST;
            case NORTHEAST: return EAST;
            case NORTHWEST: return NORTH;
            case SOUTHEAST: return SOUTH;
            case SOUTHWEST: return WEST;
            default : return NORTH;
        }
    }

    public MapDirection previous(){
        switch(this){
            case NORTH: return NORTHWEST;
            case SOUTH: return SOUTHEAST;
            case EAST: return NORTHEAST;
            case WEST: return SOUTHWEST;
            case NORTHEAST: return NORTH;
            case NORTHWEST: return WEST;
            case SOUTHEAST: return EAST;
            case SOUTHWEST: return SOUTH;
            default : return NORTH;
        }
    }

    public Vector2d toUnitVector(){
        switch (this){
            case NORTH: return new Vector2d(0,1);
            case SOUTH: return new Vector2d(0,-1);
            case EAST: return new Vector2d(1,0);
            case WEST: return new Vector2d(-1,0);
            case NORTHEAST: return new Vector2d(1,1);
            case NORTHWEST: return new Vector2d(-1,1);
            case SOUTHEAST: return new Vector2d(1,-1);
            case SOUTHWEST: return new Vector2d(-1,-1);
            default: return new Vector2d(0,0);
        }
    }
}