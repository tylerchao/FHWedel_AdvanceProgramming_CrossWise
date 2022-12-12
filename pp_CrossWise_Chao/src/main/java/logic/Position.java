package logic;

/**
 * a class for the single position on the game cross board.
 * The Position class represents a position on the board and prevents the use of an array or independent int values.
 * only represent the x and y of the game board.
 *
 * @author chien-hsun
 */
public class Position {
    /**
     * x and y
     */
    private int x, y;

    /**
     * an empty constructor to create an empty position
     */
    public Position() {
    }

    /**
     * a position object contains two parameters, row and col
     *
     * @param col x
     * @param row y
     */
    public Position(int row, int col) {
        super();
        this.x = row;
        this.y = col;
    }

    /**
     * getter for x
     *
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * getter for y
     *
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * setter for x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * setter for y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Position: [2, 3]
     *
     * @return the representation of a position object.
     */
    @Override
    public String toString() {
        Position position = new Position(x, y);

        return "[" + position.getX() + "," + position.getY() + "]";
    }

}
