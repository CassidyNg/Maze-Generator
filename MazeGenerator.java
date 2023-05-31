import java.util.ArrayList;
import java.io.IOException;

import bridges.connect.Bridges;
import bridges.base.Color;
import bridges.base.ColorGrid;
import bridges.validation.RateLimitException;

public class MazeGenerator {
    public static void main(String[] args) throws IOException, RateLimitException{
        //create a new bridges project
        //remember to fill in your own assignment number, username, and api key
        Bridges bridges = new Bridges(4, "cng", "1048575341021");

        //set the title and description
        //YOUR CODE HERE:
        bridges.setTitle("Maze Generator");
        bridges.setDescription("Creating a randomly generated maze");

        //create a new MazeGenerator with a width and height of your choice
        //then call the toImage method to create a ColorGrid object
        //YOUR CODE HERE:
        MazeGenerator maze = new MazeGenerator(15, 20);
        ColorGrid colorGrid = maze.toImage();

        //replace 'object name' with the name of your ColorGrid object
        bridges.setDataStructure(colorGrid);
        bridges.visualize();
    }

    private final int height;
    private final int width;
    private int lastRow;
    private int lastCol;
    private Cell[][] grid;

    private MazeGenerator(int h, int w) {
        //set the instance variables 'height' and 'width'
        //YOUR CODE HERE:
        height = h;
        width = w;

        //set the variable 'grid' to an blank array with the appropriate dimensions
        //set the cell in the middle to a new Cell object
        //YOUR CODE HERE:
        grid = new Cell[height][width];
        grid[height/2][width/2] = new Cell();

        //add cells until the maze is complete
        while(isIncomplete()){
            addCell();
        }
    }

    //create a method called 'isIncomplete' which takes no parameters and returns a boolean
    //the method should return true if any of the elements of 'grid' are null
    //YOUR CODE HERE:
    public boolean isIncomplete(){
        for(int row = 0; row < height; row++){
            for(int col = 0; col < width; col++){
                if(grid[row][col] == null){
                    return true;
                }
            }
        }
        return false;
    }

    //return an ArrayList of all the directions that the cell at [row, col] can branch in
    private ArrayList<Direction> availableDirections(int row, int col) {
        //create an empty ArrayList of Direction constants
        ArrayList<Direction> directions = new ArrayList<Direction>();

        //if the cell is not at the top of the maze and the cell above it is null,
        //add Direction.TOP to the list of available directions
        if(row > 0 && grid[row - 1][col] == null) {
            directions.add(Direction.TOP);
        }

        //repeat the code above for the other three directions
        //YOUR CODE HERE:
        if(row < height - 1 && grid[row + 1][col] == null){
            directions.add(Direction.BOTTOM);
        }

        if(col > 0 && grid[row][col - 1] == null){
            directions.add(Direction.LEFT);
        }

        if(col < width - 1 && grid[row][col + 1] == null){
            directions.add(Direction.RIGHT);
        }

        //return the list of available directions
        return directions;
    }

    //branch the cell at [row, col] in a particular direction
    private void branch(int row, int col, Direction d) {
        //create two variables called 'newRow' and 'newCol'
        int newRow;
        int newCol;

        //for example, if 'd' is Direction.RIGHT, 'newCol' should be 1 greater than 'col'
        //YOUR CODE HERE:
        if(d == Direction.TOP){
            newRow = row - 1;
            newCol = col;
        }else if(d == Direction.BOTTOM){
            newRow = row++;
            newCol = col;
        }else if(d == Direction.LEFT){
            newRow = row;
            newCol = col - 1;
        }else{
            newRow = row;
            newCol = col + 1;
        }

        //create a new Cell object at the coordinates [newRow, newCol]
        //connect the cell at [row, col] in the direction 'd'
        //connect the cell at [newRow, newCol] in the inverse direction of 'd'
        //this joins to old cell to the new one
        //YOUR CODE HERE:
        grid[newRow][newCol] = new Cell();
        grid[row][col].connect(d);
        grid[newRow][newCol].connect(Direction.invert(d));

        //set 'lastRow' and 'lastCol' to the coordinates of the new cell
        //YOUR CODE HERE:
        lastRow = newRow;
        lastCol = newCol;
    }

    //create a method called 'randInt' which takes an integer and returns an integer
    //the returned value should be a random natural number smaller than the input
    //for example, if the input is 3, the method should randomly return 0, 1, or 2
    //YOUR CODE HERE:
    public int randInt(int num){
        return (int)(Math.random() * num);
    }

    //add a new cell to the maze
    private void addCell() {
        int row = lastRow;
        int col = lastCol;
        Cell cell = grid[lastRow][lastCol];

        //while 'cell' is null or unavailable,
        //use the randInt method to set 'row' and 'col' to random coordinates,
        //and reassign 'cell' accordingly
        //YOUR CODE HERE:
        while(cell == null || !(cell.isUnavailabe())){
            row = randInt(height);
            col = randInt(width);
            cell = grid[row][col];
        }

        //get an ArrayList of the available directions
        //if there are no available directions, set 'cell' to unavailable and break out of the method
        //YOUR CODE HERE:
        ArrayList<Direction> available = availableDirections(row, col);
        if(available.size() == 0){
            cell.setUnavailable();
            return;
        }

        //use the randInt method to get a random direction out of the list
        //YOUR CODE HERE:
        Direction randDirection = available.get(randInt(available.size()));

        //call the 'branch' method with your randomly generated row, column, and direction
        //this will create a new cell by branching the maze
        //YOUR CODE HERE:
        branch(row, col, randDirection);
    }

    //use the maze to create a ColorGrid image
    private ColorGrid toImage() {
        //each cell is 5 * 5, plus an additional unit for the bottom and right borders
        ColorGrid image = new ColorGrid(height * 5 + 1, width * 5 + 1);

        //create two new Color objects called 'wallColor' and 'pathColor'
        //YOUR CODE HERE:
        Color wallColor = new Color(0, 0, 0);
        Color pathColor = new Color(153, 204, 255);

        for(int r = 0; r < height; r++) {
            for(int c = 0; c < width; c++) {
                //create two new Color objects called 'leftColor' and 'topColor'
                //set leftColor to 'wallColor' if the cell at [r, c] is blocked on the left
                //set topColor to 'wallColor' if the cell is blocked on the top
                //otherwise, set it to 'pathColor'
                //YOUR CODE HERE:
                Color leftColor;
                Color topColor;

                if(grid[r][c].blocked(Direction.LEFT)){
                    leftColor = wallColor;
                }else{
                    leftColor = pathColor;
                }

                if(grid[r][c].blocked(Direction.TOP)){
                    topColor = wallColor;
                }else{
                    topColor = pathColor;
                }

                //top and left border of cell
                image.set(r * 5, c * 5, wallColor);
                for(int i = 1; i <= 4; i++) {
                    image.set(r * 5 + i, c * 5, leftColor);
                }
                for(int j = 1; j <= 4; j++) {
                    image.set(r * 5, c * 5 + j, topColor);
                }

                //set the inner part of the cell to 'pathColor' using a nested loop
                //color every pixel from [r * 5 + 1, c * 5 + 1] to [r * 5 + 4, c * 5 + 4]
                //YOUR CODE HERE:
                for(int row = 1; row <= 4; row++){
                    for(int col = 1; col <= 4; col++){
                        image.set(r * 5 + row, c * 5 + col, pathColor);
                    }
                }
            }
        }

        //right border of maze
        for(int i = 0; i <= height * 5; i++) {
            image.set(i, width * 5, wallColor);
        }

        //bottom border of maze
        for(int j = 0; j <= width * 5; j++) {
            image.set(height * 5, j, wallColor);
        }

        //return the ColorGrid object
        return image;
    }
}