//each cell in the maze is represented by a Cell object
public class Cell {
    private boolean wallTop = true;
    private boolean wallBottom = true;
    private boolean wallLeft = true;
    private boolean wallRight = true;

    private boolean unavailable = false;

    //create a method called 'connect' which takes a Direction constant as a parameter
    //it should set the corresponding instance variable to 'false'
    //YOUR CODE HERE:
    public void connect(Direction d){
        if(d == Direction.TOP){
            wallTop = false;
        }else if(d == Direction.BOTTOM){
            wallBottom = false;
        }else if(d == Direction.LEFT){
            wallLeft = false;
        }else{
            wallRight = false;
        }
    }

    //create a method called 'blocked' which takes a Direction and returns a boolean
    //for example, if the input is Direction.RIGHT, return 'wallRight'
    //YOUR CODE HERE:
    public boolean blocked(Direction d){
        if(d == Direction.TOP){
            return wallTop;
        }else if(d == Direction.BOTTOM){
            return wallBottom;
        }else if(d == Direction.LEFT){
            return wallLeft;
        }else{
            return wallRight;
        }
    }

    //create a method called 'setUnavailable' which takes no parameters,
    //and sets 'unavailable' to 'true'
    //YOUR CODE HERE:
    public void setUnavailable(){
        unavailable = true;
    }

    //create a getter method called 'isUnavailable' which returns the value of 'unavailable'
    //YOUR CODE HERE:
    public boolean isUnavailabe(){
        return unavailable;
    }
}
