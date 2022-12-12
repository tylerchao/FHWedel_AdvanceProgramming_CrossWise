package logic;

/**
 * a hand contains several cards.
 * every player have a hand of cards
 * hand class as a child of the pack class.
 * It inherits the method and attribute from pack class.
 *
 * @author chien-hsun
 */
public class Hand extends Pack {

    /**
     * an empty hand constructor
     */
    public Hand() {
        super();
    }

    /**
     * testing constructor
     * a hand constructor contains several tokens which has stored as an array.
     *
     * @param tokens given token array
     */
    public Hand(Token[] tokens) {
        super(tokens);
    }


    /**
     * add a token in a arrayList
     *
     * @param token token
     */
    @Override
    public void addToken(Token token) {
        super.addToken(token);
    }

    /**
     * convert hand to string value
     *
     * @return string value
     */
    public String handToString() {
        StringBuilder s = new StringBuilder();
        String token;
        StringBuilder handString = new StringBuilder();

        for (int index = 0; index < this.handList.size(); index++) {
            if (this.handList.get(index) == null) {
                token = "null";
            } else {
                token = String.valueOf(this.handList.get(index).getIndex());
            }
            if (index != this.handList.size() - 1) {
                token = token + ", ";
            }

            handString.append(token);
        }
        s.append("[").append(handString).append("]");
        return s.toString();
    }

    /**
     * transform hand token into an array
     *
     * @return int array of a hand
     */
    public int[] handToIntArray() {
        int[] handToInt = new int[handList.size()];

        for (int i = 0; i < handList.size(); i++) {
            if (handList.get(i) == null) {
                handToInt[i] = -1;
            } else {
                handToInt[i] = handList.get(i).getIndex();
            }
        }
        return handToInt;
    }

    /**
     * hand token in an array
     * [2,3,4,5]
     *
     * @return string of a hand
     */
    @Override
    public String toString() {
        return this.handList.toString();
    }

}
