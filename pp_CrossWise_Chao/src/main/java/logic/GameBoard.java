package logic;

import java.util.*;

/**
 * this is just a board class.
 * it can be used to create a field generated every token on which has put on the board.
 * <p>
 *
 * @author chien-hsun
 */
public class GameBoard {

    /**
     * a field represent all the tokens in number which may be used fpr json file.
     */
    public Token[][] field;

    /**
     * a constructor create a token field to store ta tokens
     * while a specific token is put on the board by the player, this gameBoard will update itself.
     *
     * @param row row
     * @param col col
     */
    GameBoard(int row, int col) {
        field = new Token[row][col];

        for (Token[] tokens : field) {
            Arrays.fill(tokens, Token.NONE);
        }
    }

    /**
     * a string representation of a game field, this constructor is used at testing.
     * for example,
     * String board = "1 1 0 0 0 0\n" +
     * "0 2 2 2 0 0\n" +
     * "0 0 3 3 3 3\n" +
     * "0 4 4 4 4 4\n" +
     * "5 5 5 5 5 5\n" +
     * "6 5 4 3 2 1\n";
     *
     * @param s string of field
     */
    public GameBoard(String s) {

        String[] afterFirstSplit1 = s.split("\n");

        this.field = new Token[afterFirstSplit1.length][afterFirstSplit1.length];

        for (int row = 0; row < afterFirstSplit1.length; row++) {
            String[] arrayToStoreEverySecSplit2 = afterFirstSplit1[row].split(" ");
            for (int col = 0; col < arrayToStoreEverySecSplit2.length; col++) {

                if (arrayToStoreEverySecSplit2[col].startsWith("0")) {
                    field[row][col] = Token.NONE;
                } else if (arrayToStoreEverySecSplit2[col].startsWith("1")) {
                    field[row][col] = Token.SUN;
                } else if (arrayToStoreEverySecSplit2[col].startsWith("2")) {
                    field[row][col] = Token.CROSS;
                } else if (arrayToStoreEverySecSplit2[col].startsWith("3")) {
                    field[row][col] = Token.TRIANGLE;
                } else if (arrayToStoreEverySecSplit2[col].startsWith("4")) {
                    field[row][col] = Token.SQUARE;
                } else if (arrayToStoreEverySecSplit2[col].startsWith("5")) {
                    field[row][col] = Token.PENTAGON;
                } else if (arrayToStoreEverySecSplit2[col].startsWith("6")) {
                    field[row][col] = Token.STAR;
                }
            }
        }

    }

    /**
     * get a two-dimensional array of field
     *
     * @return field a field
     */
    public Token[][] getField() {
        return field;
    }

    /**
     * get the cell of given position
     *
     * @param pos position class
     * @return an int from given position
     */
    public int getCell(Position pos) {
        int x = pos.getX();     // row
        int y = pos.getY();     // col

        return field[x][y].getIndex();
    }

    /**
     * get the cell element of the given x and y
     *
     * @param x given x
     * @param y given y
     * @return an int from given x and y
     */
    public Token getCell(int x, int y) {
        return field[x][y];
    }

    /**
     * set the cell
     *
     * @param token given token
     * @param x     given x
     * @param y     given y
     */
    public void setCell(Token token, int x, int y) {
        this.field[x][y] = token;
    }

    /**
     * auxiliary method
     *
     * @return an int array
     */
    public int[] getAListOfSameColumn(int col) {
        int sizeOfALine = 6;
        int[] columnList = new int[sizeOfALine];
        for (int i = 0; i < columnList.length; i++) {
            columnList[i] = field[i][col].getIndex();
        }

        return columnList;
    }

    /**
     * auxiliary method
     * get a list of given row.
     *
     * @param row given row
     * @return an int array
     */
    public int[] getAListOfSameRow(int row) {
        int sizeOfALine = 6;
        int[] rowList = new int[sizeOfALine];
        for (int i = 0; i < rowList.length; i++) {
            rowList[i] = field[row][i].getIndex();
        }

        return rowList;
    }

    /**
     * using hashset to check if there are tokens are duplicated by using add method.
     * because the add method in hashset structure does not apply to the same elements.
     * <p>
     * array = "1,1,0,0,0,0"
     *
     * @return boolean value.
     */
    public boolean hasDuplicatedTokens(int[] array) {

        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] != Token.NONE.getIndex() && array[i] != Token.NONE.getIndex()) {
                    if (array[j] == array[i]) return true;
                }

            }
        }
        return false;

    }


    /**
     * show how many token set in a given array.
     * <p>
     * [3,4,4,2,1,1]
     *
     * @return an array contains all appearing frequency of the duplicate element
     */
    public int[] getNumOfDifferentSetTokens(int[] array) {
        int min_of_sameTokens = 2;
        int[] copyArray = array.clone();
        ArrayList<Integer> list = new ArrayList<>();

        for (int i = 0; i < copyArray.length; i++) {
            int count = 0;
            if (copyArray[i] != Token.NONE.getIndex()) {
                int recordCompare = copyArray[i];
                for (int j = 0; j < copyArray.length; j++) {
                    if (copyArray[j] != Token.NONE.getIndex()) {
                        if (recordCompare == copyArray[j]) {
                            copyArray[j] = Token.NONE.getIndex();
                            count++;
                        }
                    }
                }
                if (count >= min_of_sameTokens) {
                    list.add(count);
                }
            }
        }

        return list.stream().mapToInt(Integer::intValue).toArray();

    }

    /**
     * show an element that has appeared the most
     * [3,4,4,2,1,1]
     *
     * @param array input array.
     * @return an array contains all appearing frequency of the duplicate element
     */
    public int getTheMostOftenElement(int[] array) {
        int min_of_sameTokens = 2;
        int[] copyArray = array.clone();

        int mostOftenElement = -1;

        for (int i = 0; i < copyArray.length; i++) {
            int count = 0;
            if (copyArray[i] != Token.NONE.getIndex()) {
                int recordCompare = copyArray[i];
                for (int j = 0; j < copyArray.length; j++) {
                    if (copyArray[j] != Token.NONE.getIndex()) {
                        if (recordCompare == copyArray[j]) {
                            copyArray[j] = Token.NONE.getIndex();
                            count++;
                        }
                    }
                }
                if (count >= min_of_sameTokens) {
                    mostOftenElement = recordCompare;
                }
            }
        }
        return mostOftenElement;
    }


    /**
     * showing a line has six same tokens
     *
     * @param array given array
     * @return true, has six same, false, no six same
     */
    public boolean hasSixSameTokens(int[] array) {
        int firstIndex = 0;
        int checkingValue = array[firstIndex];
        if (checkingValue != Token.NONE.getIndex()) {
            for (int element : array) {
                if (element != checkingValue) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * check a given array has all different elements and must be full of elements
     *
     * @param array the given array
     * @return true, all different,
     * false, not all different
     */
    public boolean hasAllDifferentTokens(int[] array) {

        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] == Token.NONE.getIndex() || array[i] == Token.NONE.getIndex() || array[j] == array[i]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * check the board is empty or not
     * <p>
     * when board is empty, it consists of all 0 in every position
     *
     * @return true, is empty,
     * false, is not empty
     */
    public boolean isBoardEmpty() {
        for (Token[] tokens : field) {
            for (Token token : tokens) {
                if (token.getIndex() != Token.NONE.getIndex()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * check the board has full tokens
     * <p>
     * when board is full, it does not contain any none token
     *
     * @return true, is empty,
     * false, is not empty
     */
    public boolean isFullBoard() {
        for (Token[] tokens : field) {
            for (Token token : tokens) {
                if (token.getIndex() == Token.NONE.getIndex()) {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * if the position on the board is nothing
     *
     * @return true, the position is empty,
     * false, the position is not empty.
     */
    public boolean isPositionEmpty(Position pos) {
        return getCell(pos) == Token.NONE.getIndex();
    }

    /**
     * transform the game field to int array
     *
     * @return a two-dimensional array of game field
     */
    public int[][] getIntField() {

        Token[][] originalField = this.field;
        int[][] newField = new int[originalField.length][originalField.length];

        for (int i = 0; i < originalField.length; i++) {
            for (int j = 0; j < originalField[i].length; j++) {
                newField[i][j] = originalField[i][j].getIndex();
            }
        }

        return newField;
    }

    /**
     * assertEquals call the equals.
     * a pack is equal if it contains the same tokens as the other pack, not
     * more or less.
     * <p>
     *
     * @param obj another pack
     * @return true, if the two packs hold the same tokens
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GameBoard) {
            if (!this.isBoardEmpty() && !(((GameBoard) obj).isBoardEmpty())) {
                return Arrays.deepEquals(this.field, ((GameBoard) obj).field);
            } else return this.isBoardEmpty() && ((GameBoard) obj).isBoardEmpty();
        }
        return false;
    }

    /**
     * @return representation of a gameBoard.
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (Token[] tokens : field) {
            for (int col = 0; col < tokens.length; col++) {
                output.append(tokens[col]);
                if (col != tokens.length - 1) {
                    output.append(" ");
                }
            }
            output.append("\n");
        }
        return output.toString();
    }


}

