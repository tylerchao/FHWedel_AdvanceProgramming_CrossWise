package logic;

/**
 * Interface used for the logic of the game crossWise to communicate with the gui.
 * <p>
 * an interface GUIConnector with all the methods necessary for the JavaFXGUI and have it implemented by the class JavaFXGUI.
 * <p>
 * The GUIConnector belongs to the logic package
 * <p>
 * <p>
 *
 * @author chien-hsun
 */
public interface GUIConnector {
    /**
     * highlight a drag position on board
     *
     * @param dragPos      drag position
     * @param durationTime time of highlighting a region
     */
    void highlightedDragPosOnBoard(Position dragPos, double durationTime);

    /**
     * add a highlight region on hand
     *
     * @param idxOfPlayer idx of player
     * @param pos         position to highlight
     */
    void addHighlightedOnHand(int idxOfPlayer, Position pos);

    /**
     * highlight a shifted position
     *
     * @param shift        shifted position
     * @param durationTime time of highlighting a region
     */
    void highlightedShiftedPosOnBoard(Position shift, double durationTime);

    /**
     * highlight a target position
     *
     * @param target       target position
     * @param durationTime time of highlighting a region
     */
    void highlightedTargetPosOnBoard(Position target, double durationTime);

    /**
     * cover cross board with the image none
     * this image is used for the beginning of the game when no tokens are putting on the board
     *
     * @param col col
     * @param row row
     */
    void coverNoneToken(int col, int row);

    /**
     * set the current player name and team color
     *
     * @param name  player name
     * @param color player team color
     */
    void setCurrentPlayer(String name, String color);

    /**
     * set the current team score
     *
     * @param OrangeScore score of orange
     * @param GreenScore  score of green
     */
    void setTeamScore(String OrangeScore, String GreenScore);

    /**
     * scoring of each line by passing some parameters.
     *
     * @param Orange     boolean value
     * @param lineNumber line number
     * @param points     input points
     */
    void setBoardPoints(boolean Orange, int lineNumber, int points);

    /**
     * call once the game is ended
     *
     * @param name  given name
     * @param score int array of score points
     */
    void gameHasEnded(String name, int[] score);

    /**
     * call once the game is ended
     */
    void alertExchangeToken();

    /**
     * get tokens in each players' hands
     *
     * @param x            x-coordinate of the position
     * @param y            y-coordinate of the position
     * @param tokenIndex   index of token
     * @param playersIndex index of player
     */
    void showHandsToken(int x, int y, int tokenIndex, int playersIndex);

    /**
     * show token image on the board
     *
     * @param tokenIndex given token index
     * @param x          coordinate x
     * @param y          coordinate y
     */
    void showTokenImageOnBoard(int tokenIndex, int x, int y);

    /**
     * set the remain action tokens
     *
     * @param actionTokensIndex    action token index
     * @param remainActionTokenNum remain action token number
     */
    void setRemainActionTokens(int actionTokensIndex, int remainActionTokenNum);

    /**
     * setting image for remove token
     *
     * @param removed_x   removed row
     * @param removed_y   removed col
     * @param playerIndex index of player
     * @param hands_x     hand row
     * @param hands_y     hand col
     */
    void setImgForRemove(int removed_x, int removed_y, int playerIndex, int hands_x, int hands_y);

    /**
     * setting image for shifting token
     *
     * @param shifted_x   shifted row
     * @param shifted_y   shifted col
     * @param target_x    target row
     * @param target_y    target col
     * @param playerIndex index of player
     */
    void setImgForShifting(int shifted_x, int shifted_y, int target_x, int target_y, int playerIndex);

    /**
     * setting image for exchange token
     *
     * @param shifted_x shifted row
     * @param shifted_y shifted col
     * @param target_x  target row
     * @param target_y  target col
     */
    void setImgForExchange(int shifted_x, int shifted_y, int target_x, int target_y);

    /**
     * set the image for replace movement
     *
     * @param shift_x   shifted row
     * @param shift_y   shifted col
     * @param target_x  target row
     * @param target_y  target col
     * @param playerIdx index of player
     */
    void setImgForReplace(int shift_x, int shift_y, int target_x, int target_y, int playerIdx);

    /**
     * set image to null
     *
     * @param playerIndex player index
     * @param y           y-coordinate of the position
     * @param x           x-coordinate of the position
     */
    void cleanImg(int playerIndex, int x, int y);

    /**
     * highlight the area of the replaced token
     *
     * @param idxOfPlayer index of player
     * @param shift       shifted position
     * @param AI          boolean value of AI player
     */
    void highlightedShiftedPosForReplace(int idxOfPlayer, Position shift, boolean AI, double durationTime);

    /**
     * remove region on board
     */
    void removeRegionOnBoard();

    /**
     * add a highlighted region on the hands of the player
     */
    void removeHandRegionOnHand();

    /**
     * add a highlighted region on the hands of the player
     */
    void removeShiftedRegionOnHand();

    /**
     * set up the animation on the given pos
     *
     * @param move     movement
     * @param runnable Runnable interface
     */
    void setUpAnimationForMovingAToken(Move move, Runnable runnable);

    /**
     * set up the animation of remove
     *
     * @param target_x         target x coordinate
     * @param target_y         target y coordinate
     * @param idxCurrentPlayer index of current player
     * @param shifted_x        shifted x coordinate
     * @param shifted_y        shifted y coordinate
     * @param runnable         Runnable interface
     */
    void setUpAnimationForRemove(int target_x, int target_y, int idxCurrentPlayer, int shifted_x, int shifted_y, Runnable runnable);

    /**
     * set up the animation of Shifting
     *
     * @param target_x target x coordinate
     * @param target_y target y coordinate
     * @param runnable Runnable interface
     */
    void setUpAnimationForShifting(int target_x, int target_y, Runnable runnable);

    /**
     * set up the animation of Exchange
     *
     * @param shifted_x shifted x coordinate
     * @param shifted_y shifted y coordinate
     * @param target_x  target x coordinate
     * @param target_y  target y coordinate
     * @param runnable  Runnable interface
     */
    void setUpAnimationForExchange(int shifted_x, int shifted_y, int target_x, int target_y, Runnable runnable);

    /**
     * set up the animation of replace
     *
     * @param target_x         target x coordinate
     * @param target_y         target x coordinate
     * @param idxCurrentPlayer idx of current player
     * @param shifted_x        shifted x coordinate
     * @param shifted_y        shifted y coordinate
     */
    void setUpAnimationForReplace(int target_x, int target_y, int idxCurrentPlayer, int shifted_x, int shifted_y, Runnable runnable);

    /**
     * hide the deck of the given player
     *
     * @param idxOfPlayer index of current player
     */
    void hiddenDeck(int idxOfPlayer);

    /**
     * displaying save and load has a file chosen error.
     *
     * @param ex the exception
     */
    void displaySaveAndLoadError(Exception ex);

    /**
     * warning the termination of AI playing is just activated
     */
    void stopAIRunning();

    /**
     * alert the first player has no symbol token
     */
    void alertNoAvailableTokenForPlayer();

    /**
     * alert the player amount is not correct
     */
    void alertPlayerAmount();
}
