package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextField;
import logic.Logic;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * this interface is for the second window, which is controlled player name and player situation, active and if it is AI player.
 * we will pass the information of this class to the main window (UserInterfaceController).
 *
 * @author chien-hsun
 */
public class UserInterfacePlayers implements Initializable {

    /**
     * an boolean array contains every boolean value of if they are AI players
     */
    boolean[] comBoolArray = new boolean[Logic.PLAYER_MAX];
    /**
     * an boolean array contains every boolean value of if they are AI players
     */
    boolean[] activeArray = new boolean[Logic.PLAYER_MAX];
    @FXML
    private RadioMenuItem RadioITemFourPlayer, RadioITemTwoPlayer;

    /**
     * a boolean value to show how many players are playing
     */
    private boolean isTwoPlayer;

    @FXML
    private CheckBox pl1ComCb, pl2ComCb, pl3ComCb, pl4ComCb;

    @FXML
    private TextField textFieldPlay1, textFieldPlay2, textFieldPlay3, textFieldPlay4;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        RadioITemTwoPlayer.setSelected(false);
        RadioITemFourPlayer.setSelected(true);
        if (isTwoPlayer) {
            textFieldPlay1.setText("Eleven");
            textFieldPlay2.setText("Will");
            textFieldPlay3.setDisable(true);
            textFieldPlay4.setDisable(true);
            textFieldPlay3.setEditable(false);
            textFieldPlay4.setEditable(false);
        }

        Arrays.fill(comBoolArray, Boolean.FALSE);
        Arrays.fill(activeArray, Boolean.TRUE);

        pl1ComCb.setSelected(comBoolArray[0]);
        pl2ComCb.setSelected(comBoolArray[1]);
        pl3ComCb.setSelected(comBoolArray[2]);
        pl4ComCb.setSelected(comBoolArray[3]);

    }

    /**
     * @return index of player
     */
    public TextField getTextFieldPlay1() {
        return textFieldPlay1;
    }

    /**
     * @return index of player
     */
    public TextField getTextFieldPlay2() {
        return textFieldPlay2;
    }

    /**
     * @return index of player
     */
    public TextField getTextFieldPlay3() {
        return textFieldPlay3;
    }

    /**
     * @return index of player
     */
    public TextField getTextFieldPlay4() {
        return textFieldPlay4;
    }

    /**
     * an array of computer boolean
     *
     * @return boolean array
     */
    public boolean[] getComBoolArray() {
        return comBoolArray;
    }

    /**
     * an array of computer boolean
     *
     * @return boolean array
     */
    public boolean[] getActiveArray() {
        return activeArray;
    }

    /**
     * get the boolean value of the number player.
     *
     * @return number of player
     */
    public int getIntNumberPlayers() {
        if (isTwoPlayer) {
            return Logic.PLAYER_MIN;
        } else {
            return Logic.PLAYER_MAX;
        }
    }

    /**
     * check box event method for player 1
     */
    @FXML
    void checkBoxComP1() {
        if (pl1ComCb.isSelected()) {
            textFieldPlay1.setText("Tyler");
            textFieldPlay1.setEditable(false);
            comBoolArray[0] = true;
        } else {
            comBoolArray[0] = false;
            textFieldPlay1.clear();
            textFieldPlay1.setText("Eleven");
            textFieldPlay1.setEditable(true);
        }
    }

    /**
     * check box event method for player 2
     */
    @FXML
    void checkBoxComP2() {
        if (pl2ComCb.isSelected()) {
            textFieldPlay2.setText("Nils");
            textFieldPlay2.setEditable(false);
            comBoolArray[1] = true;
        } else {
            comBoolArray[1] = false;
            textFieldPlay2.clear();
            textFieldPlay2.setText("Will");
            textFieldPlay2.setEditable(true);
        }
    }

    /**
     * check box event method for player 3
     */
    @FXML
    void checkBoxComP3() {
        if (pl3ComCb.isSelected()) {
            textFieldPlay3.setText("Markus");
            textFieldPlay3.setEditable(false);
            comBoolArray[2] = true;
        } else {
            comBoolArray[2] = false;
            textFieldPlay3.clear();
            textFieldPlay3.setText("Dustin");
            textFieldPlay3.setEditable(true);
        }
    }

    /**
     * check box event method for player 4
     */
    @FXML
    void checkBoxComP4() {
        if (pl4ComCb.isSelected()) {
            textFieldPlay4.setText("Hermann");
            textFieldPlay4.setEditable(false);
            comBoolArray[3] = true;
        } else {
            textFieldPlay4.clear();
            textFieldPlay4.setText("Cooper");
            textFieldPlay4.setEditable(true);
            comBoolArray[3] = false;
        }
    }

    /**
     * menu item for player 2
     */
    @FXML
    void onClickMnItm2Player() {
        textFieldPlay1.setText("Eleven");
        textFieldPlay2.setText("Will");
        textFieldPlay3.setText("None");
        textFieldPlay4.setText("None");
        textFieldPlay3.setDisable(true);
        textFieldPlay4.setDisable(true);
        pl3ComCb.setDisable(true);
        pl4ComCb.setDisable(true);
        isTwoPlayer = true;
        RadioITemFourPlayer.setSelected(false);
        comBoolArray[2] = false;
        comBoolArray[3] = false;
        activeArray[0] = true;
        activeArray[1] = true;
        activeArray[2] = false;
        activeArray[3] = false;

    }

    /**
     * menu item for player 4
     */
    @FXML
    void onClickMnItm4Player() {
        textFieldPlay1.setText("Eleven");
        textFieldPlay2.setText("Will");
        textFieldPlay3.setText("Dustin");
        textFieldPlay4.setText("Cooper");
        textFieldPlay3.setDisable(false);
        textFieldPlay4.setDisable(false);
        pl3ComCb.setDisable(false);
        pl4ComCb.setDisable(false);
        isTwoPlayer = false;
        RadioITemTwoPlayer.setSelected(false);
        activeArray[0] = true;
        activeArray[1] = true;
        activeArray[2] = true;
        activeArray[3] = true;
    }

    /**
     * an action method for apply button, which can send data
     */
    @FXML
    void sendData() {
        textFieldPlay1.getScene().getWindow().hide();
        textFieldPlay2.getScene().getWindow().hide();
        textFieldPlay3.getScene().getWindow().hide();
        textFieldPlay4.getScene().getWindow().hide();
    }
}
