package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logic.*;

/**
 * Main class for the user interface.
 * <p>
 * Control gui of the game and all action perform by players
 * <p>
 * The UserInterfaceController creates an instance of JavaFXGUI and passes it to the game logic.
 *
 * @author chien-hsun
 */
public class UserInterfaceController implements Initializable {
    /**
     * animation duration
     */
    public static int ANIMATION_DURATION = 2000;
    private static final int HIGHLIGHTING_LONG_DURATION = 5000;
    private static final int HIGHLIGHTING_MEDIUM_DURATION = 3000;
    private static final int HIGHLIGHTING_LOW_DURATION = 1000;
    /**
     * Label that displays the welcome text. Should be deleted when creating an
     * actual project.
     */
    // label green and orange and current team color
    @FXML
    private BorderPane BoardPaneGame;
    @FXML
    private Label OrangeLabelScore, GreenLabelScore, lblCurrentPlayerName;

    // imageView
    @FXML
    private ImageView actionTokenImage1, actionTokenImage2, actionTokenImage3, actionTokenImage4;

    // Checkbox
    @FXML
    private CheckMenuItem comHandCb, currentPointsCb, rowColCb;

    //GridPane
    @FXML
    private GridPane grdPnChess, grdPnPly1, grdPnPly2, grdPnPly3, grdPnPly4;

    //duration Radio
    @FXML
    private RadioMenuItem idHighDuration, idMediumDuration, idShortDuration;

    //Label
    @FXML
    private Label lblHorz1, lblHorz2, lblHorz3, lblHorz4, lblHorz5, lblHorz6, lblVer1, lblVer2, lblVer3, lblVer4, lblVer5, lblVer6, lblCorner;

    // Action Tokens
    @FXML
    private Label lbNumRemoveToken, lbNumShifterToken, lbNumExchangeToken, lbNumReplaceToken;

    // All label
    @FXML
    private Label lbP1Nm, lbP2Nm, lbP3Nm, lbP4Nm;

    //RadioButton
    @FXML
    private CheckMenuItem menuItemStopPlay;
    @FXML
    private MenuItem menuItemLoad, menuItemSave;

    //label array of displaying score on the crossPane
    @FXML
    private Label[] label_OrangeBoardPoints, label_GreenBoardPoints, teamScoreColor, lbActionTokenNumber;

    /**
     * game and GUI
     */
    private Logic game;
    private JavaFxGUI gui;
    /**
     * loader of fxml, root for second window, new stage for second window
     */
    private FXMLLoader loader;
    private Parent root;
    private Stage stage2;
    private final FileChooser fileChooser = new FileChooser();

    /**
     * attributes
     */
    private final String[] playerName = new String[Logic.PLAYER_MAX];
    private boolean[] comBoolArray;
    private int playerAccount;
    private boolean[] activeArray;
    private int idxCurrentPlayer;
    private int[] usedActionList;
    /**
     * drag position, drop position, position for replace , shift clicked, target clicked
     */
    private final Position drag_position = new Position();
    private final Position drop_position = new Position();
    private final Position storeReplacePosOnHand = new Position();
    private final Position shiftedClickedPos = new Position();
    private final Position targetPosClicked = new Position();
    private boolean endGame;
    /**
     * region for highlight
     */
    private final Region crossBoardRegion = new Region();
    private final Region handRegion = new Region();
    private final Region shiftedRegion = new Region();
    private final Region targetRegion = new Region();

    /**
     * highlighting duration
     */
    private double durationTime;
    /**
     * counter for clicking
     */
    private int clickingCounter = 0;

    /**
     * This is where you need to add code that should happen during
     * initialization and then change the java doc comment.
     *
     * @param location  probably not used
     * @param resources probably not used
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        menuItemSave.setDisable(true);
        menuItemLoad.setDisable(true);
        comHandCb.setSelected(false);
        lblCurrentPlayerName.setVisible(false);
        grdPnChess.setDisable(true);
        grdPnPly1.setDisable(true);
        grdPnPly2.setDisable(true);
        grdPnPly3.setDisable(true);
        grdPnPly4.setDisable(true);
        comHandCb.setDisable(true);
        menuItemStopPlay.setDisable(true);
        // initialise the root and loader of second window
        try {
            loader = new FXMLLoader(getClass().getResource("UserInterfacePlayers.fxml"));
            root = loader.load();
            stage2 = new Stage();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //stuff should probably happen here
        label_OrangeBoardPoints = new Label[]{lblVer1, lblVer2, lblVer3, lblVer4, lblVer5, lblVer6};
        label_GreenBoardPoints = new Label[]{lblHorz1, lblHorz2, lblHorz3, lblHorz4, lblHorz5, lblHorz6};

        // put team score into tow separate array.
        teamScoreColor = new Label[]{OrangeLabelScore, GreenLabelScore};

        // label array of action tokens
        lbActionTokenNumber = new Label[]{lbNumRemoveToken, lbNumShifterToken, lbNumExchangeToken, lbNumReplaceToken};

        for (int i = 0; i < label_GreenBoardPoints.length; i++) {
            label_OrangeBoardPoints[i].setVisible(false);
            label_GreenBoardPoints[i].setVisible(false);
        }

        if (playerAccount == Logic.PLAYER_MIN) {
            activeArray[0] = true;
            activeArray[1] = true;
            activeArray[2] = false;
            activeArray[3] = false;
        } else if (playerAccount == Logic.PLAYER_MAX) {
            Arrays.fill(activeArray, true);
        }

        gridLinesSetting(grdPnChess);
        gridLinesSetting(grdPnPly1);
        gridLinesSetting(grdPnPly2);
        gridLinesSetting(grdPnPly3);
        gridLinesSetting(grdPnPly4);
        locatedFile();

        startNewGame();
    }

    /**
     * locate a file
     */
    private void locatedFile() {
        File currDir = null;
        try {
            currDir = new File(UserInterfaceController.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }

        if (currDir != null) {
            //ensure the dialog opens in the correct directory
            fileChooser.setInitialDirectory(currDir.getParentFile());
        }
    }

    /**
     * using css file to set up grdPane visible lines
     *
     * @param grdPane grid pane
     */
    private void gridLinesSetting(GridPane grdPane) {
        grdPane.getStyleClass().add("style-grid");
        int firstIndex = 0;
        for (int i = 0; i < grdPane.getColumnCount(); i++) {
            for (int j = 0; j < grdPane.getRowCount(); j++) {
                Pane pane = new Pane();
                pane.getStyleClass().add("style-grid-cell");
                if (i == firstIndex) {
                    pane.getStyleClass().add("first-column");
                }
                if (j == firstIndex) {
                    pane.getStyleClass().add("first-row");
                }
                grdPane.add(pane, i, j);
            }
        }
    }

    /**
     * set gridPane to disable.
     *
     * @param idxCurrentPlayer index of current player
     */
    private void gridPaneSetDisable(int idxCurrentPlayer) {
        switch (idxCurrentPlayer) {
            case 0 -> grdPnPly1.setDisable(false);
            case 1 -> grdPnPly2.setDisable(false);
            case 2 -> grdPnPly3.setDisable(false);
            case 3 -> grdPnPly4.setDisable(false);
        }
    }

    /**
     * a method to add ImageView in the required Grid pane with the specific row amount and column amount
     *
     * @param pane     grid pane
     * @param colCount col
     * @param rowCount row
     * @return ImageView array
     */
    private ImageView[][] initialImage(GridPane pane, int colCount, int rowCount) {
        ImageView[][] imageViews = new ImageView[colCount][rowCount];

        int nodeWidth = (int) pane.getWidth() / colCount;
        int nodeHeight = (int) pane.getHeight() / rowCount;

        for (int col = 0; col < colCount; col++) {
            for (int row = 0; row < rowCount; row++) {
                //create an empty imageview
                imageViews[col][row] = new ImageView();
                imageViews[col][row].setFitHeight(nodeHeight);
                imageViews[col][row].setFitWidth(nodeWidth);
                imageViews[col][row].setPreserveRatio(false);
                imageViews[col][row].setSmooth(true);
                GridPane.setConstraints(imageViews[col][row], col, row);

                pane.add(imageViews[col][row], col, row);
                imageViews[col][row].fitWidthProperty().bind(pane.widthProperty().divide(colCount));
                imageViews[col][row].fitHeightProperty().bind(pane.heightProperty().divide(rowCount));
            }
        }
        return imageViews;
    }

    /**
     * display four different action tokens on the game board
     */
    private void displayActionTokens() {

        Image shifter = new Image("gui/img/move.png");
        Image remove = new Image("gui/img/remove.png");
        Image swapOnBoard = new Image("gui/img/swapOnBoard.png");
        Image swapWithHand = new Image("gui/img/swapWithHand.png");

        actionTokenImage1.setImage(remove);
        actionTokenImage2.setImage(shifter);
        actionTokenImage3.setImage(swapOnBoard);
        actionTokenImage4.setImage(swapWithHand);
    }

    /**
     * start a new game
     */
    private void startNewGame() {
        endGame = false;
        clickingCounter = 0;
        currentPointsCb.setSelected(false);
        GreenLabelScore.setVisible(false);
        OrangeLabelScore.setVisible(false);
        rowColCb.setSelected(false);
        comHandCb.setSelected(false);
        menuItemStopPlay.setSelected(false);
        idShortDuration.setSelected(false);
        idMediumDuration.setSelected(false);
        idHighDuration.setSelected(false);

        displayActionTokens();

        //set up all GridPane of every imageView
        ImageView[][] crossBoard = initialImage(grdPnChess, grdPnChess.getColumnCount() - 1, grdPnChess.getRowCount() - 1);

        ImageView[][] player1 = initialImage(grdPnPly1, grdPnPly1.getColumnCount(), grdPnPly1.getRowCount());
        ImageView[][] player2 = initialImage(grdPnPly2, grdPnPly2.getColumnCount(), grdPnPly2.getRowCount());
        ImageView[][] player3 = initialImage(grdPnPly3, grdPnPly3.getColumnCount(), grdPnPly3.getRowCount());
        ImageView[][] player4 = initialImage(grdPnPly4, grdPnPly4.getColumnCount(), grdPnPly4.getRowCount());
        gui = new JavaFxGUI(comHandCb,grdPnChess, grdPnPly1, grdPnPly2, grdPnPly3, grdPnPly4, crossBoard, player1, player2, player3, player4, lblCurrentPlayerName, teamScoreColor, label_OrangeBoardPoints, label_GreenBoardPoints, lbActionTokenNumber, handRegion, shiftedRegion, targetRegion, crossBoardRegion);

        game = new Logic(crossBoard.length, crossBoard.length, gui, playerAccount, playerName, activeArray, comBoolArray, usedActionList, idxCurrentPlayer, durationTime, drag_position, storeReplacePosOnHand);

    }

    /**
     * a menu item for new
     */
    @FXML
    void menuItNew() {
        menuItemLoad.setDisable(false);
        menuItemSave.setDisable(false);
        endGame = false;
        grdPnChess.setDisable(false);
        grdPnPly1.setDisable(false);
        grdPnPly2.setDisable(false);
        grdPnPly3.setDisable(false);
        grdPnPly4.setDisable(false);
        lblCurrentPlayerName.setVisible(true);
        gui.setCurrentPlayer("", "");
        comHandCb.setDisable(false);
        menuItemStopPlay.setDisable(false);
        try {
            Scene scene2 = new Scene(root);
            stage2.setScene(scene2);
            stage2.setTitle("player");
            stage2.showAndWait();
        } catch (Exception e) {
            stage2.showAndWait();
        }
        Initial_playerSetting();
        startNewGame();

        game.logStartANewGame();
        game.logInitializePlayers();

        if (!checkAllPlayerAI()) {
            menuItemStopPlay.setDisable(true);
        }

        if (comBoolArray[idxCurrentPlayer]) {
            game.AI_playersTurn(game.getBoard(), idxCurrentPlayer);
        }

    }


    /**
     * Initialise all text fields for each player.
     */
    private void Initial_playerSetting() {
        UserInterfacePlayers userInterfacePlayers = loader.getController();
        idxCurrentPlayer = 0;
        usedActionList = new int[Logic.TOKEN_AMOUNT];
        Arrays.fill(usedActionList, 0);
        playerName[0] = userInterfacePlayers.getTextFieldPlay1().getText();
        playerName[1] = userInterfacePlayers.getTextFieldPlay2().getText();
        playerName[2] = userInterfacePlayers.getTextFieldPlay3().getText();
        playerName[3] = userInterfacePlayers.getTextFieldPlay4().getText();

        lbP1Nm.setText(playerName[0]);
        lbP2Nm.setText(playerName[1]);
        lbP3Nm.setText(playerName[2]);
        lbP4Nm.setText(playerName[3]);

        comBoolArray = userInterfacePlayers.getComBoolArray();
        playerAccount = userInterfacePlayers.getIntNumberPlayers();
        activeArray = userInterfacePlayers.getActiveArray();

        if (playerAccount == Logic.PLAYER_MIN) {
            lbP3Nm.setDisable(true);
            lbP4Nm.setDisable(true);
            lbP3Nm.setVisible(false);
            lbP4Nm.setVisible(false);
            grdPnPly3.setDisable(true);
            grdPnPly4.setDisable(true);
            grdPnPly3.setVisible(false);
            grdPnPly4.setVisible(false);
        } else if (playerAccount == Logic.PLAYER_MAX) {
            lbP3Nm.setDisable(false);
            lbP4Nm.setDisable(false);
            lbP3Nm.setVisible(true);
            lbP4Nm.setVisible(true);
            grdPnPly3.setDisable(false);
            grdPnPly4.setDisable(false);
            grdPnPly3.setVisible(true);
            grdPnPly4.setVisible(true);
        }
    }

    /**
     * checking if all players are AI
     *
     * @return true, all AI
     * false, not all AI
     */
    private boolean checkAllPlayerAI() {
        boolean allPlayerAI = false;
        int countingHowManyAI = 0;
        for (boolean b : comBoolArray) {
            if (b) {
                countingHowManyAI++;
            }
        }
        if (playerAccount == Logic.PLAYER_MIN) {
            if (countingHowManyAI == Logic.PLAYER_MIN) {
                allPlayerAI = true;
            }
        } else if (playerAccount == Logic.PLAYER_MAX) {
            if (countingHowManyAI == Logic.PLAYER_MAX) {
                allPlayerAI = true;
            }
        }

        return allPlayerAI;
    }

    /**
     * click close menu item and close the game.
     */
    @FXML
    void onClickMnItmClose() {
        Stage stage = (Stage) grdPnChess.getScene().getWindow();
        stage.close();
    }

    /**
     * initialise interface of game including label of player name, label of points, label of disable property, visible property
     */
    private void load_initialise() {
        endGame = false;
        game.settingCurrentPlayerGui(game.getPlayers());   // current player
        game.showPlayerHandGui(game.getPlayers());         // players
        game.showBoardToken(game.getBoard());         //board tokens

        lbP1Nm.setText(game.getPlayers()[0].getName());
        lbP2Nm.setText(game.getPlayers()[1].getName());
        lbP3Nm.setText(game.getPlayers()[2].getName());
        lbP4Nm.setText(game.getPlayers()[3].getName());

        game.updateDeck();
        game.updatePoint();

        game.updateRemainActionTokenDisplay();

        if (game.getPlayers()[0].getIsActive() && game.getPlayers()[1].getIsActive() && game.getPlayers()[2].getIsActive() && game.getPlayers()[3].getIsActive()) {
            // four players
            lbP3Nm.setDisable(false);
            lbP4Nm.setDisable(false);
            lbP3Nm.setVisible(true);
            lbP4Nm.setVisible(true);
            grdPnPly3.setDisable(false);
            grdPnPly4.setDisable(false);
            grdPnPly3.setVisible(true);
            grdPnPly4.setVisible(true);
            game.setPlayerAccount(Logic.PLAYER_MAX);
        } else if (game.getPlayers()[0].getIsActive() && game.getPlayers()[1].getIsActive() && !game.getPlayers()[2].getIsActive() && !game.getPlayers()[3].getIsActive()) {
            // two players
            lbP3Nm.setDisable(true);
            lbP4Nm.setDisable(true);
            lbP3Nm.setVisible(false);
            lbP4Nm.setVisible(false);
            grdPnPly3.setDisable(true);
            grdPnPly4.setDisable(true);
            grdPnPly3.setVisible(false);
            grdPnPly4.setVisible(false);
            game.setPlayerAccount(Logic.PLAYER_MIN);
        } else {
            gui.alertPlayerAmount();
        }
    }

    /**
     * load the existing file by clicking the load item
     */
    @FXML
    void onClickMnItmLoad() {
        idxCurrentPlayer = 0;
        game.setIndexCurrentPlayer(0);
        fileChooser.setTitle("Load");

        try {
            File file = fileChooser.showOpenDialog(BoardPaneGame.getScene().getWindow());
            fileChooser.setInitialDirectory(file.getParentFile());

            game.jsonToJavaUsingGson(file);   // load file

            load_initialise();

            idxCurrentPlayer = game.getIndexCurrentPlayer();
            gui.hiddenDeck(idxCurrentPlayer);

            game.logInitializePlayers();

            if (game.getPlayers()[idxCurrentPlayer].getIsAI()) {
                game.AI_playersTurn(game.getBoard(), idxCurrentPlayer);
            }

        } catch (Exception e) {
            e.printStackTrace();
            gui.displaySaveAndLoadError(e);
        }

    }

    /**
     * save the game and store unfinished game into a file
     */
    @FXML
    void onClickMnItmSave() {

        fileChooser.setTitle("Save");
        fileChooser.setInitialFileName("save first");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("json file(*.json)", "*.json"));

        try {
            File file = fileChooser.showSaveDialog(BoardPaneGame.getScene().getWindow());
            fileChooser.setInitialDirectory(file.getParentFile());

            game.javaToJSONUsingGson(file);   // save file

        } catch (Exception e) {
            e.printStackTrace();
            gui.displaySaveAndLoadError(e);
        }

    }

    /**
     * a radio button to stop AI playing
     */
    @FXML
    void onRadioButtonStopAIPlaying() {
        ANIMATION_DURATION = 0;
        gui.stopAIRunning();
    }

    /**
     * a checkbox to decide whether show computer hands or not.
     */
    @FXML
    void onCheckboxCPHands() {

        if (!comHandCb.isSelected()) {
            if (comBoolArray[0]) grdPnPly1.setVisible(false);
            if (comBoolArray[1]) grdPnPly2.setVisible(false);
            if (comBoolArray[2]) grdPnPly3.setVisible(false);
            if (comBoolArray[3]) grdPnPly4.setVisible(false);

        } else {
            if (comBoolArray[0]) grdPnPly1.setVisible(true);
            if (comBoolArray[1]) grdPnPly2.setVisible(true);
            if (comBoolArray[2]) grdPnPly3.setVisible(true);
            if (comBoolArray[3]) grdPnPly4.setVisible(true);
        }
    }


    /**
     * a checkbox to decide whether show the current point or not
     * If the checkbox is selected, the both label of the score points will show up and checkbox value is set as true.
     * If the checkbox is not selected, the both label of the score points will disappear and checkbox value is set as false.
     */
    @FXML
    void onCheckboxCurrentPoint() {
        if (currentPointsCb.isSelected()) {
            GreenLabelScore.setVisible(true);
            OrangeLabelScore.setVisible(true);
            currentPointsCb.setSelected(true);
        } else {
            GreenLabelScore.setVisible(false);
            OrangeLabelScore.setVisible(false);
            currentPointsCb.setSelected(false);
        }
    }

    /**
     * a checkbox to decide whether show each row or column point on the chessboard or not
     */
    @FXML
    void onCheckboxRowCol() {
        if (rowColCb.isSelected()) {
            for (int i = 0; i < label_GreenBoardPoints.length; i++) {
                label_OrangeBoardPoints[i].setVisible(true);
                label_GreenBoardPoints[i].setVisible(true);
            }
            lblCorner.setVisible(true);
            rowColCb.setSelected(true);
        } else {
            for (int i = 0; i < label_GreenBoardPoints.length; i++) {
                label_OrangeBoardPoints[i].setVisible(false);
                label_GreenBoardPoints[i].setVisible(false);
            }
            lblCorner.setVisible(false);
            rowColCb.setSelected(false);
        }
    }

    /**
     * long duration of the highlighting the token on the game board
     */
    @FXML
    void menuItDurationLong() {

        if (idHighDuration.isSelected()) {
            idHighDuration.setSelected(true);
            idShortDuration.setSelected(false);
            idMediumDuration.setSelected(false);
        } else {
            idHighDuration.setSelected(true);
        }
        durationTime = HIGHLIGHTING_LONG_DURATION;
    }

    /**
     * medium duration of the highlighting the token on the game board
     */
    @FXML
    void menuItDurationMedium() {

        if (idMediumDuration.isSelected()) {
            idHighDuration.setSelected(false);
            idMediumDuration.setSelected(true);
            idShortDuration.setSelected(false);
        } else {
            idMediumDuration.setSelected(true);
        }
        durationTime = HIGHLIGHTING_MEDIUM_DURATION;
    }

    /**
     * short duration of the highlighting the token on the game board
     */
    @FXML
    void menuItDurationShort() {
        if (idShortDuration.isSelected()) {
            idHighDuration.setSelected(false);
            idMediumDuration.setSelected(false);
        }
        idShortDuration.setSelected(true);

        durationTime = HIGHLIGHTING_LOW_DURATION;
    }

    /**
     * a method when player 1 clicking on his hands to choose which card should use.
     * highlight the chosen token.
     * a highlighted token  method will be implemented.
     * <p>
     * handle the action token here.
     *
     * @param event mouse event
     */
    @FXML
    void onMouseClickHandP1(MouseEvent event) {
        idxCurrentPlayer = game.getIndexCurrentPlayer();

        if (game.getPlayers()[idxCurrentPlayer].getHand().containAllActionToken() && game.getBoard().isBoardEmpty()) {
            gui.alertNoAvailableTokenForPlayer();
        }

        if (!endGame) {
            if (idxCurrentPlayer == 0 && !game.getBoard().isBoardEmpty()) {
                gui.removeRegionOnBoard();
                gui.removeShiftedRegionOnHand();
                grdPnPly1.setDisable(false);
                int col = -1;
                int row = -1;
                boolean leftClicked = event.getButton() == MouseButton.PRIMARY;
                //determine the imageview of the grid that contains the coordinates of the mouseclick
                //to determine the board-coordinates
                for (Node node : grdPnPly1.getChildren()) {
                    if (node instanceof ImageView) {
                        if (node.getBoundsInParent().contains(event.getX(), event.getY())) {
                            col = GridPane.getColumnIndex(node);
                            row = GridPane.getRowIndex(node);
                        }
                    }
                }

                assert (col >= 0 && row >= 0) : "The given coordinate cannot be clicked";

                // record the click coordinate before get in condition to check if it is symbol token
                drag_position.setX(row);
                drag_position.setY(col);
                if (leftClicked) {
                    if (clickingCounter == 0 && !game.isSymbolToken(drag_position, idxCurrentPlayer)) {
                        if (game.isWhichActionToken(drag_position, idxCurrentPlayer).equals(Token.EXCHANGE) && game.hasNotMoreThanTwoTokens(game.getBoard())) {
                            gui.alertExchangeToken();
                        } else {
                            gui.removeHandRegionOnHand();
                            gui.removeShiftedRegionOnHand();
                            gui.addHighlightedOnHand(idxCurrentPlayer, drag_position);
                            grdPnPly1.setDisable(true);

                            if (game.isWhichActionToken(drag_position, idxCurrentPlayer).equals(Token.REPLACER)) {
                                clickingCounter++;
                                storeReplacePosOnHand.setX(drag_position.getX());
                                storeReplacePosOnHand.setY(drag_position.getY());
                                grdPnPly1.setDisable(false);
                                grdPnChess.setDisable(true);
                            }
                        }

                    } else if (clickingCounter == 1 && game.isSymbolToken(drag_position, idxCurrentPlayer)) {
                        shiftedClickedPos.setX(drag_position.getX());
                        shiftedClickedPos.setY(drag_position.getY());
                        gui.highlightedShiftedPosForReplace(idxCurrentPlayer, shiftedClickedPos, false, durationTime);
                        grdPnChess.setDisable(false);
                        clickingCounter++;
                    }
                }
            }
        }
    }

    /**
     * a method when player 2 clicking on his hands to choose which card should use.
     * highlight the chosen token.
     * a highlighted token  method will be implemented.
     *
     * @param event mouse event
     */
    @FXML
    void onMouseClickHandP2(MouseEvent event) {
        idxCurrentPlayer = game.getIndexCurrentPlayer();

        if (game.getPlayers()[idxCurrentPlayer].getHand().containAllActionToken() && game.getBoard().isBoardEmpty()) {
            gui.alertNoAvailableTokenForPlayer();
        }

        if (!endGame) {
            if (idxCurrentPlayer == 1 && !game.getBoard().isBoardEmpty()) {
                gui.removeRegionOnBoard();
                gui.removeShiftedRegionOnHand();
                grdPnPly2.setDisable(false);
                int col = -1;
                int row = -1;
                boolean leftClicked = event.getButton() == MouseButton.PRIMARY;
                //determine the imageview of the grid that contains the coordinates of the mouseclick
                //to determine the board-coordinates
                for (Node node : grdPnPly2.getChildren()) {
                    if (node instanceof ImageView) {
                        if (node.getBoundsInParent().contains(event.getX(), event.getY())) {
                            col = GridPane.getColumnIndex(node);
                            row = GridPane.getRowIndex(node);
                        }

                    }
                }

                assert (col >= 0 && row >= 0) : "The given coordinate cannot be clicked";

                // record the click coordinate before get in condition to check if it is symbol token
                drag_position.setX(row);
                drag_position.setY(col);
                if (leftClicked) {
                    if (clickingCounter == 0 && !game.isSymbolToken(drag_position, idxCurrentPlayer)) {
                        if (game.isWhichActionToken(drag_position, idxCurrentPlayer).equals(Token.EXCHANGE) && game.hasNotMoreThanTwoTokens(game.getBoard())) {
                            gui.alertExchangeToken();
                        } else {
                            gui.removeHandRegionOnHand();
                            gui.removeShiftedRegionOnHand();
                            gui.addHighlightedOnHand(idxCurrentPlayer, drag_position);
                            grdPnPly2.setDisable(true);

                            if (game.isWhichActionToken(drag_position, idxCurrentPlayer).equals(Token.REPLACER)) {
                                clickingCounter++;
                                storeReplacePosOnHand.setX(drag_position.getX());
                                storeReplacePosOnHand.setY(drag_position.getY());
                                grdPnPly2.setDisable(false);
                                grdPnChess.setDisable(true);
                            }
                        }

                    } else if (clickingCounter == 1 && game.isSymbolToken(drag_position, idxCurrentPlayer)) {
                        shiftedClickedPos.setX(drag_position.getX());
                        shiftedClickedPos.setY(drag_position.getY());
                        gui.highlightedShiftedPosForReplace(idxCurrentPlayer, shiftedClickedPos, false, durationTime);
                        grdPnChess.setDisable(false);
                        clickingCounter++;
                    }

                }
            }
        }
    }

    /**
     * a method when player 3 clicking on his hands to choose which card should use.
     * a  highlight the chosen token.
     * a highlighted token  method will be implemented.
     *
     * @param event mouse event
     */
    @FXML
    void onMouseClickHandP3(MouseEvent event) {
        idxCurrentPlayer = game.getIndexCurrentPlayer();

        if (game.getPlayers()[idxCurrentPlayer].getHand().containAllActionToken() && game.getBoard().isBoardEmpty()) {
            gui.alertNoAvailableTokenForPlayer();
        }

        if (!endGame) {
            if (idxCurrentPlayer == 2 && !game.getBoard().isBoardEmpty()) {
                gui.removeRegionOnBoard();
                gui.removeShiftedRegionOnHand();
                grdPnPly3.setDisable(false);

                int col = -1;
                int row = -1;
                boolean leftClicked = event.getButton() == MouseButton.PRIMARY;
                //determine the imageview of the grid that contains the coordinates of the mouseclick
                //to determine the board-coordinates
                for (Node node : grdPnPly3.getChildren()) {
                    if (node instanceof ImageView) {
                        if (node.getBoundsInParent().contains(event.getX(), event.getY())) {
                            col = GridPane.getColumnIndex(node);
                            row = GridPane.getRowIndex(node);
                        }
                    }
                }

                assert (col >= 0 && row >= 0) : "The given coordinate cannot be clicked";

                // record the click coordinate before get in condition to check if it is symbol token
                drag_position.setX(row);
                drag_position.setY(col);

                if (leftClicked) {
                    if (clickingCounter == 0 && !game.isSymbolToken(drag_position, idxCurrentPlayer)) {
                        if (game.isWhichActionToken(drag_position, idxCurrentPlayer).equals(Token.EXCHANGE) && game.hasNotMoreThanTwoTokens(game.getBoard())) {
                            gui.alertExchangeToken();
                        } else {
                            gui.removeHandRegionOnHand();
                            gui.removeShiftedRegionOnHand();
                            gui.addHighlightedOnHand(idxCurrentPlayer, drag_position);
                            grdPnPly3.setDisable(true);

                            if (game.isWhichActionToken(drag_position, idxCurrentPlayer).equals(Token.REPLACER)) {
                                clickingCounter++;
                                storeReplacePosOnHand.setX(drag_position.getX());
                                storeReplacePosOnHand.setY(drag_position.getY());
                                grdPnPly3.setDisable(false);
                                grdPnChess.setDisable(true);
                            }

                        }

                    } else if (clickingCounter == 1 && game.isSymbolToken(drag_position, idxCurrentPlayer)) {

                        shiftedClickedPos.setX(drag_position.getX());
                        shiftedClickedPos.setY(drag_position.getY());
                        gui.highlightedShiftedPosForReplace(idxCurrentPlayer, shiftedClickedPos, false, durationTime);
                        grdPnChess.setDisable(false);
                        clickingCounter++;

                    }
                }

            }
        }

    }

    /**
     * a method when player 4 clicking on his hands to choose which card should use.
     * highlight the chosen token.
     * a highlighted token  method will be implemented.
     *
     * @param event mouse event
     */
    @FXML
    void onMouseClickHandP4(MouseEvent event) {
        idxCurrentPlayer = game.getIndexCurrentPlayer();

        if (game.getPlayers()[idxCurrentPlayer].getHand().containAllActionToken() && game.getBoard().isBoardEmpty()) {
            gui.alertNoAvailableTokenForPlayer();
        }

        if (!endGame) {
            if (idxCurrentPlayer == 3 && !game.getBoard().isBoardEmpty()) {
                gui.removeRegionOnBoard();
                gui.removeShiftedRegionOnHand();
                grdPnPly4.setDisable(false);

                int col = -1;
                int row = -1;
                boolean leftClicked = event.getButton() == MouseButton.PRIMARY;
                //determine the imageview of the grid that contains the coordinates of the mouseclick
                //to determine the board-coordinates
                for (Node node : grdPnPly4.getChildren()) {
                    if (node instanceof ImageView) {
                        if (node.getBoundsInParent().contains(event.getX(), event.getY())) {
                            col = GridPane.getColumnIndex(node);
                            row = GridPane.getRowIndex(node);
                        }

                    }
                }

                assert (col >= 0 && row >= 0) : "The given coordinate cannot be clicked";

                // record the click coordinate before get in condition to check if it is symbol token
                drag_position.setX(row);
                drag_position.setY(col);
                if (leftClicked) {
                    if (clickingCounter == 0 && !game.isSymbolToken(drag_position, idxCurrentPlayer)) {
                        if (game.isWhichActionToken(drag_position, idxCurrentPlayer).equals(Token.EXCHANGE) && game.hasNotMoreThanTwoTokens(game.getBoard())) {
                            gui.alertExchangeToken();
                        } else {
                            gui.removeHandRegionOnHand();
                            gui.removeShiftedRegionOnHand();
                            gui.addHighlightedOnHand(idxCurrentPlayer, drag_position);
                            grdPnPly4.setDisable(true);

                            if (game.isWhichActionToken(drag_position, idxCurrentPlayer).equals(Token.REPLACER)) {
                                clickingCounter++;
                                storeReplacePosOnHand.setX(drag_position.getX());
                                storeReplacePosOnHand.setY(drag_position.getY());
                                grdPnPly4.setDisable(false);
                                grdPnChess.setDisable(true);
                            }
                        }

                    } else if (clickingCounter == 1 && game.isSymbolToken(drag_position, idxCurrentPlayer)) {

                        shiftedClickedPos.setX(drag_position.getX());
                        shiftedClickedPos.setY(drag_position.getY());
                        gui.highlightedShiftedPosForReplace(idxCurrentPlayer, shiftedClickedPos, false, durationTime);
                        grdPnChess.setDisable(false);
                        clickingCounter++;

                    }
                }

            }
        }

    }

    /**
     * when action token is selected and the symbol token on the board need to be determined by clicking, the process is implemented in this method.
     *
     * @param event mouse event
     */
    @FXML
    private void handleMouseClickCrossPane(MouseEvent event) {

        if (!endGame) {
            int col = -1;
            int row = -1;
            //determine the imageview of the grid that contains the coordinates of the mouseclick
            //to determine the board-coordinates
            boolean leftClicked = event.getButton() == MouseButton.PRIMARY;
            for (Node node : grdPnChess.getChildren()) {
                if (node instanceof ImageView) {
                    if (node.getBoundsInParent().contains(event.getX(), event.getY())) {
                        col = GridPane.getColumnIndex(node);
                        row = GridPane.getRowIndex(node);
                    }
                }
            }
            assert (col >= 0 && row >= 0) : "The given coordinate cannot be clicked";

            if (leftClicked) {
                if (game.isWhichActionToken(drag_position, idxCurrentPlayer).equals(Token.REMOVE)) {
                    if (clickingCounter == 0 && game.isOccupied(row, col)) {
                        shiftedClickedPos.setX(row);
                        shiftedClickedPos.setY(col);
                        gui.highlightedShiftedPosOnBoard(shiftedClickedPos, durationTime);
                        game.usedActionToken_Player(drag_position, idxCurrentPlayer, shiftedClickedPos, targetPosClicked);
                        gridPaneSetDisable(idxCurrentPlayer);
                        game.callback();
                        game.logRemoveAndReplace(idxCurrentPlayer, shiftedClickedPos, Token.REMOVE.getIndex());
                        clickingCounter = 0;
                    }
                } else if (game.isWhichActionToken(drag_position, idxCurrentPlayer).equals(Token.SHIFTER)) {
                    if (clickingCounter == 0 && game.isOccupied(row, col)) {
                        shiftedClickedPos.setX(row);
                        shiftedClickedPos.setY(col);
                        gui.highlightedShiftedPosOnBoard(shiftedClickedPos, durationTime);

                        clickingCounter++;

                    } else if (clickingCounter == 1 && !game.isOccupied(row, col)) {
                        targetPosClicked.setX(row);
                        targetPosClicked.setY(col);
                        gui.highlightedTargetPosOnBoard(targetPosClicked, durationTime);

                        game.usedActionToken_Player(drag_position, idxCurrentPlayer, shiftedClickedPos, targetPosClicked);
                        gridPaneSetDisable(idxCurrentPlayer);
                        game.callback();
                        game.logShifterAndExchange(idxCurrentPlayer, shiftedClickedPos, targetPosClicked, Token.SHIFTER.getIndex());
                        clickingCounter = 0;
                    }
                } else if (game.isWhichActionToken(drag_position, idxCurrentPlayer).equals(Token.EXCHANGE)) {
                    if (clickingCounter == 0 && game.isOccupied(row, col)) {
                        shiftedClickedPos.setX(row);
                        shiftedClickedPos.setY(col);
                        gui.highlightedShiftedPosOnBoard(shiftedClickedPos, durationTime);

                        clickingCounter++;

                    } else if (clickingCounter == 1 && game.isOccupied(row, col)) {
                        targetPosClicked.setX(row);
                        targetPosClicked.setY(col);

                        gui.highlightedTargetPosOnBoard(targetPosClicked, durationTime);

                        game.usedActionToken_Player(drag_position, idxCurrentPlayer, shiftedClickedPos, targetPosClicked);
                        gridPaneSetDisable(idxCurrentPlayer);
                        game.callback();
                        game.logShifterAndExchange(idxCurrentPlayer, shiftedClickedPos, targetPosClicked, Token.EXCHANGE.getIndex());

                        clickingCounter = 0;
                    }
                } else if (game.isWhichActionToken(storeReplacePosOnHand, idxCurrentPlayer).equals(Token.REPLACER)) {

                    if (clickingCounter == 2 && game.isOccupied(row, col)) {

                        targetPosClicked.setX(row);
                        targetPosClicked.setY(col);

                        gui.highlightedTargetPosOnBoard(targetPosClicked, durationTime);

                        game.usedActionToken_Player(storeReplacePosOnHand, idxCurrentPlayer, shiftedClickedPos, targetPosClicked);
                        gridPaneSetDisable(idxCurrentPlayer);

                        game.callback();
                        game.logRemoveAndReplace(idxCurrentPlayer, targetPosClicked, Token.REPLACER.getIndex());

                        clickingCounter = 0;
                    }
                }
            }

            game.updatePoint();
            game.updateRemainActionTokenDisplay();

            if (game.isFinished()) {
                endGame = true;
                game.logEnd(game.getWinnerTeam(), game.getFinalScore());
                gui.gameHasEnded(game.getWinnerTeam(), game.getFinalScore());
            }
            idxCurrentPlayer = game.getIndexCurrentPlayer();
        }
        event.consume();
    }

    /**
     * get the imageview by given grid pane and position
     *
     * @return the imageview
     */
    private ImageView getCoordinateImage(GridPane gridPane, Position position) {

        ImageView requiredImageView = new ImageView();

        for (Node node : gridPane.getChildren()) {
            // get the x and y on the pane
            int comp_col = GridPane.getColumnIndex(node);
            int comp_row = GridPane.getRowIndex(node);
            if (node instanceof ImageView && comp_row == position.getX() && comp_col == position.getY()) {   // compare x and y  from pane with the given x and y
                requiredImageView = ((ImageView) node);
            }
        }
        return requiredImageView;
    }

    /**
     * detect there is a drag action on player 1 hand
     *
     * @param event mouse event
     */
    @FXML
    void handleDragDetectP1(MouseEvent event) {
        idxCurrentPlayer = game.getIndexCurrentPlayer();

        if (game.getPlayers()[idxCurrentPlayer].getHand().containAllActionToken() && game.getBoard().isBoardEmpty()) {
            gui.alertNoAvailableTokenForPlayer();
        }

        if (idxCurrentPlayer == 0 && !endGame) {
            gui.removeHandRegionOnHand();
            gui.removeShiftedRegionOnHand();
            grdPnPly1.setDisable(false);

            int col = -1;
            int row = -1;
            //determine the imageview of the grid that contains the coordinates of the mouseclick
            //to determine the board-coordinates
            for (Node node : grdPnPly1.getChildren()) {
                if (node instanceof ImageView) {
                    if (node.getBoundsInParent().contains(event.getX(), event.getY())) {
                        col = GridPane.getColumnIndex(node);
                        row = GridPane.getRowIndex(node);
                    }
                }
            }

            assert (col >= 0 && row >= 0) : "The given coordinate cannot be clicked";

            // store drag position
            drag_position.setX(row);
            drag_position.setY(col);
            Dragboard db = grdPnPly1.startDragAndDrop(TransferMode.ANY);
            ClipboardContent cb = new ClipboardContent();

            if (game.isSymbolToken(drag_position, idxCurrentPlayer)) {
                Image image = getCoordinateImage(grdPnPly1, drag_position).getImage();
                cb.putImage(image);
            }

            db.setContent(cb);
            event.consume();
        }
    }

    /**
     * detect there is a drag action on player 2 hand
     *
     * @param event mouse event
     */
    @FXML
    void handleDragDetectP2(MouseEvent event) {
        idxCurrentPlayer = game.getIndexCurrentPlayer();

        if (game.getPlayers()[idxCurrentPlayer].getHand().containAllActionToken() && game.getBoard().isBoardEmpty()) {
            gui.alertNoAvailableTokenForPlayer();
        }

        if (idxCurrentPlayer == 1 && !endGame) {
            gui.removeHandRegionOnHand();
            gui.removeShiftedRegionOnHand();
            grdPnPly2.setDisable(false);

            int col = -1;
            int row = -1;
            //determine the imageview of the grid that contains the coordinates of the mouseclick
            //to determine the board-coordinates
            for (Node node : grdPnPly2.getChildren()) {
                if (node instanceof ImageView) {
                    if (node.getBoundsInParent().contains(event.getX(), event.getY())) {
                        col = GridPane.getColumnIndex(node);
                        row = GridPane.getRowIndex(node);
                    }
                }
            }

            assert (col >= 0 && row >= 0) : "The given coordinate cannot be clicked";
            // store click position
            // store drag position
            drag_position.setX(row);
            drag_position.setY(col);
            Dragboard db = grdPnPly2.startDragAndDrop(TransferMode.ANY);

            ClipboardContent cb = new ClipboardContent();
            if (game.isSymbolToken(drag_position, idxCurrentPlayer)) {
                Image image = getCoordinateImage(grdPnPly2, drag_position).getImage();
                cb.putImage(image);
            }

            db.setContent(cb);
            event.consume();
        }
    }

    /**
     * detect there is a drag action on player 3 hand
     *
     * @param event mouse event
     */
    @FXML
    void handleDragDetectP3(MouseEvent event) {
        idxCurrentPlayer = game.getIndexCurrentPlayer();

        if (game.getPlayers()[idxCurrentPlayer].getHand().containAllActionToken() && game.getBoard().isBoardEmpty()) {
            gui.alertNoAvailableTokenForPlayer();
        }

        if (idxCurrentPlayer == 2 && !endGame) {
            gui.removeHandRegionOnHand();
            gui.removeShiftedRegionOnHand();
            grdPnPly3.setDisable(false);

            int col = -1;
            int row = -1;
            //determine the imageview of the grid that contains the coordinates of the mouseclick
            //to determine the board-coordinates
            for (Node node : grdPnPly3.getChildren()) {
                if (node instanceof ImageView) {
                    if (node.getBoundsInParent().contains(event.getX(), event.getY())) {
                        col = GridPane.getColumnIndex(node);
                        row = GridPane.getRowIndex(node);
                    }
                }
            }

            assert (col >= 0 && row >= 0) : "The given coordinate cannot be clicked";

            // store drag position
            drag_position.setX(row);
            drag_position.setY(col);
            Dragboard db = grdPnPly3.startDragAndDrop(TransferMode.ANY);

            ClipboardContent cb = new ClipboardContent();
            if (game.isSymbolToken(drag_position, idxCurrentPlayer)) {
                Image image = getCoordinateImage(grdPnPly3, drag_position).getImage();
                cb.putImage(image);
            }
            db.setContent(cb);
            event.consume();
        }
    }

    /**
     * detect there is a drag action on player 4 hand
     *
     * @param event mouse event
     */
    @FXML
    void handleDragDetectP4(MouseEvent event) {
        idxCurrentPlayer = game.getIndexCurrentPlayer();

        if (game.getPlayers()[idxCurrentPlayer].getHand().containAllActionToken() && game.getBoard().isBoardEmpty()) {
            gui.alertNoAvailableTokenForPlayer();
        }

        if (idxCurrentPlayer == 3 && !endGame) {
            gui.removeHandRegionOnHand();
            gui.removeShiftedRegionOnHand();
            grdPnPly4.setDisable(false);

            int col = -1;
            int row = -1;
            //determine the imageview of the grid that contains the coordinates of the mouseclick
            //to determine the board-coordinates
            for (Node node : grdPnPly4.getChildren()) {
                if (node instanceof ImageView) {
                    if (node.getBoundsInParent().contains(event.getX(), event.getY())) {
                        col = GridPane.getColumnIndex(node);
                        row = GridPane.getRowIndex(node);
                    }
                }
            }
            assert (col >= 0 && row >= 0) : "The given coordinate cannot be clicked";

            // store click position
            // store drag position
            drag_position.setX(row);
            drag_position.setY(col);
            Dragboard db = grdPnPly4.startDragAndDrop(TransferMode.ANY);
            ClipboardContent cb = new ClipboardContent();

            if (game.isSymbolToken(drag_position, idxCurrentPlayer)) {
                Image image = getCoordinateImage(grdPnPly4, drag_position).getImage();
                cb.putImage(image);
            }

            db.setContent(cb);
            event.consume();
        }
    }

    /**
     * drag over method that accept drag board
     *
     * @param event drag event
     */
    @FXML
    void handleDragOverChessBoard(DragEvent event) {

        Dragboard dragboard = event.getDragboard();
        if (dragboard.hasImage()) {
            event.acceptTransferModes(TransferMode.ANY);
        }

    }

    /**
     * when drag drop on the chess board and get the drop position and update whole board and highlighted the cell.
     *
     * @param event drag event
     */
    @FXML
    void handleDragDropChessBoard(DragEvent event) {
        gui.removeRegionOnBoard();
        Dragboard db = event.getDragboard();
        boolean success = false;
        Node node = event.getPickResult().getIntersectedNode();
        int coord_col = GridPane.getColumnIndex(node);
        int coord_row = GridPane.getRowIndex(node);
        if (node != grdPnChess && db.hasImage() && !game.isOccupied(coord_row, coord_col)) {

            drop_position.setX(coord_row);
            drop_position.setY(coord_col);

            game.DragUsedForSymbolToken(idxCurrentPlayer, drag_position, drop_position);     //(1,3)

            gui.highlightedDragPosOnBoard(drop_position, durationTime);
            game.callback();
            game.logUsingSymbol(idxCurrentPlayer, drop_position, game.getBoard().getCell(drop_position));

            game.updatePoint();
            game.updateRemainActionTokenDisplay();

            success = true;
        }

        //let the source know whether the image was successfully transferred and used
        event.setDropCompleted(success);

        if (game.isFinished()) {
            endGame = true;
        }

        idxCurrentPlayer = game.getIndexCurrentPlayer();


        event.consume(); // The consume method consumes this event so that it will not be processed in the default manner by the source which originated it.

    }

    /**
     * when drag action has done, and the dragged component need to clear the image and add a new token(image).
     *
     * @param event drag event
     */
    @FXML
    void handleDragDoneP1(DragEvent event) {
        event.consume(); // The consume method consumes this event so that it will not be processed in the default manner by the source which originated it.
    }

    /**
     * when drag action has done, and the dragged component need to clear the image and add a new token(image).
     *
     * @param event drag event
     */
    @FXML
    void handleDragDoneP2(DragEvent event) {
        event.consume(); // The consume method consumes this event so that it will not be processed in the default manner by the source which originated it.
    }

    /**
     * when drag action has done, and the dragged component need to clear the image and add a new token(image).
     *
     * @param event drag event
     */
    @FXML
    void handleDragDoneP3(DragEvent event) {
        event.consume(); // The consume method consumes this event so that it will not be processed in the default manner by the source which originated it.
    }

    /***
     * when drag action has done, and the dragged component need to clear the image and add a new token(image).
     * @param event drag event
     */
    @FXML
    void handleDragDoneP4(DragEvent event) {
        event.consume();  // The consume method consumes this event so that it will not be processed in the default manner by the source which originated it.
    }


}