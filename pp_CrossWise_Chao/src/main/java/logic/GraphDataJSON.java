package logic;

import java.util.List;

/**
 * this class is for converting java object to gson format and also bind gson format with java objects.
 * here has several setter and getter for all necessary attributes.
 *
 * @author chien-hsun
 */
public class GraphDataJSON {
    /**
     * attributes
     */
    private List<GraphDataJSON.gsonPlayer> players;
    private int currPlayer;
    private int[][] field;
    private int[] usedActionTiles;

    /**
     * getter for list
     *
     * @return a list of gson player
     */
    public List<gsonPlayer> getPlayers() {
        return players;
    }

    /**
     * setter player list
     *
     * @param player a list player
     */
    public void setPlayers(List<gsonPlayer> player) {
        this.players = player;
    }

    /**
     * getter current player
     *
     * @return index of current player
     */
    public int getCurrPlayer() {
        return this.currPlayer;
    }

    /**
     * setter current player
     *
     * @param currPlayer given current player
     */
    public void setCurrPlayer(int currPlayer) {
        this.currPlayer = currPlayer;
    }

    /**
     * getter board field
     *
     * @return int two dimensional array
     */
    public int[][] getField() {
        return field;
    }

    /**
     * setter board field
     *
     * @param field a given field
     */
    public void setField(int[][] field) {
        this.field = field;
    }

    /**
     * getter usedAction token
     *
     * @return int array
     */
    public int[] getUsedActionTiles() {
        return usedActionTiles;
    }

    /**
     * setter a used Action token
     *
     * @param usedActionTiles the given int array
     */
    public void setUsedActionTiles(int[] usedActionTiles) {
        this.usedActionTiles = usedActionTiles;
    }

    /**
     * inner class for players, which has four attributes: name, isActive, isAI, hand
     */
    static class gsonPlayer {
        public String name;
        public boolean isActive;
        public boolean isAI;
        public int[] hand;

    }

}
