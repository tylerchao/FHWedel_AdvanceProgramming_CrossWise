package gui;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.util.Duration;
import logic.*;

/**
 * first, contains all components in the constructor that it should be able to update (e.g. various images for maps, labels or even entire panes).
 * The methods of the JavaFXGUI allow the logic to change the output GUI
 * (e.g. method showCards(Player player1) displays the cards of a player by updating the corresponding ImageViews).
 * <p>
 * the JavaFXGUI to the GUI package,
 *
 * @author chien-hsun
 */
public class JavaFxGUI implements GUIConnector {

    private static final int FONT_SIZE = 20;
    private static final int IMG_WIDTH = 100;
    private static final int IMG_HEIGHT = 100;

    private static final boolean RATIO = false;
    private static final boolean SMOOTH = false;

    private final CheckMenuItem comHandCb;
    private final Label lblCurrentPlayer;
    private final Label[] label_OrangeBoardPoint, label_GreenBoardPoint, teamScoreColor, labelActionTokenNumber;
    private final ImageView[][] crossBoard;

    private final ImageView[][][] hand;
    private final Region handRegion;
    private final Region shiftedRegion;
    private final Region targetRegion;
    private final Region crossBoardRegion;

    private final GridPane grdCross;

    private final GridPane[] gridPanesPlayers;

    private static final Image IMG_NONE = new Image("gui/img/covered.png", IMG_WIDTH, IMG_HEIGHT, RATIO, SMOOTH);
    private static final Image IMG_SUN = new Image("gui/img/sun.png", IMG_WIDTH, IMG_HEIGHT, RATIO, SMOOTH);
    private static final Image IMG_CROSS = new Image("gui/img/cross.png", IMG_WIDTH, IMG_HEIGHT, RATIO, SMOOTH);
    private static final Image IMG_TRIANGLE = new Image("gui/img/triangle.png", IMG_WIDTH, IMG_HEIGHT, RATIO, SMOOTH);
    private static final Image IMG_SQUARE = new Image("gui/img/square.png", IMG_WIDTH, IMG_HEIGHT, RATIO, SMOOTH);
    private static final Image IMG_PENTAGON = new Image("gui/img/pentagon.png", IMG_WIDTH, IMG_HEIGHT, RATIO, SMOOTH);
    private static final Image IMG_STAR = new Image("gui/img/star.png", IMG_WIDTH, IMG_HEIGHT, RATIO, SMOOTH);
    private static final Image IMG_REMOVE = new Image("gui/img/remove.png", IMG_WIDTH, IMG_HEIGHT, RATIO, SMOOTH);
    private static final Image IMG_SHIFTER = new Image("gui/img/move.png", IMG_WIDTH, IMG_HEIGHT, RATIO, SMOOTH);
    private static final Image IMG_EXCHANGE = new Image("gui/img/swapOnBoard.png", IMG_WIDTH, IMG_HEIGHT, RATIO, SMOOTH);
    private static final Image IMG_REPLACER = new Image("gui/img/swapWithHand.png", IMG_WIDTH, IMG_HEIGHT, RATIO, SMOOTH);


    /**
     * passing parameter to update the element which should be updated consistently
     *
     * @param crossBoard             game board
     * @param handsP1                player 1 hands
     * @param handsP2                player 2 hands
     * @param handsP3                player 3 hands
     * @param handsP4                player 4 hands
     * @param lblCurrentPlayer       label of current player
     * @param teamScoreColor         label of team color
     * @param label_OrangeBoardPoint label of horizontal point
     * @param label_GreenBoardPoint  label of vertical point
     * @param labelActionTokenNumber label of all used action token
     */
    public JavaFxGUI(CheckMenuItem comHandCb, GridPane gridCross, GridPane gridP1, GridPane gridP2, GridPane gridP3, GridPane gridP4, ImageView[][] crossBoard, ImageView[][] handsP1, ImageView[][] handsP2, ImageView[][] handsP3, ImageView[][] handsP4, Label lblCurrentPlayer, Label[] teamScoreColor, Label[] label_OrangeBoardPoint, Label[] label_GreenBoardPoint, Label[] labelActionTokenNumber, Region handRegion, Region shiftedRegion, Region targetRegion, Region crossBoardRegion) {
        this.comHandCb = comHandCb;
        this.grdCross = gridCross;
        this.crossBoard = crossBoard;
        this.lblCurrentPlayer = lblCurrentPlayer;
        this.label_OrangeBoardPoint = label_OrangeBoardPoint;
        this.label_GreenBoardPoint = label_GreenBoardPoint;
        this.teamScoreColor = teamScoreColor;
        this.labelActionTokenNumber = labelActionTokenNumber;
        this.handRegion = handRegion;
        this.shiftedRegion = shiftedRegion;

        this.targetRegion = targetRegion;
        this.crossBoardRegion = crossBoardRegion;
        hand = new ImageView[][][]{
                handsP1, handsP2, handsP3, handsP4
        };

        gridPanesPlayers = new GridPane[]{
                gridP1, gridP2, gridP3, gridP4
        };

    }


    /**
     * cover cross board with the image none
     * this image is used for the beginning of the game when no tokens are putting on the board
     *
     * @param col col
     * @param row row
     */
    @Override
    public void coverNoneToken(int col, int row) {
        this.crossBoard[col][row].setImage(IMG_NONE);

    }

    /**
     * get tokens in each players' hands
     *
     * @param x            x-coordinate of the position
     * @param y            y-coordinate of the position
     * @param tokenIndex   index of token
     * @param playersIndex index of player
     */
    @Override
    public void showHandsToken(int x, int y, int tokenIndex, int playersIndex) {
        showTokenImageOnHand(tokenIndex, hand[playersIndex], x, y);
    }

    /**
     * show token image on player hand
     *
     * @param tokenIndex index of token
     * @param hands      index of player
     * @param x          x-coordinate of the position
     * @param y          y-coordinate of the position
     */
    private void showTokenImageOnHand(int tokenIndex, ImageView[][] hands, int x, int y) {
        switch (tokenIndex) {
            case 0 -> hands[x][y].setImage(IMG_NONE);
            case 1 -> hands[x][y].setImage(IMG_SUN);
            case 2 -> hands[x][y].setImage(IMG_CROSS);
            case 3 -> hands[x][y].setImage(IMG_TRIANGLE);
            case 4 -> hands[x][y].setImage(IMG_SQUARE);
            case 5 -> hands[x][y].setImage(IMG_PENTAGON);
            case 6 -> hands[x][y].setImage(IMG_STAR);
            case 7 -> hands[x][y].setImage(IMG_REMOVE);
            case 8 -> hands[x][y].setImage(IMG_SHIFTER);
            case 9 -> hands[x][y].setImage(IMG_EXCHANGE);
            case 10 -> hands[x][y].setImage(IMG_REPLACER);
            default -> hands[x][y].getImage().cancel();
        }
    }

    /**
     * show token image on the board
     *
     * @param tokenIndex given token index
     * @param x          coordinate x
     * @param y          coordinate y
     */
    @Override
    public void showTokenImageOnBoard(int tokenIndex, int x, int y) {
        switch (tokenIndex) {
            case 0 -> crossBoard[y][x].setImage(IMG_NONE);
            case 1 -> crossBoard[y][x].setImage(IMG_SUN);
            case 2 -> crossBoard[y][x].setImage(IMG_CROSS);
            case 3 -> crossBoard[y][x].setImage(IMG_TRIANGLE);
            case 4 -> crossBoard[y][x].setImage(IMG_SQUARE);
            case 5 -> crossBoard[y][x].setImage(IMG_PENTAGON);
            case 6 -> crossBoard[y][x].setImage(IMG_STAR);
            case 7 -> crossBoard[y][x].setImage(IMG_REMOVE);
            case 8 -> crossBoard[y][x].setImage(IMG_SHIFTER);
            case 9 -> crossBoard[y][x].setImage(IMG_EXCHANGE);
            case 10 -> crossBoard[y][x].setImage(IMG_REPLACER);
            default -> crossBoard[y][x].getImage().cancel();
        }
    }

    /**
     * set the remain action tokens
     *
     * @param actionTokensIndex    action token index
     * @param remainActionTokenNum remain action token number
     */
    @Override
    public void setRemainActionTokens(int actionTokensIndex, int remainActionTokenNum) {
        if (actionTokensIndex == Token.REMOVE.getIndex()) {
            labelActionTokenNumber[0].setText(String.valueOf(remainActionTokenNum));
            labelActionTokenNumber[0].setFont(new Font(FONT_SIZE));
            labelActionTokenNumber[0].setAlignment(Pos.CENTER);

        } else if (actionTokensIndex == Token.SHIFTER.getIndex()) {
            labelActionTokenNumber[1].setText(String.valueOf(remainActionTokenNum));
            labelActionTokenNumber[1].setFont(new Font(FONT_SIZE));
            labelActionTokenNumber[1].setAlignment(Pos.CENTER);
        } else if (actionTokensIndex == Token.EXCHANGE.getIndex()) {
            labelActionTokenNumber[2].setText(String.valueOf(remainActionTokenNum));
            labelActionTokenNumber[2].setFont(new Font(FONT_SIZE));
            labelActionTokenNumber[2].setAlignment(Pos.CENTER);
        } else if (actionTokensIndex == Token.REPLACER.getIndex()) {
            labelActionTokenNumber[3].setText(String.valueOf(remainActionTokenNum));
            labelActionTokenNumber[3].setFont(new Font(FONT_SIZE));
            labelActionTokenNumber[3].setAlignment(Pos.CENTER);
        }
    }

    /**
     * set the current player name and team color
     *
     * @param name  player name
     * @param color player team color
     */
    @Override
    public void setCurrentPlayer(String name, String color) {
        lblCurrentPlayer.setText(name);
        lblCurrentPlayer.setFont(new Font(FONT_SIZE));
        lblCurrentPlayer.setAlignment(Pos.CENTER);

        if (color.equals("GREEN")) {
            lblCurrentPlayer.setStyle("-fx-background-color: #00ff7f");
        } else if (color.equals("ORANGE")) {
            lblCurrentPlayer.setStyle("-fx-background-color: #ffa500");
        } else {
            lblCurrentPlayer.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5);");
        }
    }

    /**
     * hide the deck of the given player
     *
     * @param idxOfPlayer index of current player
     */
    @Override
    public void hiddenDeck(int idxOfPlayer) {
        if(comHandCb.isSelected()){
            for (GridPane gridPanesPlayer : gridPanesPlayers) {
                gridPanesPlayer.setVisible(true);
            }
        }else{
            for (int i = 0; i < gridPanesPlayers.length; i++) {
                gridPanesPlayers[i].setVisible(i == idxOfPlayer);
            }
        }

    }

    /**
     * set the current team score
     *
     * @param OrangeScore score of orange
     * @param GreenScore  score of green
     */
    @Override
    public void setTeamScore(String OrangeScore, String GreenScore) {
        teamScoreColor[0].setText(GreenScore);
        teamScoreColor[0].setFont(new Font(FONT_SIZE));
        teamScoreColor[0].setAlignment(Pos.CENTER);
        teamScoreColor[1].setText(OrangeScore);
        teamScoreColor[1].setFont(new Font(FONT_SIZE));
        teamScoreColor[1].setAlignment(Pos.CENTER);
    }


    /**
     * scoring of each line by passing some parameters.
     *
     * @param Orange     boolean value
     * @param lineNumber line number
     * @param points     input points
     */
    @Override
    public void setBoardPoints(boolean Orange, int lineNumber, int points) {
        if (Orange) {
            label_OrangeBoardPoint[lineNumber].setText(String.valueOf(points));
            label_OrangeBoardPoint[lineNumber].setFont(new Font(FONT_SIZE));
            label_OrangeBoardPoint[lineNumber].setAlignment(Pos.CENTER);
        } else {
            label_GreenBoardPoint[lineNumber].setText(String.valueOf(points));
            label_GreenBoardPoint[lineNumber].setFont(new Font(FONT_SIZE));
            label_GreenBoardPoint[lineNumber].setAlignment(Pos.CENTER);
        }
    }


    /**
     * set image to null
     *
     * @param playerIndex player index
     * @param y           y-coordinate of the position
     * @param x           x-coordinate of the position
     */
    @Override
    public void cleanImg(int playerIndex, int x, int y) {
        assert hand[playerIndex] != null;
        hand[playerIndex][y][x].setImage(null);

    }

    /**
     * setting image for remove token
     *
     * @param removed_x   removed row
     * @param removed_y   removed col
     * @param playerIndex index of player
     * @param hands_x     hand row
     * @param hands_y     hand col
     */
    @Override
    public void setImgForRemove(int removed_x, int removed_y, int playerIndex, int hands_x, int hands_y) {

        Image removedImg = crossBoard[removed_y][removed_x].getImage();


        crossBoard[removed_y][removed_x].getImage().cancel();
        coverNoneToken(removed_y, removed_x);

        assert hand[playerIndex] != null;
        hand[playerIndex][hands_y][hands_x].getImage().cancel();
        hand[playerIndex][hands_y][hands_x].setImage(removedImg);

    }

    /**
     * setting image for shifting token
     *
     * @param shifted_x   shifted row
     * @param shifted_y   shifted col
     * @param target_x    target row
     * @param target_y    target col
     * @param playerIndex index of player
     */
    @Override
    public void setImgForShifting(int shifted_x, int shifted_y, int target_x, int target_y, int playerIndex) {
        Image shiftedImage = crossBoard[shifted_y][shifted_x].getImage();
        crossBoard[target_y][target_x].setImage(shiftedImage);
        coverNoneToken(shifted_y, shifted_x);
    }

    /**
     * setting image for exchange token
     *
     * @param shifted_x shifted row
     * @param shifted_y shifted col
     * @param target_x  target row
     * @param target_y  target col
     */
    @Override
    public void setImgForExchange(int shifted_x, int shifted_y, int target_x, int target_y) {
        Image shiftedImage = crossBoard[shifted_y][shifted_x].getImage();
        Image targetImage = crossBoard[target_y][target_x].getImage();

        crossBoard[target_y][target_x].setImage(shiftedImage);
        crossBoard[shifted_y][shifted_x].getImage().cancel();
        crossBoard[shifted_y][shifted_x].setImage(targetImage);

    }

    /**
     * set the image for replace movement
     *
     * @param shift_x   shifted row
     * @param shift_y   shifted col
     * @param target_x  target row
     * @param target_y  target col
     * @param playerIdx index of player
     */
    @Override
    public void setImgForReplace(int shift_x, int shift_y, int target_x, int target_y, int playerIdx) {
        assert hand[playerIdx] != null;
        Image shiftedImage = hand[playerIdx][shift_y][shift_x].getImage();
        Image targetImage = crossBoard[target_y][target_x].getImage();
        coverNoneToken(target_y, target_x);
        crossBoard[target_y][target_x].getImage().cancel();
        crossBoard[target_y][target_x].setImage(shiftedImage);
        cleanImg(playerIdx, shift_x, shift_y);
        hand[playerIdx][shift_y][shift_x].setImage(targetImage);

    }

    /**
     * call once the game is ended
     *
     * @param name  given name
     * @param score int array of score points
     */
    @Override
    public void gameHasEnded(String name, int[] score) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game has ended");
        alert.setHeaderText(name + " win");
        alert.setContentText("Orange score: " + score[0] + "\r\n" + "Green score " + score[1] + "\r\n" +
                             "If you want to you can start a new game");

        alert.show();

    }

    /**
     * call once the game is ended
     */
    @Override
    public void alertExchangeToken() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Exchange Token Warning");
        alert.setContentText("Exchange token must have more than two tokens on board ");
        alert.showAndWait();
    }

    /**
     * add a highlight region on hand
     *
     * @param idxOfPlayer idx of player
     * @param pos         position to highlight
     */
    @Override
    public void addHighlightedOnHand(int idxOfPlayer, Position pos) {
        String style;
        switch (idxOfPlayer) {
            case 0, 2 -> {
                style = "-fx-border-color:  #ffa500; -fx-border-width:  3";
                handRegion.setStyle(style);
            }
            case 1, 3 -> {
                style = "-fx-border-color:   #00ff7f; -fx-border-width:  3";
                handRegion.setStyle(style);
            }
        }
        gridPanesPlayers[idxOfPlayer].add(handRegion, pos.getY(), pos.getX());

    }

    /**
     * highlight the area of the replaced token
     *
     * @param idxOfPlayer index of player
     * @param shift       shifted position
     * @param AI          boolean value of AI player
     */
    @Override
    public void highlightedShiftedPosForReplace(int idxOfPlayer, Position shift, boolean AI, double durationTime) {

        String style = "-fx-border-color:  #FF0000; -fx-border-width:  3";

        shiftedRegion.setStyle(style);

        if (AI) {
            switch (idxOfPlayer) {
                case 0, 2 -> gridPanesPlayers[idxOfPlayer].add(shiftedRegion, shift.getY(), shift.getX());
                case 1, 3 -> gridPanesPlayers[idxOfPlayer].add(shiftedRegion, shift.getX(), shift.getY());
            }
        } else {
            gridPanesPlayers[idxOfPlayer].add(shiftedRegion, shift.getY(), shift.getX());
        }
        setUpDurationForHighlight(durationTime, shiftedRegion);
    }

    /**
     * highlight a shifted position
     *
     * @param shift        shifted position
     * @param durationTime time of highlighting a region
     */
    @Override
    public void highlightedShiftedPosOnBoard(Position shift, double durationTime) {

        String style1 = "-fx-border-color:  #FF0000; -fx-border-width:  3";

        shiftedRegion.setStyle(style1);

        grdCross.add(shiftedRegion, shift.getY(), shift.getX());
        setUpDurationForHighlight(durationTime, shiftedRegion);
    }

    /**
     * highlight a target position
     *
     * @param target       target position
     * @param durationTime time of highlighting a region
     */
    @Override
    public void highlightedTargetPosOnBoard(Position target, double durationTime) {

        String style2 = "-fx-border-color:  #FF0000; -fx-border-width:  3";

        targetRegion.setStyle(style2);

        grdCross.add(targetRegion, target.getY(), target.getX());

        setUpDurationForHighlight(durationTime, targetRegion);

    }

    /**
     * highlight a drag position on board
     *
     * @param dragPos      drag position
     * @param durationTime time of highlighting a region
     */
    @Override
    public void highlightedDragPosOnBoard(Position dragPos, double durationTime) {

        String style = "-fx-border-color:  #FF0000; -fx-border-width:  3";

        crossBoardRegion.setStyle(style);

        grdCross.add(crossBoardRegion, dragPos.getY(), dragPos.getX());
        setUpDurationForHighlight(durationTime, crossBoardRegion);
    }

    /**
     * remove region on board
     */
    @Override
    public void removeRegionOnBoard() {
        grdCross.getChildren().remove(crossBoardRegion);
        grdCross.getChildren().remove(shiftedRegion);
        grdCross.getChildren().remove(targetRegion);
    }

    /**
     * add a highlighted region on the hands of the player
     */
    @Override
    public void removeHandRegionOnHand() {
        for (GridPane gridPanesPlayer : gridPanesPlayers) {
            gridPanesPlayer.getChildren().remove(handRegion);
        }
    }

    /**
     * add a highlighted region on the hands of the player
     */
    @Override
    public void removeShiftedRegionOnHand() {
        for (GridPane gridPanesPlayer : gridPanesPlayers) {
            gridPanesPlayer.getChildren().remove(shiftedRegion);
        }
    }

    /**
     * set up the duration of the highlight area
     *
     * @param time   time of highlighting a region
     * @param region a region
     */
    private void setUpDurationForHighlight(double time, Region region) {
        FadeTransition fade = new FadeTransition();
        fade.setNode(region);
        fade.setDuration(Duration.millis(time));
        fade.setFromValue(1);
        fade.setToValue(0);
        fade.play();
    }

    /**
     * set up the animation on the given pos
     *
     * @param move     movement
     * @param runnable Runnable interface
     */
    @Override
    public void setUpAnimationForMovingAToken(Move move, Runnable runnable) {
        FadeTransition fade = new FadeTransition();
        fade.setNode(this.crossBoard[move.getPosition().getY()][move.getPosition().getX()]);
        fade.setDuration(Duration.millis(UserInterfaceController.ANIMATION_DURATION));
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.setOnFinished(actionEvent -> runnable.run());
        fade.play();

    }

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
    @Override
    public void setUpAnimationForRemove(int target_x, int target_y, int idxCurrentPlayer, int shifted_x, int shifted_y, Runnable runnable) {
        FadeTransition fade_target = new FadeTransition();
        fade_target.setNode(this.crossBoard[target_y][target_x]);
        fade_target.setDuration(Duration.millis(UserInterfaceController.ANIMATION_DURATION));
        fade_target.setFromValue(0);
        fade_target.setToValue(1);

        FadeTransition fade_shift = new FadeTransition();
        fade_shift.setDuration(Duration.millis(UserInterfaceController.ANIMATION_DURATION));
        fade_shift.setFromValue(0);
        fade_shift.setToValue(1);

        fade_shift.setNode(this.hand[idxCurrentPlayer][shifted_y][shifted_x]);

        ParallelTransition parallelTransition = new ParallelTransition(fade_shift, fade_target);
        parallelTransition.setOnFinished(actionEvent ->
                runnable.run()
        );
        parallelTransition.play();
    }


    /**
     * set up the animation of Shifting
     *
     * @param target_x target x coordinate
     * @param target_y target y coordinate
     * @param runnable Runnable interface
     */
    @Override
    public void setUpAnimationForShifting(int target_x, int target_y, Runnable runnable) {

        FadeTransition fade_target = new FadeTransition();
        fade_target.setNode(this.crossBoard[target_y][target_x]);
        fade_target.setDuration(Duration.millis(UserInterfaceController.ANIMATION_DURATION));
        fade_target.setFromValue(0);
        fade_target.setToValue(1);
        fade_target.setOnFinished(actionEvent -> runnable.run());
        fade_target.play();
    }

    /**
     * set up the animation of Exchange
     *
     * @param shifted_x shifted x coordinate
     * @param shifted_y shifted y coordinate
     * @param target_x  target x coordinate
     * @param target_y  target y coordinate
     * @param runnable  Runnable interface
     */
    @Override
    public void setUpAnimationForExchange(int shifted_x, int shifted_y, int target_x, int target_y, Runnable runnable) {

        FadeTransition fade_shift = new FadeTransition();
        fade_shift.setNode(this.crossBoard[shifted_y][shifted_x]);
        fade_shift.setDuration(Duration.millis(UserInterfaceController.ANIMATION_DURATION));
        fade_shift.setFromValue(0);
        fade_shift.setToValue(1);

        FadeTransition fade_target = new FadeTransition();
        fade_target.setNode(this.crossBoard[target_y][target_x]);
        fade_target.setDuration(Duration.millis(UserInterfaceController.ANIMATION_DURATION));
        fade_target.setFromValue(0);
        fade_target.setToValue(1);

        ParallelTransition parallelTransition = new ParallelTransition(fade_shift, fade_target);
        parallelTransition.setOnFinished(actionEvent -> runnable.run());
        parallelTransition.play();

    }

    /**
     * set up the animation of replace
     *
     * @param target_x         target x coordinate
     * @param target_y         target x coordinate
     * @param idxCurrentPlayer idx of current player
     * @param shifted_x        shifted x coordinate
     * @param shifted_y        shifted y coordinate
     */
    @Override
    public void setUpAnimationForReplace(int target_x, int target_y, int idxCurrentPlayer, int shifted_x, int shifted_y, Runnable runnable) {
        FadeTransition fade_target = new FadeTransition();
        fade_target.setNode(this.crossBoard[target_y][target_x]);
        fade_target.setDuration(Duration.millis(UserInterfaceController.ANIMATION_DURATION));
        fade_target.setFromValue(0);
        fade_target.setToValue(1);

        FadeTransition fade_shift = new FadeTransition();
        fade_shift.setDuration(Duration.millis(UserInterfaceController.ANIMATION_DURATION));
        fade_shift.setFromValue(0);
        fade_shift.setToValue(1);
        fade_shift.setNode(this.hand[idxCurrentPlayer][shifted_y][shifted_x]);


        ParallelTransition parallelTransition = new ParallelTransition(fade_shift, fade_target);
        parallelTransition.setOnFinished(actionEvent -> runnable.run());
        parallelTransition.play();
    }

    /**
     * displaying save and load has a file chosen error.
     *
     * @param ex the exception
     */
    @Override
    public void displaySaveAndLoadError(Exception ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("File does not exist ");
        alert.setHeaderText("The chosen file does not exist.");
        if (ex instanceof NullPointerException) {
            alert.setContentText(ex.getMessage());
        }
        alert.setContentText("Please select/input a file");
        alert.showAndWait();
    }

    /**
     * warning the termination of AI playing is just activated
     */
    @Override
    public void stopAIRunning() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Stop AI running");
        alert.setHeaderText("AI playing mode has been terminated.");
        alert.setContentText("please start a new game");
        alert.showAndWait();
    }

    /**
     * alert the first player has no symbol token
     */
    @Override
    public void alertNoAvailableTokenForPlayer() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Game cannot be played ");
        alert.setHeaderText("No available token for player");
        alert.setContentText("Please restart a new game");
        alert.showAndWait();
    }

    /**
     * alert the player amount is not correct
     */
    @Override
    public void alertPlayerAmount() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("The loading file is not correct");
        alert.setHeaderText("Player amount is not correct");
        alert.setContentText("Please check load file format");
        alert.showAndWait();
    }

}
