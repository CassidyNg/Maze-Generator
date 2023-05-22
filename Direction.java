//a Direction constant can be any of the four cardinal directions
public enum Direction {
    TOP, BOTTOM, LEFT, RIGHT;

    //create a static method called 'invert' which takes a Direction and returns a Direction
    //for example, Direction.invert(Direction.LEFT) should return Direction.RIGHT
    //YOUR CODE HERE:
    public static Direction invert(Direction d){
        if(d == TOP){
            return BOTTOM;
        }else if(d == BOTTOM){
            return TOP;
        }else if(d == LEFT){
            return RIGHT;
        }else{
            return LEFT;
        }
    }
}