package logic;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * This test class is for testing GameBoard class.
 * It contains the test case for all methods which is created in GameBoard class.
 */
public class GameBoardTest {
    /**
     * check if the board is empty
     */
    @Test
    public void testBoardIsEmpty() {
        String board = """
                0 0 0 0 0 0
                0 0 0 0 0 0
                0 0 0 0 0 0
                0 0 0 0 0 0
                0 0 0 0 0 0
                0 0 0 0 0 0
                """;
        GameBoard gameBoard = new GameBoard(board);
        assertTrue(gameBoard.isBoardEmpty());

    }

    /**
     * check the board is not empty
     */
    @Test
    public void testBoardIsNotEmpty() {
        String board = """
                0 0 0 0 0 0
                0 0 0 0 0 0
                0 0 0 1 0 0
                0 0 0 0 0 0
                0 0 0 0 0 0
                0 0 0 0 0 0
                """;
        GameBoard gameBoard = new GameBoard(board);
        assertFalse(gameBoard.isBoardEmpty());

    }

    /**
     * check the board is not full
     */
    @Test
    public void testBoardIsNotFullBoard() {
        String board = """
                0 0 0 0 0 0
                0 0 0 0 0 0
                0 0 0 1 0 0
                0 0 0 0 0 0
                0 0 0 0 0 0
                0 0 0 0 0 0
                """;
        GameBoard gameBoard = new GameBoard(board);
        assertFalse(gameBoard.isFullBoard());

    }

    /**
     * check the board is full board
     */
    @Test
    public void testBoardIsFullBoard() {
        String board = """
                1 2 3 4 5 6
                2 3 4 5 6 1
                3 4 5 6 1 2
                4 5 6 1 2 3
                5 6 1 2 3 4
                6 1 2 3 4 5
                """;
        GameBoard gameBoard = new GameBoard(board);
        assertTrue(gameBoard.isFullBoard());

    }

    /**
     * test getListOfSameCol getListOfSameRow of empty board Method
     */
    @Test
    public void test_GetListOfSameRow_empty() {
        String board = """
                0 0 0 0 0 0
                0 0 0 0 0 0
                0 0 0 0 0 0
                0 0 0 0 0 0
                0 0 0 0 0 0
                0 0 0 0 0 0
                """;
        Logic game = new Logic(new FakeGUI(), board);
        GameBoard gameBoard = game.getBoard();

        int[] expectedColumn = {0, 0, 0, 0, 0, 0};
        int[] expectedRow = {0, 0, 0, 0, 0, 0};

        assertArrayEquals(expectedColumn, gameBoard.getAListOfSameColumn(0));
        assertArrayEquals(expectedRow, gameBoard.getAListOfSameRow(0));

    }

    /**
     * test getListOfSameRow Method
     */
    @Test
    public void test_GetListOfSameRow_allMixed() {
        String board = """
                1 2 3 4 5 6
                2 3 4 5 6 1
                3 4 5 6 1 2
                4 5 6 1 2 3
                5 6 1 2 3 4
                6 1 2 3 4 5
                """;
        Logic game = new Logic(new FakeGUI(), board);
        GameBoard gameBoard = game.getBoard();

        int[] expectedRow = {1, 2, 3, 4, 5, 6};
        int[] expectedRow2 = {2, 3, 4, 5, 6, 1};
        int[] expectedRow3 = {3, 4, 5, 6, 1, 2};
        int[] expectedRow4 = {4, 5, 6, 1, 2, 3};
        int[] expectedRow5 = {5, 6, 1, 2, 3, 4};
        int[] expectedRow6 = {6, 1, 2, 3, 4, 5};

        assertArrayEquals(expectedRow, gameBoard.getAListOfSameRow(0));
        assertArrayEquals(expectedRow2, gameBoard.getAListOfSameRow(1));
        assertArrayEquals(expectedRow3, gameBoard.getAListOfSameRow(2));
        assertArrayEquals(expectedRow4, gameBoard.getAListOfSameRow(3));
        assertArrayEquals(expectedRow5, gameBoard.getAListOfSameRow(4));
        assertArrayEquals(expectedRow6, gameBoard.getAListOfSameRow(5));
    }


    /**
     * test getListOfSameCol Method
     */
    @Test
    public void test_GetListOfSameCol_allMixed() {
        String board = """
                1 2 3 4 5 6
                2 3 4 5 6 1
                3 4 5 6 1 2
                4 5 6 1 2 3
                5 6 1 2 3 4
                6 1 2 3 4 5
                """;
        Logic game = new Logic(new FakeGUI(), board);
        GameBoard gameBoard = game.getBoard();

        int[] expectedCol = {1, 2, 3, 4, 5, 6};
        int[] expectedCol2 = {2, 3, 4, 5, 6, 1};
        int[] expectedCol3 = {3, 4, 5, 6, 1, 2};
        int[] expectedCol4 = {4, 5, 6, 1, 2, 3};
        int[] expectedCol5 = {5, 6, 1, 2, 3, 4};
        int[] expectedCol6 = {6, 1, 2, 3, 4, 5};

        assertArrayEquals(expectedCol, gameBoard.getAListOfSameRow(0));
        assertArrayEquals(expectedCol2, gameBoard.getAListOfSameRow(1));
        assertArrayEquals(expectedCol3, gameBoard.getAListOfSameRow(2));
        assertArrayEquals(expectedCol4, gameBoard.getAListOfSameRow(3));
        assertArrayEquals(expectedCol5, gameBoard.getAListOfSameRow(4));
        assertArrayEquals(expectedCol6, gameBoard.getAListOfSameRow(5));
    }

    /**
     * test a row has duplicate value
     */
    @Test
    public void test_hasDuplicatedValue_row() {
        String board = """
                1 1 0 0 0 0
                0 2 2 2 0 0
                0 0 0 0 0 0
                0 4 4 4 4 4
                5 5 5 5 5 5
                6 5 4 3 2 1
                """;
        GameBoard gameBoard = new GameBoard(board);

        int[] row = gameBoard.getAListOfSameRow(0);
        int[] row2 = gameBoard.getAListOfSameRow(1);
        int[] row3 = gameBoard.getAListOfSameRow(2);
        int[] row4 = gameBoard.getAListOfSameRow(3);
        int[] row5 = gameBoard.getAListOfSameRow(4);
        int[] row6 = gameBoard.getAListOfSameRow(5);

        assertTrue(gameBoard.hasDuplicatedTokens(row));
        assertTrue(gameBoard.hasDuplicatedTokens(row2));
        assertFalse(gameBoard.hasDuplicatedTokens(row3));
        assertTrue(gameBoard.hasDuplicatedTokens(row4));
        assertTrue(gameBoard.hasDuplicatedTokens(row5));
        assertFalse(gameBoard.hasDuplicatedTokens(row6));

    }

    /**
     * test a col has duplicate value
     */
    @Test
    public void test_hasDuplicatedValue_col() {
        String board = """
                1 1 0 0 0 6
                0 2 2 2 0 6
                0 0 3 3 3 6
                0 4 4 4 4 6
                5 5 5 5 5 6
                6 5 4 3 2 6
                """;
        GameBoard gameBoard = new GameBoard(board);

        int[] col = gameBoard.getAListOfSameColumn(0);
        int[] col2 = gameBoard.getAListOfSameColumn(1);
        int[] col3 = gameBoard.getAListOfSameColumn(2);
        int[] col4 = gameBoard.getAListOfSameColumn(3);
        int[] col5 = gameBoard.getAListOfSameColumn(4);
        int[] col6 = gameBoard.getAListOfSameColumn(5);

        assertFalse(gameBoard.hasDuplicatedTokens(col));
        assertTrue(gameBoard.hasDuplicatedTokens(col2));
        assertTrue(gameBoard.hasDuplicatedTokens(col3));
        assertTrue(gameBoard.hasDuplicatedTokens(col4));
        assertFalse(gameBoard.hasDuplicatedTokens(col5));
        assertTrue(gameBoard.hasDuplicatedTokens(col6));
    }

    /**
     * test a line has six same token
     */
    @Test
    public void test_hasSixSameToken() {
        String board = """
                0 0 0 0 0 0
                0 2 2 2 0 0
                0 0 3 3 3 3
                0 4 4 4 4 4
                5 5 5 5 5 5
                6 5 4 3 2 1
                """;
        GameBoard gameBoard = new GameBoard(board);

        int[] row = gameBoard.getAListOfSameRow(0);
        int[] row2 = gameBoard.getAListOfSameRow(1);
        int[] row3 = gameBoard.getAListOfSameRow(2);
        int[] row4 = gameBoard.getAListOfSameRow(3);
        int[] row5 = gameBoard.getAListOfSameRow(4);
        int[] row6 = gameBoard.getAListOfSameRow(5);

        assertFalse(gameBoard.hasSixSameTokens(row));
        assertFalse(gameBoard.hasSixSameTokens(row2));
        assertFalse(gameBoard.hasSixSameTokens(row3));
        assertFalse(gameBoard.hasSixSameTokens(row4));
        assertTrue(gameBoard.hasSixSameTokens(row5));
        assertFalse(gameBoard.hasSixSameTokens(row6));
    }

    /**
     * test a line has all different tokens
     */
    @Test
    public void test_hasDifferentTokens() {
        String board = """
                1 1 0 0 0 0
                0 2 2 2 0 0
                0 0 3 3 3 3
                0 4 4 4 4 4
                5 5 5 5 5 5
                6 5 4 3 2 1
                """;
        GameBoard gameBoard = new GameBoard(board);

        int[] col = gameBoard.getAListOfSameColumn(0);
        int[] col2 = gameBoard.getAListOfSameColumn(1);
        int[] col3 = gameBoard.getAListOfSameColumn(2);
        int[] col4 = gameBoard.getAListOfSameColumn(3);
        int[] col5 = gameBoard.getAListOfSameColumn(4);
        int[] col6 = gameBoard.getAListOfSameColumn(5);
        System.out.println(Arrays.toString(col));

        assertFalse(gameBoard.hasAllDifferentTokens(col));
        assertFalse(gameBoard.hasAllDifferentTokens(col2));
        assertFalse(gameBoard.hasAllDifferentTokens(col3));
        assertFalse(gameBoard.hasAllDifferentTokens(col4));
        assertFalse(gameBoard.hasAllDifferentTokens(col5));
        assertFalse(gameBoard.hasAllDifferentTokens(col6));
    }

    /**
     * test a line has all different tokens with all none
     */
    @Test
    public void test_hasDifferentTokens_allNone() {
        String board = """
                0 0 0 0 0 0
                0 0 0 0 0 0
                0 0 0 0 0 0
                0 0 0 0 0 0
                0 0 0 0 0 0
                0 0 0 0 0 0
                """;
        GameBoard gameBoard = new GameBoard(board);

        int[] col = gameBoard.getAListOfSameColumn(0);
        int[] col2 = gameBoard.getAListOfSameColumn(1);
        int[] col3 = gameBoard.getAListOfSameColumn(2);
        int[] col4 = gameBoard.getAListOfSameColumn(3);
        int[] col5 = gameBoard.getAListOfSameColumn(4);
        int[] col6 = gameBoard.getAListOfSameColumn(5);

        assertFalse(gameBoard.hasAllDifferentTokens(col));
        assertFalse(gameBoard.hasAllDifferentTokens(col2));
        assertFalse(gameBoard.hasAllDifferentTokens(col3));
        assertFalse(gameBoard.hasAllDifferentTokens(col4));
        assertFalse(gameBoard.hasAllDifferentTokens(col5));
        assertFalse(gameBoard.hasAllDifferentTokens(col6));
    }

    /**
     * test a line has all different tokens
     */
    @Test
    public void test_hasDifferentTokens_row_allDifferent() {
        String board = """
                1 2 3 4 5 6
                2 3 4 5 6 1
                3 4 5 6 1 2
                4 5 6 1 2 3
                5 6 1 2 3 4
                6 1 2 3 4 5
                """;
        GameBoard gameBoard = new GameBoard(board);

        int[] row = gameBoard.getAListOfSameRow(0);
        int[] row2 = gameBoard.getAListOfSameRow(1);
        int[] row3 = gameBoard.getAListOfSameRow(2);
        int[] row4 = gameBoard.getAListOfSameRow(3);
        int[] row5 = gameBoard.getAListOfSameRow(4);
        int[] row6 = gameBoard.getAListOfSameRow(5);

        assertTrue(gameBoard.hasAllDifferentTokens(row));
        assertTrue(gameBoard.hasAllDifferentTokens(row2));
        assertTrue(gameBoard.hasAllDifferentTokens(row3));
        assertTrue(gameBoard.hasAllDifferentTokens(row4));
        assertTrue(gameBoard.hasAllDifferentTokens(row5));
        assertTrue(gameBoard.hasAllDifferentTokens(row6));
    }

    /**
     * test get an array has different token from a line
     */
    @Test
    public void test_getNumOfDifferentSetTokens() {
        String board = """
                3 4 4 2 1 1
                0 2 2 2 0 0
                0 0 3 3 3 3
                0 4 4 4 4 4
                5 5 5 5 5 5
                6 5 4 3 2 1
                """;
        GameBoard gameBoard = new GameBoard(board);

        int[] row = gameBoard.getAListOfSameRow(0);
        int[] row2 = gameBoard.getAListOfSameRow(1);
        int[] row3 = gameBoard.getAListOfSameRow(2);
        int[] row4 = gameBoard.getAListOfSameRow(3);
        int[] row5 = gameBoard.getAListOfSameRow(4);
        int[] row6 = gameBoard.getAListOfSameRow(5);

        int[] expectedArray = {2, 2};
        int[] expectedArray2 = {3};
        int[] expectedArray3 = {4};
        int[] expectedArray4 = {5};
        int[] expectedArray5 = {6};
        int[] expectedArray6 = {};

        assertArrayEquals(expectedArray, gameBoard.getNumOfDifferentSetTokens(row));
        assertArrayEquals(expectedArray2, gameBoard.getNumOfDifferentSetTokens(row2));
        assertArrayEquals(expectedArray3, gameBoard.getNumOfDifferentSetTokens(row3));
        assertArrayEquals(expectedArray4, gameBoard.getNumOfDifferentSetTokens(row4));
        assertArrayEquals(expectedArray5, gameBoard.getNumOfDifferentSetTokens(row5));
        assertArrayEquals(expectedArray6, gameBoard.getNumOfDifferentSetTokens(row6));
    }

    /**
     * test get an array has different token from a line with none token
     */
    @Test
    public void test_getNumOfDifferentSetTokens_allNone() {
        String board = """
                0 0 0 0 0 0
                0 0 0 0 0 0
                0 0 0 0 0 0
                0 0 0 0 0 0
                0 0 0 0 0 0
                0 0 0 0 0 0
                """;
        GameBoard gameBoard = new GameBoard(board);

        int[] row = gameBoard.getAListOfSameRow(0);
        int[] row2 = gameBoard.getAListOfSameRow(1);
        int[] row3 = gameBoard.getAListOfSameRow(2);
        int[] row4 = gameBoard.getAListOfSameRow(3);
        int[] row5 = gameBoard.getAListOfSameRow(4);
        int[] row6 = gameBoard.getAListOfSameRow(5);

        int[] expectedArray = {};
        int[] expectedArray2 = {};
        int[] expectedArray3 = {};
        int[] expectedArray4 = {};
        int[] expectedArray5 = {};
        int[] expectedArray6 = {};

        assertArrayEquals(expectedArray, gameBoard.getNumOfDifferentSetTokens(row));
        assertArrayEquals(expectedArray2, gameBoard.getNumOfDifferentSetTokens(row2));
        assertArrayEquals(expectedArray3, gameBoard.getNumOfDifferentSetTokens(row3));
        assertArrayEquals(expectedArray4, gameBoard.getNumOfDifferentSetTokens(row4));
        assertArrayEquals(expectedArray5, gameBoard.getNumOfDifferentSetTokens(row5));
        assertArrayEquals(expectedArray6, gameBoard.getNumOfDifferentSetTokens(row6));
    }

    /**
     * test get the most often element from a line
     */
    @Test
    public void test_getTheMostOftenElement() {
        String board = """
                3 4 4 2 1 1
                0 2 2 2 0 0
                0 0 3 3 3 3
                0 4 4 4 4 4
                5 5 5 5 5 5
                6 5 4 3 2 1
                """;
        GameBoard gameBoard = new GameBoard(board);

        int[] row = gameBoard.getAListOfSameRow(0);
        int[] row2 = gameBoard.getAListOfSameRow(1);
        int[] row3 = gameBoard.getAListOfSameRow(2);
        int[] row4 = gameBoard.getAListOfSameRow(3);
        int[] row5 = gameBoard.getAListOfSameRow(4);
        int[] row6 = gameBoard.getAListOfSameRow(5);

        assertEquals(1, gameBoard.getTheMostOftenElement(row));
        assertEquals(2, gameBoard.getTheMostOftenElement(row2));
        assertEquals(3, gameBoard.getTheMostOftenElement(row3));
        assertEquals(4, gameBoard.getTheMostOftenElement(row4));
        assertEquals(5, gameBoard.getTheMostOftenElement(row5));
        assertEquals(-1, gameBoard.getTheMostOftenElement(row6));

    }

    /**
     * test is the given position is empty
     */
    @Test
    public void test_isPositionEmpty_col() {
        String board = """
                1 1 0 0 0 0
                0 2 2 2 0 0
                0 0 3 3 3 3
                0 4 4 4 4 4
                5 5 5 5 5 5
                6 5 4 3 2 1
                """;

        GameBoard gameBoard = new GameBoard(board);
        Position pos = new Position(0, 0);
        Position pos1 = new Position(0, 1);
        Position pos2 = new Position(0, 2);
        Position pos3 = new Position(2, 0);
        Position pos4 = new Position(3, 3);

        assertFalse(gameBoard.isPositionEmpty(pos));
        assertFalse(gameBoard.isPositionEmpty(pos1));
        assertTrue(gameBoard.isPositionEmpty(pos2));
        assertTrue(gameBoard.isPositionEmpty(pos3));
        assertFalse(gameBoard.isPositionEmpty(pos4));
    }

}
