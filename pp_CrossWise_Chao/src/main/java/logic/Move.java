package logic;

/**
 * a move class contain a token and the position
 *
 * @author chien-hsun
 */
public class Move {

    /**
     * a token
     */
    private Token token;

    /**
     * a position
     */
    private Position position;

    /**
     * move constructor
     */
    public Move(){
    }

    /**
     * a move constructor contain a token and a position
     *
     * @param token    a token need to move
     * @param position a position will move to
     */
    public Move(Token token, Position position) {
        super();
        this.token = token;
        this.position = position;
    }

    /**
     * get a token
     *
     * @return token
     */
    public Token getToken() {
        return token;
    }

    /**
     * get the position
     *
     * @return position
     */
    public Position getPosition() {
        return position;
    }

}
