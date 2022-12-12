package logic;

import com.google.gson.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * this is a game class
 * a class for creating logic of the game and also the gameBoard element
 * <p>
 *
 * @author chien-hsun
 */
public class Logic {

    private static final int NO_PLAYER = 0;
    private static final int TWO_SAME_POINT = 1;
    private static final int THREE_SAME_POINT = 3;
    private static final int FOUR_SAME_POINT = 5;
    private static final int FIVE_SAME_POINT = 7;
    private static final int ALL_DIFFERENT_POINT = 6;

    public static final int PLAYER_MIN = 2;
    public static final int PLAYER_MAX = 4;
    public static final int TOKEN_AMOUNT = 4;

    /**
     * enum class two team
     */
    public enum Team {
        ORANGE, GREEN
    }

    /**
     * a player 1d array consists of every player
     */
    private Players[] players;

    /**
     * players number
     */
    private int playerCount;

    /**
     * connection to gui
     */
    private final GUIConnector gui;

    /**
     * a board class
     */
    private final GameBoard Board;
    /**
     * the current player index
     */
    private int idxOfCurrentPlayer;
    /**
     * an int array of action token
     */
    private int[] usedActionList;
    /**
     * an action token pack
     */
    public Pack actionTokenPack;
    /**
     * a symbol token pack
     */
    public Pack symbolTokenPack;
    /**
     * a token deck
     */
    public Pack deck;
    /**
     * duration time of the highlighted area
     */
    private double duration;

    /**
     * drag position
     */
    private Position drag_position = new Position();

    /**
     * position for replacing hand
     */
    private Position storeReplaceOnHand = new Position();
    /**
     * a chosen Token for identify the token picked from hand
     */
    private Token chosenToken = Token.NONE;
    /**
     * record board Position. this used for AI
     */
    private Position[] recordAIBoardPos = new Position[2];

    /**
     * record the symbol token on the board from AI player
     */
    private Move symbolMove = new Move(chosenToken, drag_position);

    /**
     * constructor of game board.
     *
     * @param columns          column
     * @param rows             row
     * @param gui              javafx gui
     * @param playerCount      a count number of player
     * @param name             array of player names
     * @param com              boolean array of AI player
     * @param usedActionList   int array of the action list
     * @param idxCurrentPlayer idx of the current player
     * @param duration         time duration of highlighting
     */
    public Logic(int columns, int rows, GUIConnector gui, int playerCount, String[] name, boolean[] active, boolean[] com, int[] usedActionList, int idxCurrentPlayer, double duration, Position drag_position, Position storeReplaceOnHand) {
        players = new Players[PLAYER_MAX];
        this.gui = gui;
        this.playerCount = playerCount;
        this.usedActionList = usedActionList;
        this.Board = new GameBoard(columns, rows); // a new board
        this.idxOfCurrentPlayer = idxCurrentPlayer;
        this.duration = duration;
        this.drag_position = drag_position;
        this.storeReplaceOnHand = storeReplaceOnHand;
        //cove every cell with none img.
        for (int row = 0; row < Board.getField().length; row++) {
            for (int col = 0; col < Board.getField()[row].length; col++) {
                this.getBoard().setCell(Token.NONE, row, col);
                gui.coverNoneToken(row, col);
            }
        }

        deck = Pack.createAPackOfEveryTokens();
        actionTokenPack = Pack.createAPackOfActionTokens();
        symbolTokenPack = Pack.createAPackOfSymbolTokens();
        initialisePlayer(name, active, com);

        usedActionList = new int[TOKEN_AMOUNT];
        Arrays.fill(usedActionList, 0);
        updateRemainActionTokenDisplay();

        // set team score
        updatePoint();
        gui.hiddenDeck(idxCurrentPlayer);


    }

    /**
     * testing constructor
     */
    Logic(GUIConnector gui, String s) {
        this.gui = gui;
        this.Board = new GameBoard(s);
    }

    /**
     * getter players
     *
     * @return player type  array
     */
    public Players[] getPlayers() {
        return players;
    }

    /**
     * getter for a board
     *
     * @return a game board
     */
    public GameBoard getBoard() {
        return Board;
    }

    /**
     * getter for index current player
     *
     * @return int value
     */
    public int getIndexCurrentPlayer() {
        return this.idxOfCurrentPlayer;
    }

    /**
     * setter for idx of current player
     *
     * @param idxOfCurrentPlayer idx of current player
     */
    public void setIndexCurrentPlayer(int idxOfCurrentPlayer) {
        this.idxOfCurrentPlayer = idxOfCurrentPlayer;
    }

    /**
     * setting player amount
     *
     * @param playerCount player amount
     */
    public void setPlayerAccount(int playerCount) {
        this.playerCount = playerCount;
    }

    /**
     * convert json to java object
     *
     * @param file given file
     */
    public void jsonToJavaUsingGson(File file) {

        Gson gson = new GsonBuilder().create();

        try (Reader reader = new FileReader(file)) {
            GraphDataJSON graphData = gson.fromJson(reader, GraphDataJSON.class);

            // set player 1-4
            for (int i = 0; i < PLAYER_MAX; i++) {
                players[i].setName(graphData.getPlayers().get(i).name);
                players[i].setIsAI(graphData.getPlayers().get(i).isAI);
                players[i].setIsActive(graphData.getPlayers().get(i).isActive);
                Hand hand = new Hand();
                for (int index : graphData.getPlayers().get(i).hand) {
                    hand.handList.add(Token.getToken(index));
                }
                players[i].setHand(hand);
            }

            // set current player
            idxOfCurrentPlayer = graphData.getCurrPlayer();

            usedActionList = graphData.getUsedActionTiles();

            // clear field list
            // set new field
            Token[][] field = this.getBoard().getField();

            int[][] jsonField = graphData.getField();

            for (int i = 0; i < jsonField.length; i++) {
                for (int j = 0; j < jsonField[i].length; j++) {
                    field[i][j] = Token.getToken(jsonField[i][j]);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * convert java to json
     *
     * @param file input file
     */
    public void javaToJSONUsingGson(File file) {
        GsonBuilder builder = new GsonBuilder();

        Gson gson = builder.setPrettyPrinting().create();

        GraphDataJSON graphDataJSON = createJsonObject();
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(graphDataJSON, writer);
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * transferring java object to gson
     *
     * @return a graphDataJson object
     */
    private GraphDataJSON createJsonObject() {

        GraphDataJSON graphDataJSON = new GraphDataJSON();
        graphDataJSON.setCurrPlayer(idxOfCurrentPlayer);
        graphDataJSON.setField(getBoard().getIntField());
        graphDataJSON.setUsedActionTiles(usedActionList);
        List<GraphDataJSON.gsonPlayer> playerList = new ArrayList<>(players.length);

        for (Players player : players) {
            GraphDataJSON.gsonPlayer gsonPlayer = new GraphDataJSON.gsonPlayer();
            gsonPlayer.name = player.getName();
            gsonPlayer.isActive = player.getIsActive();
            gsonPlayer.isAI = player.getIsAI();
            gsonPlayer.hand = player.getHand().handToIntArray();
            playerList.add(gsonPlayer);
        }

        graphDataJSON.setPlayers(playerList);

        return graphDataJSON;

    }

    /**
     * Auxiliary method
     * scoring points
     *
     * @param numberOfSameTokens number amounts of same tokens
     * @return the score point
     */
    private int scoringByGivenAToken(int numberOfSameTokens) {
        int score = 0;
        switch (numberOfSameTokens) {
            case 2 -> score = score + TWO_SAME_POINT;
            case 3 -> score = score + THREE_SAME_POINT;
            case 4 -> score = score + FOUR_SAME_POINT;
            case 5 -> score = score + FIVE_SAME_POINT;
        }
        return score;
    }

    /**
     * using hasDuplicated method, hasAllDifferent to score
     *
     * <p>
     * <p>
     * [2,3,4,5,1,3]
     *
     * @param index a row index
     * @return point for the array
     */
    public int scoringALine(int index, Team teamColor) {
        int score = 0;
        Team row = Team.GREEN;
        Team col = Team.ORANGE;
        GameBoard board = this.getBoard();

        // select the teamColor of the array
        int[] linesArray = new int[board.getField().length];
        if (teamColor.equals(row)) {
            linesArray = board.getAListOfSameRow(index);
        } else if (teamColor.equals(col)) {
            linesArray = board.getAListOfSameColumn(index);
        }

        score = scoreALineWithAnArray(board, linesArray, score);

        return score;
    }

    /**
     * scoring a line
     *
     * @param board      game board
     * @param linesArray a line
     * @param score      score points
     * @return a score
     */
    private int scoreALineWithAnArray(GameBoard board, int[] linesArray, int score) {
        // get all appear frequency of same tokens from given array
        int[] differentSetOfSameToken = board.getNumOfDifferentSetTokens(linesArray);
        //check if the board is empty
        if (board.isBoardEmpty()) {
            score = 0;
        } else {
            if (board.hasDuplicatedTokens(linesArray)) {
                for (int j : differentSetOfSameToken) {
                    score = score + scoringByGivenAToken(j);
                }
            } else if (board.hasAllDifferentTokens(linesArray)) {
                score = score + ALL_DIFFERENT_POINT;
            }
        }
        return score;
    }


    /**
     * store each lines score in an array
     *
     * @param gameBoard a game board
     * @param teamColor team colour
     * @return an int array of team
     */
    private int[] getAllScoreFromATeam(GameBoard gameBoard, Team teamColor) {
        int[] scoreArray = new int[gameBoard.getField().length];

        for (int i = 0; i < scoreArray.length; i++) {
            scoreArray[i] = scoringALine(i, teamColor);
        }

        return scoreArray;
    }

    /**
     * get the total score for each team
     *
     * @param gameBoard Game board
     * @param teamColor String teamColor.
     * @return a score point
     */
    public int getTotalScore(GameBoard gameBoard, Team teamColor) {
        int totalScore = 0;
        int[] scoreArray = getAllScoreFromATeam(gameBoard, teamColor);

        for (int j : scoreArray) {
            totalScore = totalScore + j;
        }

        return totalScore;
    }


    /**
     * a method is used for deciding which team is the winner.
     * orange or green or Tie
     *
     * @return string value of winner team
     */
    public String getWinnerTeam() {
        String result = "";
        if (sixTokenExisted()) {
            String team = whoHasSixToken();
            result = result + team;
        } else if (getBoard().isFullBoard()) {
            int totalScore_green = getTotalScore(this.getBoard(), Team.GREEN);
            int totalScore_orange = getTotalScore(this.getBoard(), Team.ORANGE);
            if (totalScore_green == totalScore_orange) {
                result = result + "tie";
            } else if (totalScore_green > totalScore_orange) {
                result = result + "green";
            } else {
                result = result + "orange";
            }
        }
        return result;
    }

    /**
     * get the final score and store them in an int array
     *
     * @return an array of two team score
     */
    public int[] getFinalScore() {

        return new int[]{
                getTotalScore(getBoard(), Team.ORANGE), getTotalScore(getBoard(), Team.GREEN)
        };
    }

    /**
     * return a string who has six tokens
     * "green", "orange"
     * <p>
     * test case for this.
     *
     * @return a string who has six token
     */
    private String whoHasSixToken() {
        GameBoard board = this.getBoard();
        Token[][] field = board.getField();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < field.length; i++) {
            if (board.hasSixSameTokens(board.getAListOfSameRow(i))) {
                result.append("green");
            } else if (board.hasSixSameTokens(board.getAListOfSameColumn(i))) {
                result.append("orange");
            }
        }
        return result.toString();
    }

    /**
     * check if the game is finished.
     * Two situation,
     * first, game board is full,
     * second, there is a line full of same tokens.
     *
     * @return true, board is finished
     * false, board is not finished
     */
    public boolean isFinished() {
        GameBoard board = this.getBoard();
        if (board.isFullBoard()) {
            return true;
        } else return sixTokenExisted();
    }

    /**
     * boolean checking six token existed or not
     *
     * @return true, has six token
     * false, has no six token
     */
    public boolean sixTokenExisted() {
        GameBoard board = this.getBoard();
        Token[][] field = board.getField();
        for (int i = 0; i < field.length; i++) {
            if (board.hasSixSameTokens(board.getAListOfSameRow(i)) || board.hasSixSameTokens(board.getAListOfSameColumn(i))) {
                return true;
            }
        }
        return false;
    }


    /**
     * identify if the token on hand is not action token
     *
     * @param hand, a player hand
     * @return get all symbol token, this may not need to use.
     */
    private Token[] getSymbolTokenFromHand(Hand hand) {
        ArrayList<Token> arrayList = new ArrayList<>();
        for (int i = 0; i < hand.getNoOfTokensFromPack(); i++) {
            if (Token.SUN.getIndex() <= hand.getTokenList().get(i).getIndex() && hand.getTokenList().get(i).getIndex() <= Token.STAR.getIndex()) {
                arrayList.add(hand.getTokenList().get(i));
            }
        }

        Token[] tokens = new Token[arrayList.size()];
        for (int idx = 0; idx < tokens.length; idx++) {
            tokens[idx] = arrayList.get(idx);
        }

        return tokens;
    }


    /**
     * get all empty position on board
     *
     * @param board game board
     * @return an array of empty position
     */
    private Position[] getEmptyCurrPos(GameBoard board) {
        ArrayList<Position> list = new ArrayList<>();
        Token[][] field = board.getField();

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                Position pos = new Position(i, j);
                if (board.isPositionEmpty(pos)) {
                    list.add(pos);
                }
            }
        }

        Position[] posArr = new Position[list.size()];
        posArr = list.toArray(posArr);

        return posArr;
    }

    /**
     * get all token position on board
     *
     * @param board game board
     * @return position array
     */
    private Position[] getAllBoardTokenPos(GameBoard board) {
        List<Position> list = new ArrayList<>();
        Token[][] field = board.getField();

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                Position pos = new Position(i, j);
                if (!board.isPositionEmpty(pos)) {
                    list.add(pos);
                }
            }
        }

        Position[] posArr = new Position[list.size()];
        posArr = list.toArray(posArr);


        return posArr;
    }

    /**
     * When we are checking the opportunity of completing six token in a line or preventing other team to complete six,
     * the checking condition always do the whole board first. If the board contains onw of them, then it goes further.
     * Otherwise, get out two those condition and go straight down to the last condition which neither of two teams has six possibility.
     * <p>
     * And for the last part, always try to implement with two loops that one for every symbol token and another for all empty position.
     * <p>
     * and finally get the best move.
     * <p>
     * <p>
     * <p>
     * <p>
     * this method will return the best move by analysing all possible token on a hand and all the empty place on the board.
     *
     * @param hand  a player hand
     * @param board current game board
     * @return best move
     */
    public Move theBestMove(Hand hand, GameBoard board, Team team) {
        // get all symbol token from hand
        Token[] handAllTokens = getSymbolTokenFromHand(hand);

        Team oppositeTeam;
        Team currTeam;
        Token theBestToken;
        Position theBestPosition = new Position();
        int theMostOftenElement;
        // get all the empty position on the current board.
        Position[] emptyPositionArray = getEmptyCurrPos(board);
        // create an int array to store every new score which is calculated by a hand token and an empty position.
        int[][] recordPossibleScore = new int[handAllTokens.length][emptyPositionArray.length];
        int greenTotalScore;
        int orangeTotalScore;
        int sixPossibleToken;
        Position distinctPos_curr;
        Position distinctPos_Opp;

        if (team.equals(Team.GREEN)) {
            currTeam = Team.GREEN;
            oppositeTeam = Team.ORANGE;

            sixPossibleToken = board.getTheMostOftenElement(board.getAListOfSameRow(getIndexOfLinesHasChanceToSix(board, currTeam)));
            theMostOftenElement = board.getTheMostOftenElement(board.getAListOfSameColumn(getIndexOfLinesHasChanceToSix(board, oppositeTeam)));

        } else {
            currTeam = Team.ORANGE;
            oppositeTeam = Team.GREEN;

            sixPossibleToken = board.getTheMostOftenElement(board.getAListOfSameColumn(getIndexOfLinesHasChanceToSix(board, currTeam)));
            theMostOftenElement = board.getTheMostOftenElement(board.getAListOfSameRow(getIndexOfLinesHasChanceToSix(board, oppositeTeam)));

        }

        distinctPos_curr = getTheDistinctPosOfPossibleSixInLines(getIndexOfLinesHasChanceToSix(board, currTeam), board, currTeam);
        distinctPos_Opp = getTheDistinctPosOfPossibleSixInLines(getIndexOfLinesHasChanceToSix(board, oppositeTeam), board, oppositeTeam);

        if (boardHasChanceToCompleteSixToken(board, currTeam) && hand.containTheToken(Token.getToken(sixPossibleToken)) && board.isPositionEmpty(distinctPos_curr)) {

            theBestPosition.setX(distinctPos_curr.getX());
            theBestPosition.setY(distinctPos_curr.getY());

            theBestToken = Token.getToken(sixPossibleToken);


        } else if (boardHasChanceToCompleteSixToken(board, oppositeTeam) && board.isPositionEmpty(distinctPos_Opp)) {

            Position preventPos = getTheDistinctPosOfPossibleSixInLines(getIndexOfLinesHasChanceToSix(board, oppositeTeam), board, oppositeTeam);

            if (this.getBoard().getCell(preventPos.getX(), preventPos.getY()).equals(Token.NONE)) {
                theBestPosition.setX(preventPos.getX());
                theBestPosition.setY(preventPos.getY());
            }

            theBestToken = getAHandTokenNotTheSameTokenInAPossibleSixTokens(hand, Token.getToken(theMostOftenElement));

        } else {
            //Two for loops here to achieve the best score of the best move
            //First loop => every symbol tokens
            //Second loop => every empty positions
            // this for loop is run for same token on hand, so it possibly runs for four times or even less than four
            for (int token_idx = 0; token_idx < handAllTokens.length; token_idx++) {        // recordPossibleScore.length = 4
                // get an array of the best possible score.
                for (int pos_empty = 0; pos_empty < emptyPositionArray.length; pos_empty++) {
                    Token originalToken = board.getCell(emptyPositionArray[pos_empty].getX(), emptyPositionArray[pos_empty].getY());

                    board.setCell(handAllTokens[token_idx], emptyPositionArray[pos_empty].getX(), emptyPositionArray[pos_empty].getY());

                    greenTotalScore = getTotalScore(board, Team.GREEN);
                    orangeTotalScore = getTotalScore(board, Team.ORANGE);

                    if (team.equals(Team.GREEN)) {
                        recordPossibleScore[token_idx][pos_empty] = greenTotalScore - orangeTotalScore;
                    } else if (team.equals(Team.ORANGE)) {
                        recordPossibleScore[token_idx][pos_empty] = orangeTotalScore - greenTotalScore;
                    }
                    board.setCell(originalToken, emptyPositionArray[pos_empty].getX(), emptyPositionArray[pos_empty].getY());

                }
            }

            int[] indexOfHighestScore = getTheHighestScore(recordPossibleScore);

            theBestToken = handAllTokens[indexOfHighestScore[0]];
            theBestPosition = emptyPositionArray[indexOfHighestScore[1]];

        }
        return new Move(theBestToken, theBestPosition);
    }

    /**
     * store each score into an array
     *
     * @param recordPossibleScore two-dimensional array had all possible record
     * @return an array has the highest score
     */
    public int[] getTheHighestScore(int[][] recordPossibleScore) {
        int maximum = Integer.MIN_VALUE;
        int twoResult = 2;
        int[] result = new int[twoResult];
        for (int row = 0; row < recordPossibleScore.length; row++) {
            for (int col = 0; col < recordPossibleScore[0].length; col++) {
                if (recordPossibleScore[row][col] > maximum) {
                    maximum = recordPossibleScore[row][col];
                    result[0] = row;
                    result[1] = col;
                }
            }
        }
        return result;
    }

    /**
     * get the highest score from a one-dimensional array
     *
     * @param recordPossibleScore one-dimensional array
     * @return the highest score
     */
    private int getTheHighestScoreOfOneDimension(int[] recordPossibleScore) {
        int maximum = Integer.MIN_VALUE;
        int result = 0;
        for (int row = 0; row < recordPossibleScore.length; row++) {
            if (recordPossibleScore[row] > maximum) {
                maximum = recordPossibleScore[row];
                result = row;
            }

        }
        return result;
    }


    /**
     * get the first element which is not the same as the token on the line that has chance to fill with six
     *
     * @param theMostOftenToken the compare token
     * @return a token from hand
     */
    private Token getAHandTokenNotTheSameTokenInAPossibleSixTokens(Hand hand, Token theMostOftenToken) {
        Token resultToken = Token.NONE;

        for (int i = 0; i < hand.getNoOfTokensFromPack(); i++) {
            if (!hand.getATokenOnIndex(i).equals(theMostOftenToken) && !hand.getATokenOnIndex(i).equals(Token.REMOVE)
                && !hand.getATokenOnIndex(i).equals(Token.SHIFTER) && !hand.getATokenOnIndex(i).equals(Token.EXCHANGE)
                && !hand.getATokenOnIndex(i).equals(Token.REPLACER)) {
                return hand.getATokenOnIndex(i);
            }
        }

        return resultToken;
    }

    /**
     * record the first index of the token which is same as the most often token on a line
     *
     * @param indexOfLinesHasChanceToSix index of a line
     * @param board                      game board
     * @param team                       game team
     * @return a position of has possible to six
     */
    private Position getFirstOppositePosOfPossibleSix(int indexOfLinesHasChanceToSix, GameBoard board, Team team) {
        Position result = new Position();
        int index;
        int[] arrayOfLinesPossibleSix;
        if (team.equals(Team.GREEN)) {
            arrayOfLinesPossibleSix = board.getAListOfSameRow(indexOfLinesHasChanceToSix);
            for (int i = 0; i < arrayOfLinesPossibleSix.length; i++) {
                if (arrayOfLinesPossibleSix[i] == board.getTheMostOftenElement(arrayOfLinesPossibleSix)) {
                    index = i;
                    result.setX(indexOfLinesHasChanceToSix);
                    result.setY(index);
                    return result;
                }
            }

        } else if (team.equals(Team.ORANGE)) {
            arrayOfLinesPossibleSix = board.getAListOfSameColumn(indexOfLinesHasChanceToSix);
            for (int i = 0; i < arrayOfLinesPossibleSix.length; i++) {
                if (arrayOfLinesPossibleSix[i] == board.getTheMostOftenElement(arrayOfLinesPossibleSix)) {
                    index = i;
                    result.setX(index);
                    result.setY(indexOfLinesHasChanceToSix);
                    return result;
                }
            }

        }
        return result;
    }

    /**
     * check if this board has chance to have six token in a line regarding of the current team
     *
     * @param board game board
     * @param team  game team
     * @return true, board has chance
     * false, board has no chance
     */
    private boolean boardHasChanceToCompleteSixToken(GameBoard board, Team team) {
        boolean hasChance = false;
        if (team.equals(Team.GREEN)) {
            if (hasChanceToSix(getAllScoreFromATeam(board, team))) {
                hasChance = true;
            }
        } else if (team.equals(Team.ORANGE)) {
            if (hasChanceToSix(getAllScoreFromATeam(board, team))) {
                hasChance = true;
            }
        }
        return hasChance;
    }

    /**
     * get the index row/col has chance to get six tokens on the board
     *
     * @param board game board
     * @param team  game team
     * @return index of a line
     */
    private int getIndexOfLinesHasChanceToSix(GameBoard board, Team team) {
        int index = 0;
        int[] arrayOfScoreATeam = getAllScoreFromATeam(board, team);
        for (int element = 0; element < arrayOfScoreATeam.length; element++) {
            if (arrayOfScoreATeam[element] == FIVE_SAME_POINT) {
                index = element;
            }
        }
        return index;
    }

    /**
     * try to find the distinct position in a line which has chance to get six tokens
     *
     * @param indexOfLinesHasChanceToSix index of a line
     * @param board                      game board
     * @param team                       game team
     * @return a position
     */
    private Position getTheDistinctPosOfPossibleSixInLines(int indexOfLinesHasChanceToSix, GameBoard board, Team
            team) {
        Position record = new Position();
        if (team.equals(Team.GREEN)) {
            int[] sameRowArray = board.getAListOfSameRow(indexOfLinesHasChanceToSix);
            int theMostOftenNum = board.getTheMostOftenElement(sameRowArray);
            for (int row = 0; row < sameRowArray.length; row++) {
                if (sameRowArray[row] != theMostOftenNum) {
                    record.setX(indexOfLinesHasChanceToSix);
                    record.setY(row);
                }
            }
        } else if (team.equals(Team.ORANGE)) {
            int[] sameColumnArray = board.getAListOfSameColumn(indexOfLinesHasChanceToSix);
            int theMostOftenNum = board.getTheMostOftenElement(sameColumnArray);
            for (int col = 0; col < sameColumnArray.length; col++) {
                if (sameColumnArray[col] != theMostOftenNum) {
                    record.setX(col);
                    record.setY(indexOfLinesHasChanceToSix);
                }
            }
        }
        return record;
    }

    /**
     * this method will return the best move by analysing all possible token on a hand and all the empty place on the board.
     *
     * @param board current game board
     * @return  best move
     */
    public Move theBestRemove_achieveBestScore(Hand hand, GameBoard board, Team team) {
        Token theRemovedToken = Token.NONE;
        Position theBestRemovePos = new Position();

        Position[] allBoardTokenPosArray = getAllBoardTokenPos(board);
        int[] recordPossibleScore = new int[allBoardTokenPosArray.length];

        if (hand.containTheToken(Token.REMOVE)) {
            // current team has chance to six tokens
            for (int pos = 0; pos < allBoardTokenPosArray.length; pos++) {
                Token originalToken = board.getCell(allBoardTokenPosArray[pos].getX(), allBoardTokenPosArray[pos].getY());

                board.setCell(Token.NONE, allBoardTokenPosArray[pos].getX(), allBoardTokenPosArray[pos].getY());

                int greenTotalScore = getTotalScore(board, Team.GREEN);
                int orangeTotalScore = getTotalScore(board, Team.ORANGE);

                if (team.equals(Team.GREEN)) {
                    recordPossibleScore[pos] = greenTotalScore - orangeTotalScore;
                } else if (team.equals(Team.ORANGE)) {
                    recordPossibleScore[pos] = orangeTotalScore - greenTotalScore;
                }
                board.setCell(originalToken, allBoardTokenPosArray[pos].getX(), allBoardTokenPosArray[pos].getY());
            }

            int indexOfHighestScore = getTheHighestScoreOfOneDimension(recordPossibleScore);

            theBestRemovePos = allBoardTokenPosArray[indexOfHighestScore];

            theRemovedToken = board.getCell(theBestRemovePos.getX(), theBestRemovePos.getY());

        }

        return new Move(theRemovedToken, theBestRemovePos);
    }

    /**
     * get the best remove to fill six tokens
     *
     * @param board game board
     * @param team  game team
     * @return a best movement for remove
     */
    public Move theBestRemove_DealingSixToken(GameBoard board, Team team) {
        Token theRemovedToken = Token.NONE;
        Position theBestRemovePos = new Position();
        Team currTeam;
        Team oppTeam;

        if (team.equals(Team.GREEN)) {
            currTeam = team;
            oppTeam = Team.ORANGE;

        } else {
            currTeam = team;
            oppTeam = Team.GREEN;
        }

        if (boardHasChanceToCompleteSixToken(board, currTeam)) {
            int indexOfLinesHasChanceToSix = getIndexOfLinesHasChanceToSix(board, currTeam);
            Position distinctPos = getTheDistinctPosOfPossibleSixInLines(indexOfLinesHasChanceToSix, board, team);

            if (!board.getCell(distinctPos.getX(), distinctPos.getY()).equals(Token.NONE)) {
                theBestRemovePos.setX(distinctPos.getX());
                theBestRemovePos.setY(distinctPos.getY());
            }
            theRemovedToken = board.getCell(theBestRemovePos.getX(), theBestRemovePos.getY());

        } else if (boardHasChanceToCompleteSixToken(board, oppTeam)) {
            //prevent opposite team to shift become six

            int indexOfLinesHasChanceToSix = getIndexOfLinesHasChanceToSix(board, oppTeam);
            Position targetPos = getFirstOppositePosOfPossibleSix(indexOfLinesHasChanceToSix, board, oppTeam);
            theBestRemovePos.setX(targetPos.getX());
            theBestRemovePos.setY(targetPos.getY());

            theRemovedToken = board.getCell(theBestRemovePos.getX(), theBestRemovePos.getY());

        }

        return new Move(theRemovedToken, theBestRemovePos);
    }


    /**
     * boolean checking
     *
     * @param arrayOfScoreATeam an array of scoring a team
     * @return true, had chance to six
     * false, has no chance to six
     */
    private boolean hasChanceToSix(int[] arrayOfScoreATeam) {
        boolean hasChanceToSix = false;
        for (int i : arrayOfScoreATeam) {
            if (i == FIVE_SAME_POINT) {
                hasChanceToSix = true;
                break;
            }
        }
        return hasChanceToSix;
    }

    /**
     * this method will return the best move by analysing all possible token on a hand and all the empty place on the board.
     *
     * @param board current game board
     * @param team  game team
     * @return  best move array, first element : a first move contain the chosen position, second element : a second move contain the chosen position to which will be moved.
     */
    public Position[] theBestShift_achieveBestScore(GameBoard board, Team team) {

        Position[] emptyPositionArray = getEmptyCurrPos(board);
        Position[] allBoardTokenPosArray = getAllBoardTokenPos(board);
        int[][] recordPossibleScore = new int[allBoardTokenPosArray.length][emptyPositionArray.length];

        // current team has chance to six tokens
        for (int token_idx = 0; token_idx < allBoardTokenPosArray.length; token_idx++) {        // recordPossibleScore.length = 4

            // get an array of the best possible score.
            for (int pos_empty = 0; pos_empty < emptyPositionArray.length; pos_empty++) {
                Token originalToken = board.getCell(allBoardTokenPosArray[token_idx].getX(), allBoardTokenPosArray[token_idx].getY());

                board.setCell(originalToken, emptyPositionArray[pos_empty].getX(), emptyPositionArray[pos_empty].getY());
                board.setCell(Token.NONE, allBoardTokenPosArray[token_idx].getX(), allBoardTokenPosArray[token_idx].getY());

                int greenTotalScore = getTotalScore(board, Team.GREEN);
                int orangeTotalScore = getTotalScore(board, Team.ORANGE);

                if (team.equals(Team.GREEN)) {
                    recordPossibleScore[token_idx][pos_empty] = greenTotalScore - orangeTotalScore;
                } else if (team.equals(Team.ORANGE)) {
                    recordPossibleScore[token_idx][pos_empty] = orangeTotalScore - greenTotalScore;
                }

                board.setCell(Token.NONE, emptyPositionArray[pos_empty].getX(), emptyPositionArray[pos_empty].getY());
                board.setCell(originalToken, allBoardTokenPosArray[token_idx].getX(), allBoardTokenPosArray[token_idx].getY());
            }
        }


        int[] indexOfHighestScore = getTheHighestScore(recordPossibleScore);

        return new Position[]{
                allBoardTokenPosArray[indexOfHighestScore[0]], emptyPositionArray[indexOfHighestScore[1]]
        };
    }

    /**
     * the best shift for six token
     *
     * @param board game boar
     * @param team  game team
     * @return an array of position
     */
    public Position[] theBestShift_dealingSixToken(GameBoard board, Team team) {
        int indexOfLinesHasChanceToSix;
        int twoCoordinates = 2;
        Position[] theBestShift = new Position[twoCoordinates];
        Team currTeam;
        Team oppTeam;
        if (team.equals(Team.GREEN)) {
            currTeam = team;
            oppTeam = Team.ORANGE;
        } else {
            currTeam = team;
            oppTeam = Team.GREEN;
        }

        if (boardHasChanceToCompleteSixToken(board, currTeam)) {
            indexOfLinesHasChanceToSix = getIndexOfLinesHasChanceToSix(board, currTeam);
            Position distinctPos = getTheDistinctPosOfPossibleSixInLines(indexOfLinesHasChanceToSix, board, currTeam);

            if (board.getCell(distinctPos.getX(), distinctPos.getY()).equals(Token.NONE)) {
                Position ShiftedPosition = searchTargetTokenOnOtherLines(distinctPos, board, currTeam);
                theBestShift[0] = ShiftedPosition;
                theBestShift[1] = distinctPos;
            }
        } else if (boardHasChanceToCompleteSixToken(board, oppTeam)) {
            //prevent opposite team to shift become six
            indexOfLinesHasChanceToSix = getIndexOfLinesHasChanceToSix(board, oppTeam);
            Position shiftedPosition = getFirstOppositePosOfPossibleSix(indexOfLinesHasChanceToSix, board, oppTeam);
            Position[] emptyCurrPos = getEmptyCurrPos(board);

            Position targetPos = getFirstEmptyNotSameLine(indexOfLinesHasChanceToSix, emptyCurrPos, team);
            theBestShift[0] = shiftedPosition;
            theBestShift[1] = targetPos;
        }

        return theBestShift;
    }

    /**
     * get the first empty position which is not on the same line of the target
     *
     * @param emptyCurrPos empty position
     * @param team         game team
     * @return a position
     */
    private Position getFirstEmptyNotSameLine(int indexOfLinesHasChanceToSix, Position[] emptyCurrPos, Team team) {
        Position result = new Position();
        if (team.equals(Team.GREEN)) {
            for (Position emptyCurrPo : emptyCurrPos) {
                if (emptyCurrPo.getY() != indexOfLinesHasChanceToSix) {
                    result = emptyCurrPo;
                    return result;
                }
            }
        } else {
            for (Position emptyCurrPo : emptyCurrPos) {
                if (emptyCurrPo.getX() != indexOfLinesHasChanceToSix) {
                    result = emptyCurrPo;
                    return result;
                }
            }

        }

        return result;
    }

    /**
     * check if the position has the target token.
     *
     * @param distinctPos a distinct position
     * @param board       game board
     * @param team        game team
     * @return true, on the pos
     * false, not on the pos
     */
    private boolean hasTargetTokenOnOtherPos(Position distinctPos, GameBoard board, Team team) {
        Token[][] field = board.getField();
        boolean hasValue = false;
        int oftenElement;
        if (team.equals(Team.ORANGE)) {
            oftenElement = board.getTheMostOftenElement(board.getAListOfSameColumn(distinctPos.getY()));
            for (Token[] tokens : field) {
                for (int j = 0; j < field.length; j++) {
                    if (j != distinctPos.getY()) {
                        if (tokens[j].getIndex() == oftenElement) {
                            hasValue = true;
                        }
                    }
                }
            }
        } else if (team.equals(Team.GREEN)) {
            oftenElement = board.getTheMostOftenElement(board.getAListOfSameRow(distinctPos.getX()));
            for (int i = 0; i < field.length; i++) {
                for (int j = 0; j < field.length; j++) {
                    if (i != distinctPos.getX()) {
                        if (field[i][j].getIndex() == oftenElement) {
                            hasValue = true;
                        }
                    }
                }
            }

        }
        return hasValue;

    }

    /**
     * search a target token on other lines.
     *
     * @param distinctPos given distinctPosition
     * @param board       board
     * @param team        given team
     * @return a position of the target
     */
    private Position searchTargetTokenOnOtherLines(Position distinctPos, GameBoard board, Team team) {
        Token[][] field = board.getField();
        Position result = new Position();
        int oftenElement;
        if (team.equals(Team.ORANGE)) {
            oftenElement = board.getTheMostOftenElement(board.getAListOfSameColumn(distinctPos.getY()));
            for (int i = 0; i < field.length; i++) {
                for (int j = 0; j < field.length; j++) {
                    if (j != distinctPos.getY()) {
                        if (field[i][j].getIndex() == oftenElement) {
                            result.setX(i);
                            result.setY(j);
                            return result;
                        }
                    }
                }
            }

        } else if (team.equals(Team.GREEN)) {

            oftenElement = board.getTheMostOftenElement(board.getAListOfSameRow(distinctPos.getX()));

            for (int i = 0; i < field.length; i++) {
                for (int j = 0; j < field.length; j++) {
                    if (i != distinctPos.getX()) {
                        if (field[i][j].getIndex() == oftenElement) {
                            result.setX(i);
                            result.setY(j);
                            return result;
                        }
                    }
                }
            }

        }
        return result;
    }

    /**
     * this method will return the best move by analysing all possible token on a hand and all the empty place on the board.
     *
     * @param board current game board
     * @return  best move array, first element : a first move contain the chosen position, second element : a second move contain the chosen position to which will be moved.
     */
    public Position[] theBestExchange_achieveBestScore(GameBoard board, Team team) {

        Position[] allBoardTokenPosArray_first = getAllBoardTokenPos(board);
        Position[] allBoardTokenPosArray_second = getAllBoardTokenPos(board);

        int[][] recordPossibleScore = new int[allBoardTokenPosArray_first.length][allBoardTokenPosArray_second.length];


        for (int token_idx = 0; token_idx < allBoardTokenPosArray_first.length; token_idx++) {
            Token originalToken_first = board.getCell(allBoardTokenPosArray_first[token_idx].getX(), allBoardTokenPosArray_first[token_idx].getY());
            // get an array of the best possible score.
            for (int pos = token_idx; pos < allBoardTokenPosArray_first.length; pos++) {

                Token originalToken_second = board.getCell(allBoardTokenPosArray_first[pos].getX(), allBoardTokenPosArray_first[pos].getY());

                if (token_idx != pos) {
                    board.setCell(originalToken_second, allBoardTokenPosArray_first[token_idx].getX(), allBoardTokenPosArray_first[token_idx].getY());
                    board.setCell(originalToken_first, allBoardTokenPosArray_first[pos].getX(), allBoardTokenPosArray_first[pos].getY());

                    int greenTotalScore = getTotalScore(board, Team.GREEN);
                    int orangeTotalScore = getTotalScore(board, Team.ORANGE);

                    if (team.equals(Team.GREEN)) {
                        recordPossibleScore[token_idx][pos] = greenTotalScore - orangeTotalScore;
                    } else if (team.equals(Team.ORANGE)) {
                        recordPossibleScore[token_idx][pos] = orangeTotalScore - greenTotalScore;
                    }

                    board.setCell(originalToken_first, allBoardTokenPosArray_first[token_idx].getX(), allBoardTokenPosArray_first[token_idx].getY());
                    board.setCell(originalToken_second, allBoardTokenPosArray_first[pos].getX(), allBoardTokenPosArray_first[pos].getY());

                }

            }
        }

        int[] indexOfHighestScore = getTheHighestScore(recordPossibleScore);

        int twoPositions = 2;
        int theNextValue = 1;
        int zero_score = 0;
        Position[] theBestExchange = new Position[twoPositions];

        if (indexOfHighestScore[0] == zero_score && indexOfHighestScore[1] == zero_score) {
            theBestExchange[0] = allBoardTokenPosArray_first[indexOfHighestScore[0]];
            theBestExchange[1] = allBoardTokenPosArray_first[indexOfHighestScore[1] + theNextValue];
        } else {
            theBestExchange[0] = allBoardTokenPosArray_first[indexOfHighestScore[0]];
            theBestExchange[1] = allBoardTokenPosArray_first[indexOfHighestScore[1]];
        }

        return theBestExchange;
    }

    /**
     * the best exchange for complete six token
     *
     * @param board game board
     * @param team  team
     * @return a position array contains two positions
     */
    public Position[] theBestExchange_dealingSixToken(GameBoard board, Team team) {
        int twoPositions = 2;
        Position[] theBestExchange = new Position[twoPositions];
        int indexOfLinesHasChanceToSix;
        Team currTeam;
        Team oppTeam;
        if (team.equals(Team.GREEN)) {
            currTeam = team;
            oppTeam = Team.ORANGE;
        } else {
            currTeam = team;
            oppTeam = Team.GREEN;
        }

        if (boardHasChanceToCompleteSixToken(board, currTeam)) {
            indexOfLinesHasChanceToSix = getIndexOfLinesHasChanceToSix(board, currTeam);
            Position distinctPos = getTheDistinctPosOfPossibleSixInLines(indexOfLinesHasChanceToSix, board, currTeam);

            Position ShiftedPosition = searchTargetTokenOnOtherLines(distinctPos, board, currTeam);

            theBestExchange[0] = ShiftedPosition;
            theBestExchange[1] = distinctPos;
        } else if (boardHasChanceToCompleteSixToken(board, oppTeam)) {
            //prevent opposite team to shift become six
            indexOfLinesHasChanceToSix = getIndexOfLinesHasChanceToSix(board, oppTeam);   // get the
            Position shiftedPosition = getFirstOppositePosOfPossibleSix(indexOfLinesHasChanceToSix, board, oppTeam);
            Position otherSameTokPos = searchTargetTokenOnOtherLines(shiftedPosition, board, oppTeam);
            Position targetPos = getTheFirstTokenPosOnTheLineOfTheSameTarget(otherSameTokPos, board, oppTeam);

            theBestExchange[0] = shiftedPosition;
            theBestExchange[1] = targetPos;
        }
        return theBestExchange;
    }

    /**
     * try to find the first token on a line. this toke must be same as the given target
     *
     * @param otherSameTokPos target token position
     * @param board           game board
     * @param team            game team
     * @return a position
     */
    private Position getTheFirstTokenPosOnTheLineOfTheSameTarget(Position otherSameTokPos, GameBoard board, Team
            team) {
        Position result = new Position();

        int[] arrayOfLine;
        int index;
        if (team.equals(Team.ORANGE)) {
            arrayOfLine = board.getAListOfSameRow(otherSameTokPos.getX());
            index = getTheFirstTokenIndexOnLine(arrayOfLine, board.getCell(otherSameTokPos.getX(), otherSameTokPos.getY()));

            result.setX(otherSameTokPos.getX());
            result.setY(index);

        } else if (team.equals(Team.GREEN)) {
            arrayOfLine = board.getAListOfSameColumn(otherSameTokPos.getY());
            index = getTheFirstTokenIndexOnLine(arrayOfLine, board.getCell(otherSameTokPos.getX(), otherSameTokPos.getY()));
            result.setX(index);
            result.setY(otherSameTokPos.getY());

        }

        return result;
    }

    /**
     * get the first token index from a line.
     *
     * @param arrayOfLine an array of a line
     * @param oftenToken  the most often token
     * @return the first token index
     */
    private int getTheFirstTokenIndexOnLine(int[] arrayOfLine, Token oftenToken) {
        int invalidIndex = -1;
        for (int element = 0; element < arrayOfLine.length; element++) {
            if (arrayOfLine[element] != Token.NONE.getIndex() && arrayOfLine[element] != oftenToken.getIndex()) {
                return element;
            }
        }

        return invalidIndex;
    }

    /**
     * this method will return the best move for replace action which is use for achieving the best score
     *
     * @param hand  a player hand
     * @param board current game board
     * @param team  game team
     * @return the best replace
     */
    private Move theBestReplace_achieveBestScore(Hand hand, GameBoard board, Team team) {

        Token[] handAllTokens = getSymbolTokenFromHand(hand);
        Position[] allBoardTokenPosArray = getAllBoardTokenPos(board);
        int[][] recordPossibleScore = new int[handAllTokens.length][allBoardTokenPosArray.length];

        for (int token_idx = 0; token_idx < handAllTokens.length; token_idx++) {        // recordPossibleScore.length = 4
            // get an array of the best possible score.
            for (int pos = 0; pos < allBoardTokenPosArray.length; pos++) {
                Token originalToken = board.getCell(allBoardTokenPosArray[pos].getX(), allBoardTokenPosArray[pos].getY());

                board.setCell(handAllTokens[token_idx], allBoardTokenPosArray[pos].getX(), allBoardTokenPosArray[pos].getY());

                int greenTotalScore = getTotalScore(board, Team.GREEN);
                int orangeTotalScore = getTotalScore(board, Team.ORANGE);

                if (team.equals(Team.GREEN)) {
                    recordPossibleScore[token_idx][pos] = greenTotalScore - orangeTotalScore;
                } else if (team.equals(Team.ORANGE)) {
                    recordPossibleScore[token_idx][pos] = orangeTotalScore - greenTotalScore;
                }
                board.setCell(originalToken, allBoardTokenPosArray[pos].getX(), allBoardTokenPosArray[pos].getY());
            }
        }

        int[] indexOfHighestScore = getTheHighestScore(recordPossibleScore);

        Token theBestTokenOnHand = handAllTokens[indexOfHighestScore[0]];
        Position targetPos = allBoardTokenPosArray[indexOfHighestScore[1]];

        return new Move(theBestTokenOnHand, targetPos);
    }

    /**
     * the best replace method to dealing a line had chance to six token
     *
     * @param hand  player hand
     * @param board game board
     * @param team  game team
     * @return a best replace
     */
    public Move theBestReplace_dealingSixToken(Hand hand, GameBoard board, Team team) {
        Token theBestTokenOnHand = Token.NONE;
        Position targetPos = new Position();
        Team currTeam;
        Team oppTeam;
        int theMostOftenToken;
        int indexOfLinesHasChanceToSix_curr;
        int indexOfLinesHasChanceToSix_opp;
        int[] aListOfLineArray_curr;
        int[] aListOfLineArray_opp;
        if (team.equals(Team.GREEN)) {
            currTeam = team;
            oppTeam = Team.ORANGE;

            indexOfLinesHasChanceToSix_curr = getIndexOfLinesHasChanceToSix(board, currTeam);
            indexOfLinesHasChanceToSix_opp = getIndexOfLinesHasChanceToSix(board, oppTeam);
            aListOfLineArray_curr = board.getAListOfSameRow(indexOfLinesHasChanceToSix_curr);
            aListOfLineArray_opp = board.getAListOfSameColumn(indexOfLinesHasChanceToSix_opp);

        } else {
            currTeam = team;
            oppTeam = Team.GREEN;

            indexOfLinesHasChanceToSix_curr = getIndexOfLinesHasChanceToSix(board, currTeam);
            indexOfLinesHasChanceToSix_opp = getIndexOfLinesHasChanceToSix(board, oppTeam);
            aListOfLineArray_curr = board.getAListOfSameColumn(indexOfLinesHasChanceToSix_curr);
            aListOfLineArray_opp = board.getAListOfSameRow(indexOfLinesHasChanceToSix_opp);

        }

        theMostOftenToken = board.getTheMostOftenElement(aListOfLineArray_curr);

        if (boardHasChanceToCompleteSixToken(board, currTeam) && hand.containTheToken(Token.getToken(theMostOftenToken))) {
            theBestTokenOnHand = Token.getToken(theMostOftenToken);

            targetPos = getTheDistinctPosOfPossibleSixInLines(indexOfLinesHasChanceToSix_curr, board, currTeam);

        } else if (boardHasChanceToCompleteSixToken(board, oppTeam)) {

            theMostOftenToken = board.getTheMostOftenElement(aListOfLineArray_opp);
            theBestTokenOnHand = getAHandTokenNotTheSameTokenInAPossibleSixTokens(hand, Token.getToken(theMostOftenToken));

            targetPos = getFirstOppositePosOfPossibleSix(indexOfLinesHasChanceToSix_opp, board, oppTeam);
        }

        return new Move(theBestTokenOnHand, targetPos);
    }


    /**
     * a method for moving Token by giving a move
     *
     * @param move a move
     */
    public void moveToken(Move move) {
        assert move != null;
        Position position = move.getPosition();
        Token target = move.getToken();
        int target_x = position.getX();
        int target_y = position.getY();

        Token[][] field = this.getBoard().field;
        if (this.getBoard().isPositionEmpty(position)) {
            field[target_x][target_y] = target;

        }
    }

    /**
     * a method to remove a position
     *
     * @param removedPos remove a position
     * @param hand       player hand
     */
    public void removeToken(Position removedPos, Hand hand) {
        int indexOfRemoveOnHand = hand.getAtTokenIndexFromAHand(Token.REMOVE);

        Token token = this.getBoard().getCell(removedPos.getX(), removedPos.getY());
        hand.removeTokenAtIndex(indexOfRemoveOnHand);

        this.getBoard().setCell(Token.NONE, removedPos.getX(), removedPos.getY());

        hand.removeToken(null);

        hand.addTokenAtGivenIndex(indexOfRemoveOnHand, token);

    }

    /**
     * a method to shift token from a pos to another position
     *
     * @param pos        the Position of first token
     * @param anotherPos Another position of second token
     */
    public void shiftToken(Position pos, Position anotherPos) {
        Token shiftedToken = this.getBoard().getCell(pos.getX(), pos.getY());

        Token targetToken = this.getBoard().getCell(anotherPos.getX(), anotherPos.getY());

        if (targetToken != Token.NONE) {
            throw new IllegalArgumentException("the target position should be empty: " + targetToken);
        }
        this.getBoard().setCell(targetToken, pos.getX(), pos.getY());

        this.getBoard().setCell(shiftedToken, anotherPos.getX(), anotherPos.getY());


    }

    /**
     * a method to exchange a pos to another pos
     *
     * @param pos        the position of first token
     * @param anotherPos the position of second token
     */
    public void exchangeToken(Position pos, Position anotherPos) {
        Token shiftedToken = this.getBoard().getCell(pos.getX(), pos.getY());

        Token targetToken = this.getBoard().getCell(anotherPos.getX(), anotherPos.getY());

        if (targetToken == Token.NONE) {
            throw new IllegalArgumentException("the target position is empty: " + targetToken);
        }

        this.getBoard().setCell(targetToken, pos.getX(), pos.getY());

        this.getBoard().setCell(shiftedToken, anotherPos.getX(), anotherPos.getY());


    }

    /**
     * replace a token on board from player hand
     *
     * @param pos                          position to replace token
     * @param indexOfReplacedTokenFromHand index on hand to replace a token from hand
     * @param hand                         player hand
     */
    public void replaceToken(Position pos, int indexOfReplacedTokenFromHand, Hand hand) {

        Token targetToken = this.getBoard().getCell(pos.getX(), pos.getY());
        Token shiftedTokenFromHand = hand.getATokenOnIndex(indexOfReplacedTokenFromHand);
        this.getBoard().setCell(shiftedTokenFromHand, pos.getX(), pos.getY());

        hand.removeTokenAtIndex(indexOfReplacedTokenFromHand);

        hand.removeToken(null);

        hand.addTokenAtGivenIndex(indexOfReplacedTokenFromHand, targetToken);


    }

    /**
     * initialise every player
     *
     * @param name player name
     * @param com  AI player
     */
    private void initialisePlayer(String[] name, boolean[] active, boolean[] com) {
        int firstPlayer = 0, noCard = 0;
        // create a full pack of token. this is used in different player.
        if (playerCount != NO_PLAYER) {
            //create several players.
            if (playerCount == PLAYER_MIN) {
                players[0] = new Players(name[0], active[0], com[0], deck, TOKEN_AMOUNT);
                players[1] = new Players(name[1], active[1], com[1], deck, TOKEN_AMOUNT);
                players[2] = new Players(name[2], active[2], com[2], deck, noCard);
                players[3] = new Players(name[3], active[3], com[3], deck, noCard);
            } else if (playerCount == PLAYER_MAX) {
                players[0] = new Players(name[0], active[0], com[0], deck, TOKEN_AMOUNT);
                players[1] = new Players(name[1], active[1], com[1], deck, TOKEN_AMOUNT);
                players[2] = new Players(name[2], active[2], com[2], deck, TOKEN_AMOUNT);
                players[3] = new Players(name[3], active[3], com[3], deck, TOKEN_AMOUNT);
            }


            if (players[firstPlayer].getHand().containAllActionToken()) {

                for (int j = 0; j < name.length; j++) {
                    deck.addToken(players[firstPlayer].getHand().handList.get(j));
                }
                players[firstPlayer] = new Players(name[firstPlayer], active[firstPlayer], com[firstPlayer], deck, TOKEN_AMOUNT);
            }

            showPlayerHandGui(players);
            settingCurrentPlayerGui(players);
        }
    }

    /**
     * check if the current player is orange team
     *
     * @param idxOfCurrentPlayer index of player
     * @return true, is orange team
     * false, green team
     */
    public boolean isOrangeTeam(int idxOfCurrentPlayer) {
        int dividedConstant = 2;
        int EvenRemainder = 0;
        return ((idxOfCurrentPlayer % dividedConstant) == EvenRemainder);
    }

    /**
     * setting the label Current player with given players array
     *
     * @param players player type array
     */
    public void settingCurrentPlayerGui(Players[] players) {

        if (isOrangeTeam(idxOfCurrentPlayer)) {
            gui.setCurrentPlayer(players[idxOfCurrentPlayer].getName(), Team.ORANGE.toString());
        } else {
            gui.setCurrentPlayer(players[idxOfCurrentPlayer].getName(), Team.GREEN.toString());
        }
    }

    /**
     * show player hand ON GUI
     */
    public void showPlayerHandGui(Players[] players) {
        int displayTokenIndex = -1;
        for (int playerIndex = 0; playerIndex < players.length; playerIndex++) {
            if (this.players[playerIndex].getIsActive()) {
                Hand handList = this.players[playerIndex].getHand();    // get player hand
                int size_of_handList = handList.getNoOfTokensFromPack();
                for (int tokenIndex = 0; tokenIndex < size_of_handList; tokenIndex++) {     // iterate hand list, start from 0 index
                    Token token = handList.getATokenOnIndex(tokenIndex);     // get the positioned token
                    if (token != null) {
                        displayTokenIndex = token.getIndex();
                    }
                    if (isOrangeTeam(playerIndex)) {
                        gui.showHandsToken(tokenIndex, 0, displayTokenIndex, playerIndex);   // for hand 1 and hand 3 , x is row ,and  it is 0 , but in image x is column
                    } else {
                        gui.showHandsToken(0, tokenIndex, displayTokenIndex, playerIndex);   // for hand 2 and hand 4
                    }
                }

            }
        }
    }

    /**
     * display board token
     *
     * @param gameBoard game board
     */
    public void showBoardToken(GameBoard gameBoard) {
        Token[][] boardField = gameBoard.getField();
        for (int i = 0; i < boardField.length; i++) {
            for (int j = 0; j < boardField[i].length; j++) {
                gui.showTokenImageOnBoard(gameBoard.getCell(i, j).getIndex(), i, j);

            }
        }
    }

    /**
     * update remain action token number
     */
    public void updateRemainActionTokenDisplay() {

        int removeTokenAmount = actionTokenPack.getRemainToken(Token.REMOVE);
        int shifterTokenAmount = actionTokenPack.getRemainToken(Token.SHIFTER);
        int exchangeTokenAmount = actionTokenPack.getRemainToken(Token.EXCHANGE);
        int replaceTokenAmount = actionTokenPack.getRemainToken(Token.REPLACER);

        gui.setRemainActionTokens(Token.REMOVE.getIndex(), removeTokenAmount);
        gui.setRemainActionTokens(Token.SHIFTER.getIndex(), shifterTokenAmount);
        gui.setRemainActionTokens(Token.EXCHANGE.getIndex(), exchangeTokenAmount);
        gui.setRemainActionTokens(Token.REPLACER.getIndex(), replaceTokenAmount);
    }

    /**
     * remove a token from a hand.
     * call for removing a token when a token is place on the board.
     *
     * @param indexOfPlayer idx of current player
     * @param dragPosition  drag position
     */
    public void clearTokenFromHand(int indexOfPlayer, Position dragPosition) {

        Hand hand = this.players[indexOfPlayer].getHand();
        if (isOrangeTeam(indexOfPlayer)) {
            hand.removeTokenAtIndex(dragPosition.getY());
        } else {
            hand.removeTokenAtIndex(dragPosition.getX());
        }
        gui.cleanImg(indexOfPlayer, dragPosition.getX(), dragPosition.getY()); // the record parameter get from controller so, do not need to switch x and y position

    }

    /**
     * a method for dragging when human players are using symbol token
     *
     * @param playerIndex  idx of current player
     * @param dragPosition drag position
     * @param dropPosition drop position
     */
    public void DragUsedForSymbolToken(int playerIndex, Position dragPosition, Position dropPosition) {
        int indexOnHand;
        if (isOrangeTeam(playerIndex)) {
            indexOnHand = dragPosition.getY();
        } else {
            indexOnHand = dragPosition.getX();
        }
        usedSymbolToken_player(indexOnHand, playerIndex, dropPosition.getX(), dropPosition.getY());

    }

    /**
     * check if the position is symbol token
     *
     * @param position    the given position
     * @param playerIndex index of current player
     * @return true, it is symbol token
     * false, it is not symbol token
     */
    public boolean isSymbolToken(Position position, int playerIndex) {
        int indexOnHand;
        if (isOrangeTeam(playerIndex)) {
            indexOnHand = position.getY();
        } else {
            indexOnHand = position.getX();
        }
        Token token = players[playerIndex].getHand().getATokenOnIndex(indexOnHand);
        if (token == null) {
            return false;
        } else {
            return (Token.NONE.getIndex() < token.getIndex()) && (token.getIndex() <= Token.STAR.getIndex());
        }
    }

    /**
     * decide which action token will be uses from drag position
     *
     * @param dragPosition drag position
     * @param playerIndex  index of current player
     * @return the token on hand
     */
    public Token isWhichActionToken(Position dragPosition, int playerIndex) {
        int indexOnHand;

        if (isOrangeTeam(playerIndex)) {
            indexOnHand = dragPosition.getY();
        } else {
            indexOnHand = dragPosition.getX();
        }

        return players[playerIndex].getHand().getATokenOnIndex(indexOnHand);
    }

    /**
     * get a new token
     *
     * @param dragPosition drag position
     * @param playerIndex  idx of current player
     */
    public void getANewToken(Position dragPosition, int playerIndex) {


        int invalid_idx = -1;
        int emptyToken = 0;
        if (deck.isEmpty()) {
            logDeck();
        }
        if (this.deck.getNoOfTokensFromPack() != emptyToken) {

            Token token = this.deck.getARandomToken();
            if (isOrangeTeam(playerIndex)) {
                players[playerIndex].getHand().removeToken(null);
                players[playerIndex].getHand().addTokenAtGivenIndex(dragPosition.getY(), token);
            } else {
                players[playerIndex].getHand().removeToken(null);
                players[playerIndex].getHand().addTokenAtGivenIndex(dragPosition.getX(), token);
            }

            gui.showHandsToken(dragPosition.getY(), dragPosition.getX(), token.getIndex(), playerIndex);
        } else {

            gui.showHandsToken(dragPosition.getY(), dragPosition.getX(), invalid_idx, playerIndex);
        }

    }

    /**
     * update whole points
     */
    public void updatePoint() {
        gui.setTeamScore(String.valueOf(getTotalScore(this.getBoard(), Team.ORANGE)), String.valueOf(getTotalScore(this.getBoard(), Team.GREEN)));
        // set board points
        for (int i = 0; i < this.getBoard().field.length; i++) {
            gui.setBoardPoints(false, i, scoringALine(i, Team.GREEN));
            gui.setBoardPoints(true, i, scoringALine(i, Team.ORANGE));
        }

    }

    /**
     * this method is used to switch player every round
     *
     * @param playerIndex idx of current player
     * @return idx of current player
     */
    private int switchPlayerName(int playerIndex, int playerCount) {
        int constantToNextPlayer = 1;
        if (playerCount == PLAYER_MIN) {
            idxOfCurrentPlayer = (playerIndex + constantToNextPlayer) % PLAYER_MIN;
        } else {
            idxOfCurrentPlayer = (playerIndex + constantToNextPlayer) % PLAYER_MAX;
        }
        Team team;
        if (isOrangeTeam(idxOfCurrentPlayer)) {
            team = Team.ORANGE;
        } else {
            team = Team.GREEN;
        }

        gui.setCurrentPlayer(players[idxOfCurrentPlayer].getName(), team.name());

        return idxOfCurrentPlayer;
    }

    /**
     * player use action token to remove, shifting, exchange or replace tokens
     *
     * @param dragPosition    position from dragging
     * @param playerIndex     index of current player
     * @param shiftedPosBoard the shifted position
     * @param targetPosBoard  the target position
     */
    public void usedActionToken_Player(Position dragPosition, int playerIndex, Position shiftedPosBoard, Position
            targetPosBoard) {
        int indexOfReplacedOnHand;
        Token theGivenToken;
        Hand playerHand = players[playerIndex].getHand();

        if (isOrangeTeam(playerIndex)) {
            indexOfReplacedOnHand = shiftedPosBoard.getY();
            theGivenToken = playerHand.getATokenOnIndex(dragPosition.getY());
        } else {
            indexOfReplacedOnHand = shiftedPosBoard.getX();
            theGivenToken = playerHand.getATokenOnIndex(dragPosition.getX());
        }

        if (theGivenToken.equals(Token.REMOVE)) {
            removeToken(shiftedPosBoard, players[playerIndex].getHand());
            actionTokenPack.removeToken(Token.REMOVE);
            usedActionList[0]++;
            chosenToken = theGivenToken;
            gui.setImgForRemove(shiftedPosBoard.getX(), shiftedPosBoard.getY(), playerIndex, dragPosition.getX(), dragPosition.getY());
        } else if (theGivenToken.equals(Token.SHIFTER)) {
            shiftToken(shiftedPosBoard, targetPosBoard);
            actionTokenPack.removeToken(Token.SHIFTER);
            usedActionList[1]++;
            gui.setImgForShifting(shiftedPosBoard.getX(), shiftedPosBoard.getY(), targetPosBoard.getX(), targetPosBoard.getY(), playerIndex);
        } else if (theGivenToken.equals(Token.EXCHANGE)) {
            exchangeToken(shiftedPosBoard, targetPosBoard);
            usedActionList[2]++;
            actionTokenPack.removeToken(Token.EXCHANGE);
            gui.setImgForExchange(shiftedPosBoard.getX(), shiftedPosBoard.getY(), targetPosBoard.getX(), targetPosBoard.getY());
        } else if (theGivenToken.equals(Token.REPLACER)) {
            replaceToken(targetPosBoard, indexOfReplacedOnHand, players[playerIndex].getHand());
            usedActionList[3]++;
            actionTokenPack.removeToken(Token.REPLACER);
            chosenToken = theGivenToken;
            gui.setImgForReplace(shiftedPosBoard.getX(), shiftedPosBoard.getY(), targetPosBoard.getX(), targetPosBoard.getY(), playerIndex);
        }
    }

    /**
     * AI use action token to achieve best score
     *
     * @param playerIndex  index of current player
     * @param tokenOrdinal token ordinal
     * @return a position array
     */
    private Position[] useActionToken_achieveBestScore_AI(int playerIndex, int tokenOrdinal) {
        int twoPosition = 2;
        gui.removeRegionOnBoard();
        gui.removeShiftedRegionOnHand();
        Position[] resultPos = new Position[twoPosition];
        int hand_row;
        int hand_col;
        Team team;
        Token theGivenToken = Token.getToken(tokenOrdinal);
        Hand playerHand = players[playerIndex].getHand();
        if (isOrangeTeam(playerIndex)) {
            team = Team.ORANGE;
            hand_row = 0;
            hand_col = playerHand.getAtTokenIndexFromAHand(theGivenToken);
        } else {
            team = Team.GREEN;
            hand_row = playerHand.getAtTokenIndexFromAHand(theGivenToken);
            hand_col = 0;

        }

        if (players[playerIndex].getIsAI()) {
            assert theGivenToken != null;
            if (theGivenToken.equals(Token.REMOVE)) {
                Move removePos = theBestRemove_achieveBestScore(players[playerIndex].getHand(), this.getBoard(), team);
                removeToken(removePos.getPosition(), playerHand);
                usedActionList[0]++;
                gui.highlightedShiftedPosOnBoard(removePos.getPosition(), duration);
                actionTokenPack.removeToken(Token.REMOVE);
                gui.setUpAnimationForRemove(removePos.getPosition().getX(), removePos.getPosition().getY(), playerIndex, hand_row, hand_col, this::callback);
                gui.setImgForRemove(removePos.getPosition().getX(), removePos.getPosition().getY(), playerIndex, hand_row, hand_col);
                resultPos[0] = removePos.getPosition();
            } else if (theGivenToken.equals(Token.SHIFTER)) {
                Position[] shiftPosition = theBestShift_achieveBestScore(this.getBoard(), team);
                shiftToken(shiftPosition[0], shiftPosition[1]);
                usedActionList[1]++;
                gui.highlightedShiftedPosOnBoard(shiftPosition[0], duration);
                gui.highlightedTargetPosOnBoard(shiftPosition[1], duration);
                actionTokenPack.removeToken(Token.SHIFTER);
                gui.setUpAnimationForShifting(shiftPosition[1].getX(), shiftPosition[1].getY(), this::callback);
                gui.setImgForShifting(shiftPosition[0].getX(), shiftPosition[0].getY(), shiftPosition[1].getX(), shiftPosition[1].getY(), playerIndex);
                resultPos[0] = shiftPosition[0];
                resultPos[1] = shiftPosition[1];
            } else if (theGivenToken.equals(Token.EXCHANGE)) {
                Position[] exchangePos = theBestExchange_achieveBestScore(this.getBoard(), team);
                exchangeToken(exchangePos[0], exchangePos[1]);
                usedActionList[2]++;
                gui.highlightedShiftedPosOnBoard(exchangePos[0], duration);
                gui.highlightedTargetPosOnBoard(exchangePos[1], duration);
                actionTokenPack.removeToken(Token.EXCHANGE);
                gui.setUpAnimationForExchange(exchangePos[0].getX(), exchangePos[0].getY(), exchangePos[1].getX(), exchangePos[1].getY(), this::callback);
                gui.setImgForExchange(exchangePos[0].getX(), exchangePos[0].getY(), exchangePos[1].getX(), exchangePos[1].getY());
                resultPos[0] = exchangePos[0];
                resultPos[1] = exchangePos[1];
            } else if (theGivenToken.equals(Token.REPLACER)) {
                Move theBestReplaceMove = theBestReplace_achieveBestScore(playerHand, this.getBoard(), team);
                Position shiftedPos = new Position();
                if (isOrangeTeam(playerIndex)) {
                    hand_col = playerHand.getAtTokenIndexFromAHand(theBestReplaceMove.getToken());
                    shiftedPos.setX(hand_col);
                    shiftedPos.setY(0);
                } else {
                    hand_row = playerHand.getAtTokenIndexFromAHand(theBestReplaceMove.getToken());
                    shiftedPos.setY(hand_row);
                    shiftedPos.setX(0);
                }

                replaceToken(theBestReplaceMove.getPosition(), playerHand.getAtTokenIndexFromAHand(theBestReplaceMove.getToken()), playerHand);
                usedActionList[3]++;
                gui.highlightedShiftedPosForReplace(idxOfCurrentPlayer, shiftedPos, true, duration);
                gui.highlightedTargetPosOnBoard(theBestReplaceMove.getPosition(), duration);
                actionTokenPack.removeToken(Token.REPLACER);
                gui.setUpAnimationForReplace(theBestReplaceMove.getPosition().getX(), theBestReplaceMove.getPosition().getY(), playerIndex, hand_row, hand_col, this::callback);
                gui.setImgForReplace(hand_row, hand_col, theBestReplaceMove.getPosition().getX(), theBestReplaceMove.getPosition().getY(), playerIndex);
                resultPos[0] = theBestReplaceMove.getPosition();

            }
        }

        return resultPos;
    }

    /**
     * method for AI using action token
     *
     * @param playerIndex  index of current player
     * @param tokenOrdinal token ordinal
     * @return a position array
     */
    private Position[] useActionToken_hasSixToken_AI(int playerIndex, int tokenOrdinal) {
        gui.removeRegionOnBoard();
        gui.removeShiftedRegionOnHand();
        int twoPosition = 2;
        Position[] resultPos = new Position[twoPosition];
        int hand_row;
        int hand_col;
        Team team;
        Token theGivenToken = Token.getToken(tokenOrdinal);
        Hand playerHand = players[playerIndex].getHand();
        if (isOrangeTeam(playerIndex)) {
            team = Team.ORANGE;
            hand_row = 0;
            hand_col = playerHand.getAtTokenIndexFromAHand(theGivenToken);
        } else {
            team = Team.GREEN;
            hand_row = playerHand.getAtTokenIndexFromAHand(theGivenToken);
            hand_col = 0;
        }

        if (players[playerIndex].getIsAI()) {
            assert theGivenToken != null;
            if (theGivenToken.equals(Token.REMOVE)) {
                Move removePos = theBestRemove_DealingSixToken(this.getBoard(), team);
                removeToken(removePos.getPosition(), playerHand);
                usedActionList[0]++;
                gui.highlightedShiftedPosOnBoard(removePos.getPosition(), duration);
                actionTokenPack.removeToken(Token.REMOVE);
                gui.setUpAnimationForRemove(removePos.getPosition().getX(), removePos.getPosition().getY(), playerIndex, hand_row, hand_col, this::callback);
                gui.setImgForRemove(removePos.getPosition().getX(), removePos.getPosition().getY(), playerIndex, hand_row, hand_col);

                resultPos[0] = removePos.getPosition();
            } else if (theGivenToken.equals(Token.SHIFTER)) {
                Position[] shiftPosition = theBestShift_dealingSixToken(this.getBoard(), team);
                shiftToken(shiftPosition[0], shiftPosition[1]);
                usedActionList[1]++;
                gui.highlightedShiftedPosOnBoard(shiftPosition[0], duration);
                gui.highlightedTargetPosOnBoard(shiftPosition[1], duration);
                actionTokenPack.removeToken(Token.SHIFTER);
                gui.setUpAnimationForShifting(shiftPosition[1].getX(), shiftPosition[1].getY(), this::callback);
                gui.setImgForShifting(shiftPosition[0].getX(), shiftPosition[0].getY(), shiftPosition[1].getX(), shiftPosition[1].getY(), playerIndex);

                resultPos[0] = shiftPosition[0];
                resultPos[1] = shiftPosition[1];
            } else if (theGivenToken.equals(Token.EXCHANGE)) {
                Position[] exchangePos = theBestExchange_dealingSixToken(this.getBoard(), team);
                exchangeToken(exchangePos[0], exchangePos[1]);
                usedActionList[2]++;
                gui.highlightedShiftedPosOnBoard(exchangePos[0], duration);
                gui.highlightedTargetPosOnBoard(exchangePos[1], duration);
                actionTokenPack.removeToken(Token.EXCHANGE);
                gui.setUpAnimationForExchange(exchangePos[0].getX(), exchangePos[0].getY(), exchangePos[1].getX(), exchangePos[1].getY(), this::callback);
                gui.setImgForExchange(exchangePos[0].getX(), exchangePos[0].getY(), exchangePos[1].getX(), exchangePos[1].getY());

                resultPos[0] = exchangePos[0];
                resultPos[1] = exchangePos[1];
            } else if (theGivenToken.equals(Token.REPLACER)) {
                Position shiftedPos = new Position();
                Move theBestReplaceMove = theBestReplace_dealingSixToken(playerHand, this.getBoard(), team);

                if (isOrangeTeam(playerIndex)) {
                    hand_col = playerHand.getAtTokenIndexFromAHand(theBestReplaceMove.getToken());
                    shiftedPos.setX(0);
                    shiftedPos.setY(hand_col);
                } else {
                    hand_row = playerHand.getAtTokenIndexFromAHand(theBestReplaceMove.getToken());
                    shiftedPos.setY(hand_row);
                    shiftedPos.setX(0);
                }

                replaceToken(theBestReplaceMove.getPosition(), playerHand.getAtTokenIndexFromAHand(theBestReplaceMove.getToken()), playerHand);
                usedActionList[3]++;
                gui.highlightedShiftedPosForReplace(idxOfCurrentPlayer, shiftedPos, true, duration);
                gui.highlightedTargetPosOnBoard(theBestReplaceMove.getPosition(), duration);
                actionTokenPack.removeToken(Token.REPLACER);
                gui.setUpAnimationForReplace(theBestReplaceMove.getPosition().getX(), theBestReplaceMove.getPosition().getY(), playerIndex, hand_row, hand_col, this::callback);
                gui.setImgForReplace(hand_row, hand_col, theBestReplaceMove.getPosition().getX(), theBestReplaceMove.getPosition().getY(), playerIndex);

                resultPos[0] = theBestReplaceMove.getPosition();

            }
        }
        return resultPos;

    }

    /**
     * a callback to wait for animation done.
     */
    public void callback() {

        Hand hand = players[idxOfCurrentPlayer].getHand();
        if (!isFinished()) {
            if (players[idxOfCurrentPlayer].getIsAI()) {
                if (!chosenToken.equals(Token.REMOVE)) {
                    if (isOrangeTeam(idxOfCurrentPlayer)) {
                        drag_position.setY(hand.getAtTokenIndexFromAHand(chosenToken));
                        drag_position.setX(0);

                    } else {
                        drag_position.setX(hand.getAtTokenIndexFromAHand(chosenToken));
                        drag_position.setY(0);
                    }

                    clearTokenFromHand(idxOfCurrentPlayer, drag_position);
                    getANewToken(drag_position, idxOfCurrentPlayer);
                }
                logForAIPlacingToken();

            } else {

                if (!chosenToken.equals(Token.REMOVE)) {
                    if (chosenToken.equals(Token.REPLACER)) {
                        clearTokenFromHand(idxOfCurrentPlayer, storeReplaceOnHand);
                        getANewToken(storeReplaceOnHand, idxOfCurrentPlayer);
                    } else {
                        clearTokenFromHand(idxOfCurrentPlayer, drag_position);
                        getANewToken(drag_position, idxOfCurrentPlayer);
                    }
                }
                chosenToken = Token.NONE;
            }

            idxOfCurrentPlayer = switchPlayerName(idxOfCurrentPlayer, playerCount);
            gui.hiddenDeck(idxOfCurrentPlayer);

            if (players[idxOfCurrentPlayer].getIsAI()) {
                AI_playersTurn(getBoard(), idxOfCurrentPlayer);
            }
        } else {
            logEnd(getWinnerTeam(), getFinalScore());
            gui.gameHasEnded(getWinnerTeam(), getFinalScore());
        }
    }

    /**
     * human player use symbol token to play game
     *
     * @param indexOnHand   index of token on hand
     * @param indexOfPlayer index of current player
     * @param pos_x_board   pos x on board
     * @param pos_y_board   pos y on board
     */
    private void usedSymbolToken_player(int indexOnHand, int indexOfPlayer, int pos_x_board, int pos_y_board) {
        Token theGivenToken = players[indexOfPlayer].getHand().getATokenOnIndex(indexOnHand);
        Move move = new Move(theGivenToken, new Position(pos_x_board, pos_y_board));
        moveToken(move);

        gui.showTokenImageOnBoard(move.getToken().getIndex(), move.getPosition().getX(), move.getPosition().getY());

        symbolTokenPack.removeToken(move.getToken());

    }


    /**
     * AI player using symbol token to play game
     *
     * @param indexOfPlayer idx of current player
     * @return the token Of one movement from AI player
     */
    private Move useSymbolToken_AI(int indexOfPlayer) {
        gui.removeRegionOnBoard();
        Team team;
        Hand hand = players[indexOfPlayer].getHand();
        Move AI_move = new Move();
        if (isOrangeTeam(indexOfPlayer)) {
            team = Team.ORANGE;
        } else {
            team = Team.GREEN;
        }

        if (players[indexOfPlayer].getIsAI()) {
            AI_move = theBestMove(hand, this.getBoard(), team);
            moveToken(AI_move);

            gui.setUpAnimationForMovingAToken(AI_move, this::callback);

            gui.showTokenImageOnBoard(AI_move.getToken().getIndex(), AI_move.getPosition().getX(), AI_move.getPosition().getY());
            symbolTokenPack.removeToken(AI_move.getToken());
            gui.highlightedDragPosOnBoard(AI_move.getPosition(), duration);

        }

        return AI_move;
    }

    /**
     * check if the position has token or not
     *
     * @param x row
     * @param y col
     * @return true, the position is occupied
     * false, the position is not occupied
     */
    public boolean isOccupied(int x, int y) {
        Position position = new Position(x, y);
        return !this.getBoard().isPositionEmpty(position);
    }

    /**
     * this method is responsible to control AI playing
     *
     * @param board              a game board
     * @param idxOfCurrentPlayer idx of current player
     */
    public void AI_playersTurn(GameBoard board, int idxOfCurrentPlayer) {

        Hand hand = players[idxOfCurrentPlayer].getHand();

        Team currTeam;
        Team oppTeam;
        if (isOrangeTeam(idxOfCurrentPlayer)) {
            currTeam = Team.ORANGE;
            oppTeam = Team.GREEN;

        } else {
            currTeam = Team.GREEN;
            oppTeam = Team.ORANGE;
        }

        if (hand.containAllReplaceToken()) {
            gui.alertNoAvailableTokenForPlayer();
        }
        if (hand.containAllSymbolToken()) {
            symbolMove = useSymbolToken_AI(idxOfCurrentPlayer);
            chosenToken = symbolMove.getToken();

        } else if (hand.containActionToken() && hand.containSymbolToken()) {

            if (boardHasChanceToCompleteSixToken(board, currTeam)) {
                int actionTokenOrdinal = analyzeActionTokenOnHand(idxOfCurrentPlayer, board, true);

                if (actionTokenOrdinal == Token.NONE.getIndex()) {
                    symbolMove = useSymbolToken_AI(idxOfCurrentPlayer);
                    chosenToken = symbolMove.getToken();
                } else {
                    recordAIBoardPos = useActionToken_hasSixToken_AI(idxOfCurrentPlayer, actionTokenOrdinal);
                    chosenToken = Token.getToken(actionTokenOrdinal);
                }

            } else if (boardHasChanceToCompleteSixToken(board, oppTeam)) {
                int actionTokenOrdinal = analyzeActionTokenOnHand(idxOfCurrentPlayer, board, false);
                if (actionTokenOrdinal == Token.NONE.getIndex()) {
                    symbolMove = useSymbolToken_AI(idxOfCurrentPlayer);
                    chosenToken = symbolMove.getToken();
                } else {
                    recordAIBoardPos = useActionToken_hasSixToken_AI(idxOfCurrentPlayer, actionTokenOrdinal);
                    chosenToken = Token.getToken(actionTokenOrdinal);
                }
            } else {
                symbolMove = useSymbolToken_AI(idxOfCurrentPlayer);
                chosenToken = symbolMove.getToken();
            }
        } else if (hand.containAllActionToken()) {

            if (boardHasChanceToCompleteSixToken(board, currTeam)) {

                int actionTokenOrdinal = analyzeActionTokenOnHand(idxOfCurrentPlayer, board, true);
                if (actionTokenOrdinal == Token.NONE.getIndex()) {
                    int tokenOrdinal = getTheFirstActionToken(idxOfCurrentPlayer);
                    recordAIBoardPos = useActionToken_achieveBestScore_AI(idxOfCurrentPlayer, tokenOrdinal);
                    chosenToken = Token.getToken(tokenOrdinal);

                } else {
                    recordAIBoardPos = useActionToken_hasSixToken_AI(idxOfCurrentPlayer, actionTokenOrdinal);
                    chosenToken = Token.getToken(actionTokenOrdinal);

                }
            } else if (boardHasChanceToCompleteSixToken(board, oppTeam)) {
                int actionTokenOrdinal = analyzeActionTokenOnHand(idxOfCurrentPlayer, board, false);
                if (actionTokenOrdinal == Token.NONE.getIndex()) {
                    int tokenOrdinal = getTheFirstActionToken(idxOfCurrentPlayer);
                    recordAIBoardPos = useActionToken_achieveBestScore_AI(idxOfCurrentPlayer, tokenOrdinal);
                    chosenToken = Token.getToken(tokenOrdinal);

                } else {
                    recordAIBoardPos = useActionToken_hasSixToken_AI(idxOfCurrentPlayer, actionTokenOrdinal);
                    chosenToken = Token.getToken(actionTokenOrdinal);

                }
            } else {
                int tokenOrdinal = getTheFirstActionToken(idxOfCurrentPlayer);
                recordAIBoardPos = useActionToken_achieveBestScore_AI(idxOfCurrentPlayer, tokenOrdinal);
                chosenToken = Token.getToken(tokenOrdinal);

            }
        }

        updateRemainActionTokenDisplay();
        updatePoint();

    }

    /**
     * log method to record the token for AI
     */
    private void logForAIPlacingToken() {
        assert chosenToken != null;
        //use for writing a log
        if (chosenToken.getIndex() <= Token.STAR.getIndex() && Token.NONE.getIndex() < chosenToken.getIndex()) {
            logUsingSymbol(idxOfCurrentPlayer, symbolMove.getPosition(), chosenToken.getIndex());
        } else if (chosenToken.equals(Token.REMOVE)) {
            logRemoveAndReplace(idxOfCurrentPlayer, recordAIBoardPos[0], Token.REMOVE.getIndex());
        } else if (chosenToken.equals(Token.REPLACER)) {
            logRemoveAndReplace(idxOfCurrentPlayer, recordAIBoardPos[0], Token.REPLACER.getIndex());
        } else if (chosenToken.equals(Token.SHIFTER)) {
            logShifterAndExchange(idxOfCurrentPlayer, recordAIBoardPos[0], recordAIBoardPos[1], Token.SHIFTER.getIndex());
        } else if (chosenToken.equals(Token.EXCHANGE)) {
            logShifterAndExchange(idxOfCurrentPlayer, recordAIBoardPos[0], recordAIBoardPos[1], Token.EXCHANGE.getIndex());
        }
    }


    /**
     * get the first action token from player hand
     *
     * @param idxOfCurrentPlayer idx of current player
     * @return the first action token index on hand
     */
    private int getTheFirstActionToken(int idxOfCurrentPlayer) {
        int firstElement = 0;
        Hand hand = players[idxOfCurrentPlayer].getHand();

        if (hand.containTheToken(Token.REPLACER)) {
            for (int idx = 0; idx < hand.getNoOfTokensFromPack(); idx++) {
                if (!hand.getATokenOnIndex(idx).equals(Token.REPLACER)) {
                    return hand.getATokenOnIndex(idx).getIndex();
                }
            }
        }
        return hand.getATokenOnIndex(firstElement).getIndex();
    }

    /**
     * analyse the action token on hand
     *
     * @param idxOfCurrentPlayer idx of current player
     * @param board              given board
     * @param hasSixCurrent      boolean value possible has Six Current
     * @return int value the index of action token
     */
    private int analyzeActionTokenOnHand(int idxOfCurrentPlayer, GameBoard board, boolean hasSixCurrent) {
        int idx;
        Position distinctPos_curr;
        Position distinctPos_opp;
        Hand hand = players[idxOfCurrentPlayer].getHand();
        int[] lineArrayOfSix_curr;
        Token sixPossibleToken_curr;

        Team currTeam;
        Team oppTeam;
        if (isOrangeTeam(idxOfCurrentPlayer)) {
            currTeam = Team.ORANGE;
            oppTeam = Team.GREEN;
            // current team -> Orange
            int indexOfLinesHasChanceToSix_curr = getIndexOfLinesHasChanceToSix(board, currTeam);
            distinctPos_curr = getTheDistinctPosOfPossibleSixInLines(indexOfLinesHasChanceToSix_curr, board, currTeam);
            lineArrayOfSix_curr = board.getAListOfSameColumn(indexOfLinesHasChanceToSix_curr);
            sixPossibleToken_curr = Token.getToken(board.getTheMostOftenElement(lineArrayOfSix_curr));

            // opposite team -> Green
            distinctPos_opp = getTheDistinctPosOfPossibleSixInLines(indexOfLinesHasChanceToSix_curr, board, oppTeam);

        } else {
            currTeam = Team.GREEN;
            oppTeam = Team.ORANGE;

            // current team -> Green
            int indexOfLinesHasChanceToSix_curr = getIndexOfLinesHasChanceToSix(board, currTeam);
            distinctPos_curr = getTheDistinctPosOfPossibleSixInLines(indexOfLinesHasChanceToSix_curr, board, currTeam);
            lineArrayOfSix_curr = board.getAListOfSameRow(indexOfLinesHasChanceToSix_curr);
            sixPossibleToken_curr = Token.getToken(board.getTheMostOftenElement(lineArrayOfSix_curr));

            // opposite team -> Orange
            distinctPos_opp = getTheDistinctPosOfPossibleSixInLines(indexOfLinesHasChanceToSix_curr, board, oppTeam);
        }

        if (hasSixCurrent) {
            idx = actionCondition_currentHasSix(hand, board, distinctPos_curr, sixPossibleToken_curr, currTeam);
        } else {
            idx = actionCondition_oppHasSix(hand, board, distinctPos_opp, sixPossibleToken_curr);
        }

        return idx;
    }

    /**
     * for current team
     * identify which action  token should be used, which is the priority, second priority and so on
     * <p>
     * this condition order is specified, action token must start from shifter, exchange, replace, and the last is removed
     *
     * @param hand             player hand
     * @param board            game board
     * @param distinctPos      a distinct position on the line which possible has six
     * @param sixPossibleToken the token that appear most often in the line which has chance to six
     * @param team             team
     * @return the action token ordinal
     */
    private int actionCondition_currentHasSix(Hand hand, GameBoard board, Position distinctPos, Token
            sixPossibleToken, Team team) {
        int idx = 0;
        if (hand.containTheToken(Token.SHIFTER) && !board.isBoardEmpty() && !board.isFullBoard() && board.isPositionEmpty(distinctPos) && hasTargetTokenOnOtherPos(distinctPos, board, team)) {
            idx = Token.SHIFTER.getIndex();
        } else if (hand.containTheToken(Token.EXCHANGE) && !board.isBoardEmpty() && !board.isPositionEmpty(distinctPos) && hasTargetTokenOnOtherPos(distinctPos, board, team)) {
            idx = Token.EXCHANGE.getIndex();
        } else if (hand.containTheToken(Token.REPLACER) && hand.containSymbolToken() && hand.containTheToken(sixPossibleToken) && !board.isPositionEmpty(distinctPos)) {
            idx = Token.REPLACER.getIndex();
        } else if (hand.containTheToken(Token.REMOVE) && !board.isBoardEmpty() && !board.isPositionEmpty(distinctPos)) {
            idx = Token.REMOVE.getIndex();
        }
        return idx;
    }

    /**
     * for opposite team
     * identify which action  token should be used, which is the priority, second priority and so on
     * <p>
     * this condition order is specified, action token must start from the lowest ordinal.
     *
     * @param hand        player hand
     * @param board       game board
     * @param distinctPos a distinct position on the line which possible has six
     * @return the action token ordinal
     */
    private int actionCondition_oppHasSix(Hand hand, GameBoard board, Position distinctPos, Token sixPossibleToken) {
        int idx = 0;
        if (hand.containTheToken(Token.REMOVE) && !board.isBoardEmpty()) {
            idx = Token.REMOVE.getIndex();
        } else if (hand.containTheToken(Token.SHIFTER) && !board.isBoardEmpty() && !board.isFullBoard() && board.isPositionEmpty(distinctPos)) {
            idx = Token.SHIFTER.getIndex();
        } else if (hand.containTheToken(Token.EXCHANGE) && !board.isBoardEmpty()) {
            idx = Token.EXCHANGE.getIndex();
        } else if (hand.containTheToken(Token.REPLACER) && hand.containSymbolToken() && !hand.containTheToken(sixPossibleToken)) {
            idx = Token.REPLACER.getIndex();
        }
        return idx;
    }

    /**
     * a condition to check if the board contain more than two token
     *
     * @param gameBoard game field
     * @return true, not more than two
     * false, more than two
     */
    public boolean hasNotMoreThanTwoTokens(GameBoard gameBoard) {
        int count = 0;
        int minTwoTokens = 2;
        for (int col = 0; col < gameBoard.getField().length; col++) {
            for (int row = 0; row < gameBoard.getField().length; row++) {
                if (gameBoard.getCell(col, row) != Token.NONE) {
                    count++;
                }
            }
        }
        return count < minTwoTokens;
    }

    /**
     * log file to start a new game.
     */
    public void logStartANewGame() {
        Log.writeToLog("A new game is started");
    }

    /**
     * a method for log file.
     * This method can display the initialisation of players
     */
    public void logInitializePlayers() {
        StringBuilder msg = new StringBuilder();
        String AIString;
        String activeString;
        for (int idx = 0; idx < players.length; idx++) {
            if (!players[idx].getIsActive()) {
                activeString = "not involved";
                msg.append("player").append(idx).append(" ").append(activeString).append("\n");
            } else {
                if (!players[idx].getIsAI()) {
                    AIString = "human";
                } else {
                    AIString = "AI";
                }
                msg.append("player").append(idx).append(" is ").append(AIString).append(" ").append(players[idx].getHand().handToString()).append("\n");
            }
        }
        Log.writeToLog(msg.toString());
    }

    /**
     * a method for log file.
     * This method can display player using a symbol token
     *
     * @param idxOfCurrentPlayer idx of current player
     * @param position           placed token position
     * @param idxOfToken         the placed token index
     */
    public void logUsingSymbol(int idxOfCurrentPlayer, Position position, int idxOfToken) {

        StringBuilder msg = new StringBuilder();

        msg.append("player").append(idxOfCurrentPlayer).append(" places ").append(idxOfToken).append(" to ").append("(").append(position.getY()).append("/").append(position.getX()).append("), ").append("new hand is ").append(players[idxOfCurrentPlayer].getHand().handToString()).append("\n");

        for (int index = 0; index < getBoard().getField().length; index++) {
            msg.append(Arrays.toString(getBoard().getIntField()[index])).append("\n");
        }

        Log.writeToLog(msg.toString());
    }

    /**
     * a method for log file.
     * This method can display player using remove or replace token
     *
     * @param idxOfCurrentPlayer idx of current player
     * @param position           placed token position
     * @param idxOfToken         the placed token index
     */
    public void logRemoveAndReplace(int idxOfCurrentPlayer, Position position, int idxOfToken) {

        StringBuilder msg = new StringBuilder();

        msg.append("player").append(idxOfCurrentPlayer).append(" uses ").append(idxOfToken).append(" to pick from ").append("(").append(position.getY()).append("/").append(position.getX()).append("), ").append("new hand is ").append(players[idxOfCurrentPlayer].getHand().handToString()).append("\n");

        for (int index = 0; index < getBoard().getField().length; index++) {
            msg.append(Arrays.toString(getBoard().getIntField()[index])).append("\n");
        }

        Log.writeToLog(msg.toString());

    }

    /**
     * a method for log file.
     * This method can display player using a shifter and exchange token
     *
     * @param idxOfCurrentPlayer idx of current player
     * @param shiftedPosition    placed token position
     * @param targetPosition     placed token position
     * @param idxOfToken         the placed token index
     */
    public void logShifterAndExchange(int idxOfCurrentPlayer, Position shiftedPosition, Position targetPosition,
                                      int idxOfToken) {

        StringBuilder msg = new StringBuilder();
        if (idxOfToken == Token.SHIFTER.getIndex()) {
            msg.append("player").append(idxOfCurrentPlayer).append(" uses ").append(idxOfToken).append(" to shift from ").append("(").append(shiftedPosition.getY()).append("/").append(shiftedPosition.getX()).append("), ").append(" to ").append("(").append(targetPosition.getY()).append("/").append(targetPosition.getX()).append("), ").append("new hand is ").append(players[idxOfCurrentPlayer].getHand().handToString()).append("\n");
        } else {
            msg.append("player").append(idxOfCurrentPlayer).append(" uses ").append(idxOfToken).append(" to exchange from ").append("(").append(shiftedPosition.getY()).append("/").append(shiftedPosition.getX()).append("), ").append(" to ").append("(").append(targetPosition.getY()).append("/").append(targetPosition.getX()).append("), ").append("new hand is ").append(players[idxOfCurrentPlayer].getHand().handToString()).append("\n");
        }

        for (int index = 0; index < getBoard().getField().length; index++) {
            msg.append(Arrays.toString(getBoard().getIntField()[index])).append("\n");
        }

        Log.writeToLog(msg.toString());

    }

    /**
     * log written if there are no decks anymore
     */
    private void logDeck() {
        Log.writeToLog("the deck has no tokens : " + this.deck.getNoOfTokensFromPack());
    }

    /**
     * log written if there are no decks anymore
     *
     * @param name       string name
     * @param finalScore int array contains final two scores
     */
    public void logEnd(String name, int[] finalScore) {
        Log.writeToLog("The game is end  " + "\n" + "The winner is  " + name + "\n" + "The Orange Team gets  " + finalScore[0] + "\n" + "The Green Team gets  " + finalScore[1]);
    }

    /**
     * update deck after load a file
     */
    public void updateDeck() {
        deck = Pack.createAPackOfEveryTokens();
        actionTokenPack = Pack.createAPackOfActionTokens();

        for (int i = 0; i < this.getBoard().getField().length; i++) {
            for (int j = 0; j < this.getBoard().getField().length; j++) {
                Token token = this.getBoard().getCell(i, j);
                deck.removeToken(token);
            }
        }

        for (int player_index = 0; player_index < playerCount; player_index++) {
            for (int handIndex = 0; handIndex < TOKEN_AMOUNT; handIndex++) {
                Token token = this.getPlayers()[player_index].getHand().getATokenOnIndex(handIndex);
                if (token != null) {
                    deck.removeToken(token);
                }
            }
        }
        int noLeftToken = 0;
        for (int index_action = 0; index_action < usedActionList.length; index_action++) {

            int removeCount = usedActionList[index_action];
            if (removeCount != noLeftToken) {
                for (int index_remove = 0; index_remove < removeCount; index_remove++) {
                    Token token = getActionTokenFromIndex(index_action);
                    deck.removeToken(token);
                    actionTokenPack.removeToken(token);
                }
            }
        }

    }

    /**
     * select which action token
     *
     * @param index the given index
     * @return action token
     */
    private Token getActionTokenFromIndex(int index) {
        Token token = null;
        switch (index) {
            case 0 -> token = Token.REMOVE;
            case 1 -> token = Token.SHIFTER;
            case 2 -> token = Token.EXCHANGE;
            case 3 -> token = Token.REPLACER;
        }
        return token;
    }


}
