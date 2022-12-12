package logic;

/**
 * the FakeGUI to the test package
 */
public class FakeGUI implements GUIConnector {

    @Override
    public void setCurrentPlayer(String name, String color) {
    }

    @Override
    public void setTeamScore(String OrangeScore, String GreenScore) {
    }

    @Override
    public void setBoardPoints(boolean horizontal, int lineNumber, int points) {
    }

    @Override
    public void gameHasEnded(String name, int[] score) {
    }

    @Override
    public void alertExchangeToken() {

    }

    @Override
    public void coverNoneToken(int col, int row) {
    }

    @Override
    public void showHandsToken(int x, int y, int tokenIndex, int playersIndex) {
    }

    @Override
    public void showTokenImageOnBoard(int tokenIndex, int x, int y) {
    }

    @Override
    public void setRemainActionTokens(int actionTokensIndex, int remainActionTokenNum) {
    }

    @Override
    public void cleanImg(int playerIndex, int x, int y) {
    }

    @Override
    public void setImgForRemove(int removed_x, int removed_y, int playerIndex, int hands_x, int hands_y) {
    }

    @Override
    public void setImgForShifting(int shifted_x, int shifted_y, int target_x, int target_y, int playerIndex) {
    }

    @Override
    public void setImgForReplace(int hand_x, int hand_y, int target_x, int target_y, int playerIdx) {
    }

    @Override
    public void setImgForExchange(int shifted_x, int shifted_y, int target_x, int target_y) {
    }

    @Override
    public void addHighlightedOnHand(int idxOfPlayer, Position pos) {
    }

    @Override
    public void highlightedShiftedPosOnBoard(Position shift, double durationTime) {
    }

    @Override
    public void highlightedTargetPosOnBoard(Position target, double durationTime) {
    }

    @Override
    public void highlightedShiftedPosForReplace(int idxOfPlayer, Position shift, boolean AI, double durationTime) {
    }

    @Override
    public void highlightedDragPosOnBoard(Position dragPos, double durationTime) {
    }

    @Override
    public void removeRegionOnBoard() {
    }

    @Override
    public void removeHandRegionOnHand() {
    }

    @Override
    public void removeShiftedRegionOnHand() {
    }

    @Override
    public void setUpAnimationForMovingAToken(Move move, Runnable runnable) {
    }

    @Override
    public void setUpAnimationForRemove(int target_x, int target_y, int idxCurrentPlayer, int shifted_x, int shifted_y, Runnable runnable) {
    }

    @Override
    public void setUpAnimationForShifting(int target_x, int target_y, Runnable runnable) {
    }

    @Override
    public void setUpAnimationForExchange(int shifted_x, int shifted_y, int target_x, int target_y, Runnable runnable) {
    }

    @Override
    public void setUpAnimationForReplace(int target_x, int target_y, int idxCurrentPlayer, int shifted_x, int shifted_y, Runnable runnable) {
    }

    @Override
    public void hiddenDeck(int idxOfPlayer) {
    }

    @Override
    public void displaySaveAndLoadError(Exception ex) {
    }

    @Override
    public void stopAIRunning() {
    }

    @Override
    public void alertNoAvailableTokenForPlayer() {
    }

    @Override
    public void alertPlayerAmount() {
    }

}
