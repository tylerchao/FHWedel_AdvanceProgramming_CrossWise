package logic;

/**
 * a Player class to create several players.
 * a player object contains several elements,
 * for instance, name, active situation, whether if it is played by AI, and a hand of token which it holds.
 *
 * @author chien-hsun
 */
public class Players {

    /**
     * a player string name
     */
    private String name;

    /**
     * is active player
     */
    private boolean isActive;

    /**
     * player is AI player
     */
    private boolean isAI;

    /**
     * an Instance from hand class. this can be used to call arrayList
     */
    private Hand hand;

    /**
     * the constructor to create all element which will need to start a game
     */
    public Players(String name, boolean isActive, boolean isAI, Pack deck, int cardNumber) {
        this.name = name;
        this.isActive = isActive;
        this.isAI = isAI;
        this.hand = new Hand();

        Token[] handToken = deck.getRandomCards(cardNumber);
        hand.addTokens(handToken);

    }

    /**
     * setter for name
     *
     * @param name input name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter for name
     *
     * @return a name
     */
    public String getName() {
        return name;
    }

    /**
     * getter for active situation
     *
     * @return a boolean value of active situation.
     */
    public boolean getIsActive() {
        return isActive;
    }

    /**
     * setter for active situation
     *
     * @param isActive active situation
     */
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * a getter of a boolean value for identifying whether the player is AI or not.
     *
     * @return AI
     */
    public boolean getIsAI() {
        return isAI;
    }

    /**
     * setter for setting AI player.
     *
     * @param isAI a boolean value for AI.
     */
    public void setIsAI(Boolean isAI) {
        this.isAI = isAI;
    }

    /**
     * getter a hand class
     *
     * @return a hand
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * setter of a hand
     *
     * @param hand hand class
     */
    public void setHand(Hand hand) {
        this.hand = hand;
    }

    /**
     * "players": [
     * {"name": "Player1", "isActive": true, "isAI": false, "hand": [1, 2, 3, 4] },
     * {"name": "Player2", "isActive": true, "isAI": false, "hand": [7, 10, 4, 4] },
     * {"name": "Player3", "isActive": true, "isAI": true, "hand": [7, 9, 8, 4] },
     * {"name": "Player4", "isActive": true, "isAI": true, "hand": [10, 8, 2, 4] }
     * ]
     * call card toString and
     * <p>
     *
     * @return representation of a player
     */
    @Override
    public String toString() {

        return "{" + "name" + name + ", isActive" + isActive + ", isAI" + isAI + ", hand" + hand.toString() + '}';
    }

}
