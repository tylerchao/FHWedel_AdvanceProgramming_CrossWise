package logic;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

/**
 * this test case is used to test Logic class.
 */
public class LogicTest {

    /**
     * test scoring a line that all is 0 point
     */
    @Test
    public void test_Row_col_zero_point_empty() {
        String board = """
                0 0 0 0 0 0
                0 0 0 0 0 0
                0 0 0 0 0 0
                0 0 0 0 0 0
                0 0 0 0 0 0
                0 0 0 0 0 0
                """;
        Logic game = new Logic(new FakeGUI(), board);

        assertEquals(0, game.scoringALine(0, Logic.Team.GREEN));
        assertEquals(0, game.scoringALine(1, Logic.Team.GREEN));
        assertEquals(0, game.scoringALine(2, Logic.Team.GREEN));
        assertEquals(0, game.scoringALine(3, Logic.Team.GREEN));
        assertEquals(0, game.scoringALine(4, Logic.Team.GREEN));
        assertEquals(0, game.scoringALine(5, Logic.Team.GREEN));
        assertEquals(0, game.scoringALine(0, Logic.Team.GREEN));
        assertEquals(0, game.scoringALine(1, Logic.Team.GREEN));
        assertEquals(0, game.scoringALine(2, Logic.Team.GREEN));
        assertEquals(0, game.scoringALine(3, Logic.Team.GREEN));
        assertEquals(0, game.scoringALine(4, Logic.Team.GREEN));
        assertEquals(0, game.scoringALine(5, Logic.Team.GREEN));

    }

    /**
     * two points
     */
    @Test
    public void test_Row_TwoSame_One_point() {
        String board = """
                2 0 2 0 0 0
                0 2 0 2 0 0
                0 0 2 0 2 0
                0 0 0 2 0 2
                2 2 0 0 0 0
                0 0 0 2 2 0
                """;
        Logic game = new Logic(new FakeGUI(), board);

        assertEquals(1, game.scoringALine(0, Logic.Team.GREEN));
        assertEquals(1, game.scoringALine(1, Logic.Team.GREEN));
        assertEquals(1, game.scoringALine(2, Logic.Team.GREEN));
        assertEquals(1, game.scoringALine(3, Logic.Team.GREEN));
        assertEquals(1, game.scoringALine(4, Logic.Team.GREEN));
        assertEquals(1, game.scoringALine(5, Logic.Team.GREEN));


    }

    /**
     * three points
     */
    @Test
    public void test_Row_ThreeSameTokens_ThreePoints() {
        String board = """
                2 2 2 0 0 0
                0 2 2 2 0 0
                0 0 2 2 2 0
                0 0 0 2 2 2
                2 2 0 2 0 0
                2 0 2 0 2 0
                """;
        Logic game = new Logic(new FakeGUI(), board);

        assertEquals(3, game.scoringALine(0, Logic.Team.GREEN));
        assertEquals(3, game.scoringALine(1, Logic.Team.GREEN));
        assertEquals(3, game.scoringALine(2, Logic.Team.GREEN));
        assertEquals(3, game.scoringALine(3, Logic.Team.GREEN));
        assertEquals(3, game.scoringALine(4, Logic.Team.GREEN));
        assertEquals(3, game.scoringALine(5, Logic.Team.GREEN));

    }

    /**
     * five points
     */
    @Test
    public void test_Row_FourSame_fivePoints() {
        String board = """
                0 2 2 2 2 0
                0 2 2 2 0 2
                0 0 2 2 2 2
                2 2 0 0 2 2
                2 0 2 2 2 0
                2 0 2 0 2 2
                """;
        Logic game = new Logic(new FakeGUI(), board);

        assertEquals(5, game.scoringALine(0, Logic.Team.GREEN));
        assertEquals(5, game.scoringALine(1, Logic.Team.GREEN));
        assertEquals(5, game.scoringALine(2, Logic.Team.GREEN));
        assertEquals(5, game.scoringALine(3, Logic.Team.GREEN));
        assertEquals(5, game.scoringALine(4, Logic.Team.GREEN));
        assertEquals(5, game.scoringALine(5, Logic.Team.GREEN));


    }

    /**
     * six points
     */
    @Test
    public void test_Row_SixDifferent_SixPoints() {
        String board = """
                1 2 3 4 5 6
                2 3 4 5 6 1
                3 4 5 6 1 2
                4 5 6 1 2 3
                5 6 1 2 3 4
                6 1 2 3 4 5
                """;
        Logic game = new Logic(new FakeGUI(), board);

        assertEquals(6, game.scoringALine(0, Logic.Team.GREEN));
        assertEquals(6, game.scoringALine(1, Logic.Team.GREEN));
        assertEquals(6, game.scoringALine(2, Logic.Team.GREEN));
        assertEquals(6, game.scoringALine(3, Logic.Team.GREEN));
        assertEquals(6, game.scoringALine(4, Logic.Team.GREEN));
        assertEquals(6, game.scoringALine(5, Logic.Team.GREEN));

    }

    /**
     * seven points
     */
    @Test
    public void test_Row_fiveSame_sevenPoints() {
        String board = """
                1 1 1 1 1 0
                0 2 2 2 2 2
                3 0 3 3 3 3
                4 4 0 4 4 4
                5 5 5 0 5 5
                6 6 6 6 0 6
                """;
        Logic game = new Logic(new FakeGUI(), board);

        assertEquals(7, game.scoringALine(0, Logic.Team.GREEN));
        assertEquals(7, game.scoringALine(1, Logic.Team.GREEN));
        assertEquals(7, game.scoringALine(2, Logic.Team.GREEN));
        assertEquals(7, game.scoringALine(3, Logic.Team.GREEN));
        assertEquals(7, game.scoringALine(4, Logic.Team.GREEN));
        assertEquals(7, game.scoringALine(5, Logic.Team.GREEN));

    }

    /**
     * one points orange
     */
    @Test
    public void test_col_TwoSame_One_point() {
        String board = """
                2 0 0 0 0 2
                2 2 0 0 0 0
                0 2 2 0 0 0
                0 0 2 2 0 0
                0 0 0 2 2 0
                0 0 0 0 2 2
                """;
        Logic game = new Logic(new FakeGUI(), board);

        assertEquals(1, game.scoringALine(0, Logic.Team.ORANGE));
        assertEquals(1, game.scoringALine(1, Logic.Team.ORANGE));
        assertEquals(1, game.scoringALine(2, Logic.Team.ORANGE));
        assertEquals(1, game.scoringALine(3, Logic.Team.ORANGE));
        assertEquals(1, game.scoringALine(4, Logic.Team.ORANGE));
        assertEquals(1, game.scoringALine(5, Logic.Team.ORANGE));


    }

    /**
     * three points orange
     */
    @Test
    public void test_Col_ThreeSameTokens_ThreePoints() {
        String board = """
                2 0 0 0 2 2
                2 2 0 0 0 0
                2 2 2 0 0 2
                0 2 2 2 0 0
                0 0 2 2 2 0
                0 0 0 2 2 2
                """;
        Logic game = new Logic(new FakeGUI(), board);

        assertEquals(3, game.scoringALine(0, Logic.Team.ORANGE));
        assertEquals(3, game.scoringALine(1, Logic.Team.ORANGE));
        assertEquals(3, game.scoringALine(2, Logic.Team.ORANGE));
        assertEquals(3, game.scoringALine(3, Logic.Team.ORANGE));
        assertEquals(3, game.scoringALine(4, Logic.Team.ORANGE));
        assertEquals(3, game.scoringALine(5, Logic.Team.ORANGE));

    }

    /**
     * four points orange
     */
    @Test
    public void test_Col_FourSame_fivePoints() {
        String board = """
                2 0 0 2 2 2
                2 2 0 0 2 0
                2 2 2 0 0 2
                2 2 2 2 0 2
                0 2 2 2 2 0
                0 0 2 2 2 2
                """;
        Logic game = new Logic(new FakeGUI(), board);

        assertEquals(5, game.scoringALine(1, Logic.Team.ORANGE));
        assertEquals(5, game.scoringALine(2, Logic.Team.ORANGE));
        assertEquals(5, game.scoringALine(3, Logic.Team.ORANGE));
        assertEquals(5, game.scoringALine(4, Logic.Team.ORANGE));
        assertEquals(5, game.scoringALine(5, Logic.Team.ORANGE));
        assertEquals(5, game.scoringALine(0, Logic.Team.ORANGE));

    }

    /**
     * six point orange
     */
    @Test
    public void test_Col_SixDifferent_SixPoints() {
        String board = """
                1 2 3 4 5 6
                2 3 4 5 6 1
                3 4 5 6 1 2
                4 5 6 1 2 3
                5 6 1 2 3 4
                6 1 2 3 4 5
                """;
        Logic game = new Logic(new FakeGUI(), board);

        assertEquals(6, game.scoringALine(1, Logic.Team.ORANGE));
        assertEquals(6, game.scoringALine(2, Logic.Team.ORANGE));
        assertEquals(6, game.scoringALine(3, Logic.Team.ORANGE));
        assertEquals(6, game.scoringALine(4, Logic.Team.ORANGE));
        assertEquals(6, game.scoringALine(5, Logic.Team.ORANGE));
        assertEquals(6, game.scoringALine(0, Logic.Team.ORANGE));

    }

    /**
     * seven points orange
     */
    @Test
    public void test_Col_fiveSame_sevenPoints() {
        String board = """
                1 0 3 4 5 6
                1 2 0 4 5 6
                1 2 3 4 5 0
                1 2 3 4 0 6
                1 2 3 0 5 6
                0 2 3 4 5 6
                """;
        Logic game = new Logic(new FakeGUI(), board);

        assertEquals(7, game.scoringALine(1, Logic.Team.ORANGE));
        assertEquals(7, game.scoringALine(2, Logic.Team.ORANGE));
        assertEquals(7, game.scoringALine(3, Logic.Team.ORANGE));
        assertEquals(7, game.scoringALine(4, Logic.Team.ORANGE));
        assertEquals(7, game.scoringALine(5, Logic.Team.ORANGE));
        assertEquals(7, game.scoringALine(0, Logic.Team.ORANGE));
    }

    /**
     * a field contains multiple points combination
     */
    @Test
    public void test_scoring_field_Horizontal() {
        String board = """
                2 2 0 0 0 0
                0 3 3 3 0 0
                0 3 3 3 4 4
                4 4 0 0 4 4
                6 1 2 3 4 5
                0 6 6 6 6 6
                """;
        Logic game = new Logic(new FakeGUI(), board);
        GameBoard gameBoard = game.getBoard();
        assertEquals(1 + 3 + 4 + 5 + 6 + 7, game.getTotalScore(gameBoard, Logic.Team.GREEN));
    }

    @Test
    public void test_scoring_field_Vertical() {
        String board = """
                1 0 3 4 6 6
                1 2 0 4 1 6
                0 2 3 4 2 0
                0 2 3 4 3 6
                0 0 4 0 4 6
                0 0 4 0 5 6
                """;
        Logic game = new Logic(new FakeGUI(), board);
        GameBoard gameBoard = game.getBoard();
        assertEquals(1 + 3 + 4 + 5 + 6 + 7, game.getTotalScore(gameBoard, Logic.Team.ORANGE));

    }

    /**
     * when one team has six same token in a line, then game is over. this team is winner.
     */
    @Test
    public void test_winnerAppear_sixSameTokenInLine_col() {
        String board = """
                1 0 3 4 6 6
                1 2 0 4 1 6
                1 2 3 4 2 0
                1 2 3 4 3 6
                1 0 4 0 4 6
                1 0 4 0 5 6
                """;
        Logic game = new Logic(new FakeGUI(), board);

        assertTrue(game.isFinished());

    }

    /**
     * when one team has six same token in a line, then game is over. this team is winner.
     */
    @Test
    public void test_winnerAppear_sixSameTokenInLine_row() {
        String board = """
                1 1 1 1 1 1
                1 2 0 4 1 6
                0 2 3 4 2 0
                3 2 3 4 3 6
                4 0 4 0 4 6
                6 0 4 0 5 6
                """;
        Logic game = new Logic(new FakeGUI(), board);

        assertTrue(game.isFinished());

    }


    /**
     * game is over when the board is full, after that the total points of all lines is the winner.
     */
    @Test
    public void test_winnerAppear_fullBoard() {
        String board = """
                1 3 3 4 6 6
                1 2 4 4 1 6
                1 2 3 4 2 5
                1 2 3 4 3 6
                1 5 4 5 4 6
                1 6 4 3 5 6
                """;
        Logic game = new Logic(new FakeGUI(), board);

        assertTrue(game.isFinished());


    }

    /**
     * game is over when the board is full, after that the total points of all lines is the winner.
     */
    @Test
    public void test_winnerAppear_fullBoard_1None() {
        String board = """
                1 3 3 4 6 6
                1 2 4 4 1 6
                1 2 3 4 2 5
                1 2 0 4 3 6
                1 5 4 5 4 6
                1 6 4 3 5 6
                """;
        Logic game = new Logic(new FakeGUI(), board);

        assertTrue(game.isFinished());

    }

    /**
     * game is over when the board is full, after that the total points of all lines is the winner.
     */
    @Test
    public void test_isFinished_winnerNotAppear_NotFullBoard() {
        String board = """
                3 3 3 4 6 6
                1 2 4 4 1 6
                1 2 3 4 2 5
                1 2 0 4 3 6
                1 5 4 5 4 6
                1 6 4 3 5 6
                """;
        Logic game = new Logic(new FakeGUI(), board);

        assertFalse(game.isFinished());


    }

    /**
     * horizontal (Green) team win
     */
    @Test
    public void test_victory_horizontal_higherTotalPoints() {
        String board = """
                6 6 6 6 6 1
                2 3 2 3 4 1
                5 5 2 5 5 2
                1 5 5 1 3 3
                2 4 4 4 2 1
                3 1 2 6 4 5
                """;
        Logic game = new Logic(new FakeGUI(), board);
        GameBoard gameBoard = game.getBoard();
        String winnerTeam = "green";

        assertTrue(game.isFinished());
        assertEquals(7 + 2 + 6 + 3 + 4 + 6, game.getTotalScore(gameBoard, Logic.Team.GREEN));
        assertEquals(winnerTeam, game.getWinnerTeam());
    }


    /**
     * vertical (Orange) team win
     */
    @Test
    public void test_victory_vertical_higherTotalPoints() {
        String board = """
                3 2 1 5 2 6
                1 4 5 5 3 6
                2 4 5 2 2 6
                6 4 1 5 3 6
                4 2 3 5 4 6
                5 1 3 2 1 1
                """;
        Logic game = new Logic(new FakeGUI(), board);
        GameBoard gameBoard = game.getBoard();
        String winnerTeam = "orange";

        assertTrue(game.isFinished());
        assertEquals(7 + 2 + 6 + 3 + 4 + 6, game.getTotalScore(gameBoard, Logic.Team.ORANGE));
        assertEquals(winnerTeam, game.getWinnerTeam());

    }

    /**
     * two team has same score
     */
    @Test
    public void test_victory_tie() {
        String board = """
                6 6 6 6 6 1
                3 4 4 2 2 1
                3 3 3 5 2 1
                5 5 4 4 2 1
                6 2 3 4 5 1
                1 1 2 5 4 5
                """;
        Logic game = new Logic(new FakeGUI(), board);
        GameBoard gameBoard = game.getBoard();
        String winnerTeam = "tie";

        assertTrue(game.isFinished());
        assertEquals(7 + 2 + 3 + 2 + 6 + 2, game.getTotalScore(gameBoard, Logic.Team.GREEN));
        assertEquals(7 + 2 + 3 + 2 + 6 + 2, game.getTotalScore(gameBoard, Logic.Team.ORANGE));
        assertTrue(game.isFinished());
        assertEquals(winnerTeam, game.getWinnerTeam());
    }

    /**
     * six tuples for horizontal team
     */
    @Test
    public void test_victory_Horizontal_sixTuples() {
        String board = """
                6 6 6 6 6 6
                1 2 0 4 1 6
                0 2 3 4 2 0
                0 2 3 4 3 6
                0 0 4 0 4 6
                0 0 4 0 5 6
                """;
        Logic game = new Logic(new FakeGUI(), board);
        String winnerTeam = "green";

        assertTrue(game.isFinished());
        assertEquals(winnerTeam, game.getWinnerTeam());
    }

    /**
     * six tuples for vertical team
     */
    @Test
    public void test_victory_vertical_sixTuples() {
        String board = """
                1 0 3 4 6 6
                1 2 0 4 1 6
                1 2 3 4 2 0
                1 2 3 4 3 6
                1 0 4 0 4 6
                1 0 4 0 5 6
                """;
        Logic game = new Logic(new FakeGUI(), board);
        String winnerTeam = "orange";

        assertTrue(game.isFinished());
        assertEquals(winnerTeam, game.getWinnerTeam());
    }

    /**
     * game is not finished
     */
    @Test
    public void test_unfinishedGame() {
        String board = """
                1 0 3 4 6 6
                1 2 0 4 1 6
                0 2 3 4 2 0
                0 2 3 4 3 6
                0 0 4 0 4 6
                0 0 4 0 5 6
                """;
        Logic game = new Logic(new FakeGUI(), board);

        assertFalse(game.isFinished());
    }

    /**
     * test move token
     */
    @Test
    public void test_moveToken_putTokenInorder1() {
        String board = """
                0 0 0 0 0 0
                0 0 0 0 0 0
                0 0 0 0 0 0
                0 0 0 0 0 0
                0 0 0 0 0 0
                0 0 0 0 0 0
                """;

        Logic game = new Logic(new FakeGUI(), board);
        Move move_hz_1 = new Move(Token.SUN, new Position(0, 0));
        Move move_hz_2 = new Move(Token.CROSS, new Position(1, 0));
        Move move_hz_3 = new Move(Token.TRIANGLE, new Position(2, 0));
        Move move_hz_4 = new Move(Token.SQUARE, new Position(3, 0));
        Move move_hz_5 = new Move(Token.PENTAGON, new Position(4, 0));
        Move move_hz_6 = new Move(Token.STAR, new Position(5, 0));
        Move move_1 = new Move(Token.SUN, new Position(0, 0));
        Move move_2 = new Move(Token.CROSS, new Position(0, 1));
        Move move_3 = new Move(Token.TRIANGLE, new Position(0, 2));
        Move move_4 = new Move(Token.SQUARE, new Position(0, 3));
        Move move_5 = new Move(Token.PENTAGON, new Position(0, 4));
        Move move_6 = new Move(Token.STAR, new Position(0, 5));

        game.moveToken(move_hz_1);
        game.moveToken(move_hz_2);
        game.moveToken(move_hz_3);
        game.moveToken(move_hz_4);
        game.moveToken(move_hz_5);
        game.moveToken(move_hz_6);
        game.moveToken(move_1);
        game.moveToken(move_2);
        game.moveToken(move_3);
        game.moveToken(move_4);
        game.moveToken(move_5);
        game.moveToken(move_6);

        assertEquals(1, game.getBoard().getCell(0, 0).getIndex());
        assertEquals(2, game.getBoard().getCell(1, 0).getIndex());
        assertEquals(3, game.getBoard().getCell(2, 0).getIndex());
        assertEquals(4, game.getBoard().getCell(3, 0).getIndex());
        assertEquals(5, game.getBoard().getCell(4, 0).getIndex());
        assertEquals(6, game.getBoard().getCell(5, 0).getIndex());
        assertEquals(1, game.getBoard().getCell(0, 0).getIndex());
        assertEquals(2, game.getBoard().getCell(0, 1).getIndex());
        assertEquals(3, game.getBoard().getCell(0, 2).getIndex());
        assertEquals(4, game.getBoard().getCell(0, 3).getIndex());
        assertEquals(5, game.getBoard().getCell(0, 4).getIndex());
        assertEquals(6, game.getBoard().getCell(0, 5).getIndex());

    }

    @Test
    public void test_moveToken_putTokenInOrder2() {
        String board = """
                0 0 0 0 0 0
                0 0 0 0 0 0
                0 0 0 0 0 0
                0 0 0 0 0 0
                0 0 0 0 0 0
                0 0 0 0 0 0
                """;

        Logic game = new Logic(new FakeGUI(), board);
        Move move_1 = new Move(Token.SUN, new Position(0, 0));
        Move move_2 = new Move(Token.CROSS, new Position(0, 1));
        Move move_3 = new Move(Token.TRIANGLE, new Position(0, 2));
        Move move_4 = new Move(Token.SQUARE, new Position(0, 3));
        Move move_5 = new Move(Token.PENTAGON, new Position(0, 4));
        Move move_6 = new Move(Token.STAR, new Position(0, 5));


        game.moveToken(move_1);
        game.moveToken(move_2);
        game.moveToken(move_3);
        game.moveToken(move_4);
        game.moveToken(move_5);
        game.moveToken(move_6);
        assertEquals(1, game.getBoard().getCell(0, 0).getIndex());
        assertEquals(2, game.getBoard().getCell(0, 1).getIndex());
        assertEquals(3, game.getBoard().getCell(0, 2).getIndex());
        assertEquals(4, game.getBoard().getCell(0, 3).getIndex());
        assertEquals(5, game.getBoard().getCell(0, 4).getIndex());
        assertEquals(6, game.getBoard().getCell(0, 5).getIndex());

    }

    @Test
    public void test_moveToken_putToken_withMove() {

        String board = """
                1 2 3 4 5 6
                0 0 0 0 0 0
                0 0 0 0 0 0
                0 0 0 0 0 0
                0 0 0 0 0 0
                0 0 0 0 0 0
                """;
        Logic game = new Logic(new FakeGUI(), board);
        Move move_1 = new Move(Token.STAR, new Position(0, 0));

        game.moveToken(move_1);

        assertEquals(1, game.getBoard().getCell(0, 0).getIndex());
        assertEquals(2, game.getBoard().getCell(0, 1).getIndex());
        assertEquals(3, game.getBoard().getCell(0, 2).getIndex());
        assertEquals(4, game.getBoard().getCell(0, 3).getIndex());
        assertEquals(5, game.getBoard().getCell(0, 4).getIndex());
        assertEquals(6, game.getBoard().getCell(0, 5).getIndex());


    }

    /**
     * correct move proposal for the remover
     */
    @Test
    public void test_removerToken() {
        String board = """
                1 0 3 4 6 6
                1 2 0 4 1 6
                0 2 3 4 2 0
                0 2 3 4 3 6
                0 0 4 0 4 6
                0 0 4 0 5 6
                """;

        Logic game = new Logic(new FakeGUI(), board);

        Hand hand = new Hand(new Token[]{Token.STAR, Token.SUN, Token.REMOVE, Token.CROSS});
        Position remove = new Position(0, 2);
        game.removeToken(remove, hand);

        assertEquals(0, game.getBoard().getCell(0, 2).getIndex());
    }

    /**
     * correct move proposal for the remover to all clear
     */
    @Test
    public void test_removerToken_allClear() {
        String board = """
                1 0 3 4 6 6
                1 2 0 4 1 6
                0 2 3 4 2 0
                0 2 3 4 3 6
                0 0 4 0 4 6
                0 0 4 0 5 6
                """;

        Logic game = new Logic(new FakeGUI(), board);

        Hand hand = new Hand(new Token[]{Token.STAR, Token.SUN, Token.REMOVE, Token.CROSS});
        Position remove = new Position(0, 2);
        game.removeToken(remove, hand);

        assertEquals(0, game.getBoard().getCell(0, 2).getIndex());

    }

    /**
     * correct move proposal for the shifter
     */
    @Test
    public void test_shifterToken() {
        String board = """
                1 0 3 4 6 6
                1 2 0 4 1 6
                0 2 3 4 2 0
                0 2 3 4 3 6
                0 0 4 0 4 6
                0 0 4 0 5 6
                """;

        String expectedBoard = """
                0 0 3 4 6 6
                1 2 0 4 1 6
                0 2 3 4 2 0
                0 2 3 4 3 6
                0 1 4 0 4 6
                0 0 4 0 5 6
                """;
        Logic game = new Logic(new FakeGUI(), board);
        Logic expectedGame = new Logic(new FakeGUI(), expectedBoard);
        GameBoard expectedGameBoard = expectedGame.getBoard();

        Position pos = new Position(0, 0);
        Position anotherPos = new Position(4, 1);
        game.shiftToken(pos, anotherPos);

        assertArrayEquals(expectedGameBoard.getField(), game.getBoard().getField());
    }


    /**
     * correct move suggestion for the exchanger
     */
    @Test
    public void test_exchangerToken() {

        String board = """
                1 0 3 4 6 6
                1 2 0 4 1 6
                0 2 3 4 2 0
                0 2 3 4 3 6
                0 0 4 0 4 6
                0 0 4 0 5 6
                """;

        Logic game = new Logic(new FakeGUI(), board);

        Position pos = new Position(3, 1);
        Position anotherPos = new Position(3, 2);

        game.exchangeToken(pos, anotherPos);

        assertEquals(3, game.getBoard().getCell(3, 1).getIndex());
        assertEquals(2, game.getBoard().getCell(3, 2).getIndex());
    }

    /**
     * correct move suggestion for the replacer
     */
    @Test
    public void test_replacerToken() {
        String board = """
                1 0 3 4 6 6
                1 2 0 4 1 6
                0 2 3 4 2 0
                0 2 3 4 3 6
                0 0 4 0 4 6
                0 0 4 0 5 6
                """;

        Logic game = new Logic(new FakeGUI(), board);

        Position pos = new Position(2, 1);
        Hand hand = new Hand(new Token[]{Token.STAR, Token.SUN, Token.TRIANGLE, Token.REPLACER});

        game.replaceToken(pos, 0, hand);

        assertEquals(6, game.getBoard().getCell(2, 1).getIndex());


    }

    /**
     * there is no lines to complete six tokens, then the best move need to consider to the best score of evey empty space.
     */
    @Test
    public void test_theBestMove_greenTeam_achieveTheBestScore_with0Score() {
        String board = """
                0 4 0 0 0 0
                0 0 0 0 0 0
                0 0 0 0 0 0
                0 0 0 0 0 0
                0 0 0 0 0 0
                0 0 0 0 0 0
                """;

        Logic game = new Logic(new FakeGUI(), board);

        Hand hand = new Hand(new Token[]{Token.STAR, Token.STAR, Token.REMOVE, Token.SQUARE});

        Move move = game.theBestMove(hand, game.getBoard(), Logic.Team.GREEN);

        assertEquals(4, move.getToken().getIndex());
        assertEquals(0, move.getPosition().getX());
        assertEquals(0, move.getPosition().getY());

    }

    /**
     * green team round, while there is a possible line for orange team to complete,
     * green team need to prevent the line by placing a token which is not the same token of the line.
     */
    @Test
    public void test_theBestMove_greenTeam_preventSix() {
        String board = """
                1 0 3 4 0 6
                1 2 0 4 1 6
                0 2 3 4 2 0
                0 2 3 4 3 6
                0 0 4 0 4 6
                0 0 4 0 5 6
                """;

        Logic game = new Logic(new FakeGUI(), board);

        Hand hand = new Hand(new Token[]{Token.STAR, Token.SUN, Token.CROSS, Token.SQUARE});
        Move expectedMove = new Move(Token.SUN, new Position(2, 5));

        Move move = game.theBestMove(hand, game.getBoard(), Logic.Team.GREEN);

        assertEquals(1, move.getToken().getIndex());
        assertEquals(2, move.getPosition().getX());
        assertEquals(5, move.getPosition().getY());
        assertEquals(expectedMove.getToken(), move.getToken());

    }

    /**
     * green team,
     * while there is a possible line to complete six tokens,
     * the green team need to place a token on the empty space of that line from hand.
     */
    @Test
    public void test_theBestMove_greenTeam_hasSixPossible() {
        String board = """
                6 6 0 6 6 6
                0 1 2 3 4 5
                4 4 4 0 0 0
                3 1 3 3 4 4
                0 2 2 2 0 6
                1 1 0 4 0 5
                """;

        Logic game = new Logic(new FakeGUI(), board);

        Hand hand = new Hand(new Token[]{Token.STAR, Token.SUN, Token.CROSS, Token.SQUARE});
        Move expectedMove = new Move(Token.STAR, new Position(0, 2));

        Move move = game.theBestMove(hand, game.getBoard(), Logic.Team.GREEN);

        assertEquals(6, move.getToken().getIndex());
        assertEquals(0, move.getPosition().getX());
        assertEquals(2, move.getPosition().getY());
        assertEquals(expectedMove.getToken(), move.getToken());

    }

    /**
     * orange team,
     * while there is a possible line to complete six tokens,
     * the orange team need to place a token on the empty space of that line from hand.
     */
    @Test
    public void test_theBestMove_orangeTeam_hasSixPossible() {
        String board = """
                1 0 3 4 0 6
                1 2 0 4 1 6
                0 2 3 4 2 0
                0 2 3 4 3 6
                0 0 4 0 4 6
                0 0 4 0 5 6
                """;

        Logic game = new Logic(new FakeGUI(), board);

        Hand hand = new Hand(new Token[]{Token.STAR, Token.SUN, Token.CROSS, Token.SQUARE});
        Move expectedMove = new Move(Token.STAR, new Position(2, 5));

        Move move = game.theBestMove(hand, game.getBoard(), Logic.Team.ORANGE);

        assertEquals(6, move.getToken().ordinal());
        assertEquals(2, move.getPosition().getX());
        assertEquals(5, move.getPosition().getY());
        assertEquals(expectedMove.getToken(), move.getToken());

    }

    /**
     * orange team round, while there is a possible line for green team to complete,
     * orange team need to prevent the line by placing a token which is not the same token of the line.
     */
    @Test
    public void test_theBestMove_orangeTeam_preventSix() {
        String board = """
                6 6 0 6 6 6
                0 1 2 3 4 5
                4 4 4 0 0 0
                3 1 3 3 4 4
                0 2 2 2 0 6
                1 1 0 4 0 5
                """;

        Logic game = new Logic(new FakeGUI(), board);

        Hand hand = new Hand(new Token[]{Token.STAR, Token.SUN, Token.CROSS, Token.SQUARE});
        Move expectedMove = new Move(Token.SUN, new Position(0, 2));

        Move move = game.theBestMove(hand, game.getBoard(), Logic.Team.ORANGE);

        assertEquals(1, move.getToken().ordinal());
        assertEquals(0, move.getPosition().getX());
        assertEquals(2, move.getPosition().getY());
        assertEquals(expectedMove.getToken(), move.getToken());

    }

    /**
     * there is no lines to complete six tokens, then the best move need to consider to the best score of evey empty space.
     */
    @Test
    public void test_theBestMove_greenTeam_achieveTheBestScore() {
        String board = """
                3 3 2 1 5 2
                0 4 5 0 2 5
                3 1 1 4 5 6
                6 2 0 0 6 6
                5 5 5 3 6 0
                1 6 1 3 1 2
                """;

        Logic game = new Logic(new FakeGUI(), board);

        Hand hand = new Hand(new Token[]{Token.STAR, Token.SUN, Token.EXCHANGE, Token.SQUARE});

        Move move = game.theBestMove(hand, game.getBoard(), Logic.Team.GREEN);

        assertEquals(6, move.getToken().getIndex());
        assertEquals(3, move.getPosition().getX());
        assertEquals(2, move.getPosition().getY());

    }

    /**
     * there is no lines to complete six tokens, then the best move need to consider to the best score of evey empty space.
     */
    @Test
    public void test_theBestMove_orangeTeam_achieveTheBestScore() {
        String board = """
                3 3 2 1 5 2
                0 4 5 0 2 5
                3 1 1 4 5 6
                6 2 0 0 6 6
                5 5 5 3 6 0
                1 6 1 3 1 2
                """;

        Logic game = new Logic(new FakeGUI(), board);

        Hand hand = new Hand(new Token[]{Token.STAR, Token.SUN, Token.CROSS, Token.SQUARE});

        Move move = game.theBestMove(hand, game.getBoard(), Logic.Team.ORANGE);

        assertEquals(1, move.getToken().ordinal());
        assertEquals(3, move.getPosition().getX());
        assertEquals(2, move.getPosition().getY());

    }

    /**
     * there is no lines to complete six tokens, then the best move need to consider to the best score of evey empty space.
     */
    @Test
    public void test_theBestMove_greenTeam_achieveTheBestScore_manyActionOnHand() {
        String board = """
                3 3 2 1 5 2
                0 4 5 0 2 5
                3 1 1 4 5 6
                6 2 0 0 6 6
                5 5 5 3 6 0
                1 6 1 3 1 2
                """;

        Logic game = new Logic(new FakeGUI(), board);

        Hand hand = new Hand(new Token[]{Token.EXCHANGE, Token.REMOVE, Token.EXCHANGE, Token.SQUARE});

        Move move = game.theBestMove(hand, game.getBoard(), Logic.Team.GREEN);

        assertEquals(4, move.getToken().ordinal());
        assertEquals(1, move.getPosition().getX());
        assertEquals(0, move.getPosition().getY());

    }

    /**
     * the best remove
     */
    @Test
    public void test_theBestRemove_Orange_hasSixPossible() {
        String board = """
                1 0 3 4 0 6
                1 2 0 4 1 6
                0 2 3 4 2 5
                0 2 3 4 3 6
                0 0 4 0 4 6
                0 0 4 0 5 6
                """;
        Logic game = new Logic(new FakeGUI(), board);

        Move theBestMove = game.theBestRemove_DealingSixToken(game.getBoard(), Logic.Team.ORANGE);

        assertEquals(5, game.getBoard().getCell(theBestMove.getPosition().getX(), theBestMove.getPosition().getY()).getIndex());
        assertEquals(2, theBestMove.getPosition().getX());
        assertEquals(5, theBestMove.getPosition().getY());

    }

    @Test
    public void test_theBestRemove_Green_hasSixPossible() {
        String board = """
                6 6 5 6 6 6
                0 1 2 3 4 5
                4 4 4 0 0 0
                3 1 3 3 4 4
                0 2 2 2 0 6
                1 1 0 4 0 5
                """;
        Logic game = new Logic(new FakeGUI(), board);

        Move theBestMove = game.theBestRemove_DealingSixToken(game.getBoard(), Logic.Team.GREEN);

        assertEquals(5, game.getBoard().getCell(theBestMove.getPosition().getX(), theBestMove.getPosition().getY()).getIndex());
        assertEquals(0, theBestMove.getPosition().getX());
        assertEquals(2, theBestMove.getPosition().getY());


    }

    @Test
    public void test_theBestRemove_Green_preventSix() {
        String board = """
                1 0 6 4 0 3
                1 2 6 4 1 3
                0 2 6 4 2 3
                0 2 0 4 3 4
                0 0 6 0 4 5
                0 0 6 0 5 6
                """;
        Logic game = new Logic(new FakeGUI(), board);

        Move theBestMove = game.theBestRemove_DealingSixToken(game.getBoard(), Logic.Team.GREEN);

        assertEquals(6, game.getBoard().getCell(theBestMove.getPosition().getX(), theBestMove.getPosition().getY()).getIndex());
        assertEquals(0, theBestMove.getPosition().getX());
        assertEquals(2, theBestMove.getPosition().getY());

    }

    @Test
    public void test_theBestRemove_Orange_preventSix() {
        String board = """
                3 3 1 4 4 4
                0 1 2 3 4 5
                4 4 4 0 0 0
                6 6 0 6 6 6
                0 2 2 2 0 6
                1 1 0 4 0 5
                """;
        Logic game = new Logic(new FakeGUI(), board);

        Move theBestMove = game.theBestRemove_DealingSixToken(game.getBoard(), Logic.Team.ORANGE);

        assertEquals(6, game.getBoard().getCell(theBestMove.getPosition().getX(), theBestMove.getPosition().getY()).getIndex());
        assertEquals(3, theBestMove.getPosition().getX());
        assertEquals(0, theBestMove.getPosition().getY());

    }

    @Test
    public void test_theBestRemove_Green_achieveTheBestScore() {
        String board = """
                0 3 0 0 0 3
                0 4 0 0 2 5
                3 0 0 4 0 0
                0 6 0 0 0 0
                5 0 5 0 0 0
                0 6 0 3 0 0
                """;
        Logic game = new Logic(new FakeGUI(), board);

        Hand hand = new Hand(new Token[]{Token.REPLACER, Token.EXCHANGE, Token.REMOVE, Token.SHIFTER});

        Move theBestMove = game.theBestRemove_achieveBestScore(hand, game.getBoard(), Logic.Team.GREEN);

        assertEquals(6, game.getBoard().getCell(theBestMove.getPosition().getX(), theBestMove.getPosition().getY()).getIndex());
        assertEquals(3, theBestMove.getPosition().getX());
        assertEquals(1, theBestMove.getPosition().getY());

    }

    @Test
    public void test_theBestRemove_Orange_achieveTheBestScore() {
        String board = """
                0 3 0 0 0 3
                0 4 0 0 2 5
                3 0 0 4 0 0
                0 6 0 0 0 0
                5 0 5 0 0 0
                0 6 0 3 0 0
                """;
        Logic game = new Logic(new FakeGUI(), board);

        Hand hand = new Hand(new Token[]{Token.STAR, Token.SUN, Token.REMOVE, Token.SQUARE});

        Move theBestMove = game.theBestRemove_achieveBestScore(hand, game.getBoard(), Logic.Team.ORANGE);

        assertEquals(3, game.getBoard().getCell(theBestMove.getPosition().getX(), theBestMove.getPosition().getY()).getIndex());
        assertEquals(0, theBestMove.getPosition().getX());
        assertEquals(1, theBestMove.getPosition().getY());

    }

    /**
     * the best SHIFTING
     */
    @Test
    public void test_theBestShift_Green_preventSix() {
        String board = """
                1 0 3 4 0 6
                1 2 0 4 1 6
                6 2 3 4 2 0
                0 2 3 4 3 6
                0 0 4 0 4 6
                0 0 4 0 5 6
                """;
        Logic game = new Logic(new FakeGUI(), board);

        Position[] theBestShift = game.theBestShift_dealingSixToken(game.getBoard(), Logic.Team.GREEN);

        Position theShiftedPosition = theBestShift[0];
        Position theTargetPosition = theBestShift[1];

        // check the position is same
        assertEquals(0, theShiftedPosition.getX());
        assertEquals(5, theShiftedPosition.getY());

        assertEquals(0, theTargetPosition.getX());
        assertEquals(1, theTargetPosition.getY());

    }

    @Test
    public void test_theBestShift_Orange_hasSixPossible() {
        String board = """
                1 0 3 4 0 6
                1 2 0 4 1 6
                0 2 3 4 2 0
                0 2 3 4 3 6
                6 0 4 0 4 6
                0 0 4 0 5 6
                """;
        Logic game = new Logic(new FakeGUI(), board);

        Position[] theBestShift = game.theBestShift_dealingSixToken(game.getBoard(), Logic.Team.ORANGE);

        Position theShiftedPosition = theBestShift[0];
        Position theTargetPosition = theBestShift[1];

        // check the position is same
        assertEquals(4, theShiftedPosition.getX());
        assertEquals(0, theShiftedPosition.getY());

        assertEquals(2, theTargetPosition.getX());
        assertEquals(5, theTargetPosition.getY());

    }

    @Test
    public void test_theBestShift_Green_hasSixPossible() {
        String board = """
                6 6 0 6 6 6
                0 1 2 3 4 5
                4 4 4 0 0 0
                3 1 3 3 4 4
                0 2 2 2 0 6
                1 1 0 4 0 5
                """;
        Logic game = new Logic(new FakeGUI(), board);

        Position[] theBestShift = game.theBestShift_dealingSixToken(game.getBoard(), Logic.Team.GREEN);

        Position theShiftedPosition = theBestShift[0];
        Position theTargetPosition = theBestShift[1];

        // check the position is same
        assertEquals(4, theShiftedPosition.getX());
        assertEquals(5, theShiftedPosition.getY());

        assertEquals(0, theTargetPosition.getX());
        assertEquals(2, theTargetPosition.getY());

    }

    @Test
    public void test_theBestShift_Orange_preventSix() {
        String board = """
                6 6 0 6 6 6
                0 1 2 3 4 5
                4 4 4 0 0 0
                3 1 3 3 4 4
                0 2 2 2 0 6
                1 1 0 4 0 5
                """;
        Logic game = new Logic(new FakeGUI(), board);

        Position[] theBestShift = game.theBestShift_dealingSixToken(game.getBoard(), Logic.Team.ORANGE);

        Position theShiftedPosition = theBestShift[0];
        Position theTargetPosition = theBestShift[1];

        // check the position is same
        assertEquals(0, theShiftedPosition.getX());
        assertEquals(0, theShiftedPosition.getY());

        assertEquals(1, theTargetPosition.getX());
        assertEquals(0, theTargetPosition.getY());

    }

    @Test
    public void test_theBestShift_Green_achieveTheBestScore() {
        String board = """
                0 3 0 0 0 3
                0 4 0 0 2 5
                3 0 0 4 0 0
                0 6 0 0 0 0
                5 0 5 0 0 0
                0 6 0 3 0 0
                """;

        Logic game = new Logic(new FakeGUI(), board);

        Position[] theBestShift = game.theBestShift_achieveBestScore(game.getBoard(), Logic.Team.GREEN);

        Position theShiftedPosition = theBestShift[0];
        Position theTargetPosition = theBestShift[1];

        // check the position is same
        assertEquals(1, theShiftedPosition.getX());
        assertEquals(5, theShiftedPosition.getY());

        assertEquals(4, theTargetPosition.getX());
        assertEquals(1, theTargetPosition.getY());

    }

    @Test
    public void test_theBestShift_Orange_achieveTheBestScore() {
        String board = """
                0 3 0 0 0 3
                0 4 0 0 2 5
                3 0 0 4 0 0
                0 6 0 0 0 0
                5 0 5 0 0 0
                0 6 0 3 0 0
                """;
        Logic game = new Logic(new FakeGUI(), board);

        Position[] theBestShift = game.theBestShift_achieveBestScore(game.getBoard(), Logic.Team.ORANGE);

        Position theShiftedPosition = theBestShift[0];
        Position theTargetPosition = theBestShift[1];

        // check the position is same
        assertEquals(0, theShiftedPosition.getX());
        assertEquals(1, theShiftedPosition.getY());

        assertEquals(1, theTargetPosition.getX());
        assertEquals(0, theTargetPosition.getY());

    }

    /**
     * the best exchange
     */
    @Test
    public void test_theBestExchange_Green_preventSix() {
        String board = """
                1 0 3 4 0 6
                1 2 0 4 1 6
                0 2 3 4 2 5
                0 2 3 4 3 6
                0 6 4 0 4 6
                0 0 4 0 5 6
                """;

        Logic game = new Logic(new FakeGUI(), board);

        Position[] theBestExchange = game.theBestExchange_dealingSixToken(game.getBoard(), Logic.Team.GREEN);

        Position theShiftedPosition = theBestExchange[0];
        Position theTargetPosition = theBestExchange[1];

        // check the position is same
        assertEquals(0, theShiftedPosition.getX());
        assertEquals(5, theShiftedPosition.getY());

        assertEquals(4, theTargetPosition.getX());
        assertEquals(2, theTargetPosition.getY());

    }

    @Test
    public void test_theBestExchange_Orange_hasSixPossible() {
        String board = """
                1 0 3 4 0 6
                1 2 0 4 1 6
                0 2 3 4 2 5
                0 2 3 4 3 6
                0 6 4 0 4 6
                0 0 4 0 5 6
                """;

        Logic game = new Logic(new FakeGUI(), board);

        Position[] theBestExchange = game.theBestExchange_dealingSixToken(game.getBoard(), Logic.Team.ORANGE);

        Position theShiftedPosition = theBestExchange[0];
        Position theTargetPosition = theBestExchange[1];

        // check the position is same
        assertEquals(4, theShiftedPosition.getX());
        assertEquals(1, theShiftedPosition.getY());

        assertEquals(2, theTargetPosition.getX());
        assertEquals(5, theTargetPosition.getY());
    }

    @Test
    public void test_theBestExchange_Green_hasSixPossible() {
        String board = """
                4 4 4 0 0 0
                0 1 2 3 4 5
                6 6 6 2 6 6
                3 1 3 3 4 4
                0 2 2 2 0 6
                1 1 0 4 0 5
                """;

        Logic game = new Logic(new FakeGUI(), board);

        Position[] theBestShift = game.theBestExchange_dealingSixToken(game.getBoard(), Logic.Team.GREEN);

        Position theShiftedPosition = theBestShift[0];    //
        Position theTargetPosition = theBestShift[1];

        // check the position is same
        assertEquals(4, theShiftedPosition.getX());
        assertEquals(5, theShiftedPosition.getY());

        assertEquals(2, theTargetPosition.getX());
        assertEquals(3, theTargetPosition.getY());

    }

    @Test
    public void test_theBestExchange_Orange_preventSix() {
        String board = """
                4 4 4 0 0 0
                0 1 2 3 4 5
                6 6 6 0 6 6
                3 1 3 3 4 4
                0 2 2 2 0 6
                1 1 0 4 0 5
                """;
        Logic game = new Logic(new FakeGUI(), board);

        Position[] theBestShift = game.theBestExchange_dealingSixToken(game.getBoard(), Logic.Team.ORANGE);

        Position theShiftedPosition = theBestShift[0];
        Position theTargetPosition = theBestShift[1];

        // check the position is same
        assertEquals(2, theShiftedPosition.getX());
        assertEquals(0, theShiftedPosition.getY());

        assertEquals(1, theTargetPosition.getX());
        assertEquals(5, theTargetPosition.getY());

    }

    @Test
    public void test_theBestExchange_Green_achieveTheBestScore() {
        String board = """
                0 3 0 0 0 3
                0 4 0 0 2 5
                3 0 0 4 0 0
                0 6 0 0 0 0
                5 0 5 0 0 0
                0 6 0 3 0 0
                """;
        Logic game = new Logic(new FakeGUI(), board);

        Position[] theBestShift = game.theBestExchange_achieveBestScore(game.getBoard(), Logic.Team.GREEN);

        Position theShiftedPosition = theBestShift[0];
        Position theTargetPosition = theBestShift[1];

        // check the position is same
        assertEquals(1, theShiftedPosition.getX());
        assertEquals(4, theShiftedPosition.getY());

        assertEquals(2, theTargetPosition.getX());
        assertEquals(3, theTargetPosition.getY());

    }

    @Test
    public void test_theBestExchange_Orange_achieveTheBestScore() {
        String board = """
                0 3 0 0 0 3
                0 4 0 0 2 5
                3 0 0 4 0 0
                0 6 0 0 0 0
                5 0 5 0 0 0
                0 6 0 3 0 0
                """;
        Logic game = new Logic(new FakeGUI(), board);

        Position[] theBestShift = game.theBestExchange_achieveBestScore(game.getBoard(), Logic.Team.ORANGE);

        Position theShiftedPosition = theBestShift[0];
        Position theTargetPosition = theBestShift[1];

        // check the position is same
        assertEquals(0, theShiftedPosition.getX());
        assertEquals(5, theShiftedPosition.getY());

        assertEquals(4, theTargetPosition.getX());
        assertEquals(0, theTargetPosition.getY());

    }

    /**
     * replace
     */
    @Test
    public void test_theBestReplace_Green_hasSixPossible() {
        String board = """
                4 4 4 0 0 0
                0 1 2 3 4 5
                6 6 2 6 6 6
                3 1 3 3 4 4
                0 2 2 2 0 6
                1 1 0 4 0 5
                """;
        Logic game = new Logic(new FakeGUI(), board);

        Hand hand = new Hand(new Token[]{Token.STAR, Token.SUN, Token.REPLACER, Token.SQUARE});

        Move theBestReplace = game.theBestReplace_dealingSixToken(hand, game.getBoard(), Logic.Team.GREEN);

        // check the position is same
        assertEquals(2, theBestReplace.getPosition().getX());
        assertEquals(2, theBestReplace.getPosition().getY());
        assertEquals(6, theBestReplace.getToken().getIndex());

    }

    @Test
    public void test_theBestReplace_Orange_hasSixPossible() {
        String board = """
                1 0 3 4 0 6
                1 2 0 4 1 6
                0 2 3 4 2 5
                0 2 3 4 3 6
                0 6 4 0 4 6
                0 0 4 0 5 6
                """;

        Logic game = new Logic(new FakeGUI(), board);
        Hand hand = new Hand(new Token[]{Token.STAR, Token.SUN, Token.REPLACER, Token.SQUARE});

        Move theBestReplace = game.theBestReplace_dealingSixToken(hand, game.getBoard(), Logic.Team.ORANGE);

        // check the position is same
        assertEquals(2, theBestReplace.getPosition().getX());
        assertEquals(5, theBestReplace.getPosition().getY());
        assertEquals(6, theBestReplace.getToken().getIndex());
    }


    @Test
    public void test_theBestReplace_Green_preventSix() {
        String board = """
                1 0 3 4 0 6
                1 2 0 4 1 6
                0 2 3 4 2 5
                0 2 3 4 3 6
                0 0 4 0 4 6
                0 0 4 0 5 6
                """;

        Logic game = new Logic(new FakeGUI(), board);

        Hand hand = new Hand(new Token[]{Token.STAR, Token.SUN, Token.REPLACER, Token.SQUARE});

        Move theBestReplace = game.theBestReplace_dealingSixToken(hand, game.getBoard(), Logic.Team.GREEN);

        // check the position is same
        assertEquals(0, theBestReplace.getPosition().getX());
        assertEquals(5, theBestReplace.getPosition().getY());
        assertEquals(1, theBestReplace.getToken().getIndex());

    }

    @Test
    public void test_theBestReplace_Orange_preventSix() {
        String board = """
                4 4 4 0 0 0
                0 1 2 3 4 5
                6 6 0 6 6 6
                3 1 3 3 4 4
                0 2 2 2 0 6
                1 1 0 4 0 5
                """;
        Logic game = new Logic(new FakeGUI(), board);

        Hand hand = new Hand(new Token[]{Token.STAR, Token.SUN, Token.REPLACER, Token.SQUARE});

        Move theBestReplace = game.theBestReplace_dealingSixToken(hand, game.getBoard(), Logic.Team.ORANGE);

        // check the position is same
        assertEquals(2, theBestReplace.getPosition().getX());
        assertEquals(0, theBestReplace.getPosition().getY());
        assertEquals(1, theBestReplace.getToken().getIndex());

    }

}


