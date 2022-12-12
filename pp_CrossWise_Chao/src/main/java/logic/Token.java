package logic;

/**
 * This enum class contains every token.
 * Each token has it own index.
 * In order to either get a token from index or get an index from a token, I create two methods to fulfill this expectation.
 *
 *
 * @author chien-hsun
 */
public enum Token {
    NONE(0), SUN(1), CROSS(2), TRIANGLE(3), SQUARE(4), PENTAGON(5), STAR(6), REMOVE(7), SHIFTER(8), EXCHANGE(9), REPLACER(10);

    private final int index;

    Token(int index) {
        this.index = index;
    }

    /**
     * get each token index. may be used for json file
     *
     * @return index
     */
    public int getIndex() {

        return index;
    }

    /**
     * get a token by giving a index
     * @param index token index
     * @return a required token
     */
    public static Token getToken(int index){
        for(Token element: Token.values()){
            if(element.ordinal() == index){
                return element;
            }
        }
        return null;
    }

}

