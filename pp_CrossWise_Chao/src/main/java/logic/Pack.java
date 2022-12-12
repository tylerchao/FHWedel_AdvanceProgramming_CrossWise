package logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * This pack contains several tokens.
 * This can create many packs, for example a pack for all tokens, only symbol tokens, action tokens
 * this class has different methods to be used to get, insert or remove a token by giving an index or in a sequence.
 *
 * @author chien-hsun
 */
public class Pack {

    /**
     * an arraylist based on token type, this store every token
     * only access to subclass hand
     */
    protected ArrayList<Token> handList;

    /**
     * random generator for dealing cards
     */
    private final Random randomGenerator = new Random();

    /**
     * a constructor for empty array
     */
    public Pack() {
        this.handList = new ArrayList<>();
    }

    /**
     * not Only for testing constructor, also need to use in logic
     * a pack contain existed tokens which has stored as a token array.
     */
    public Pack(Token[] tokens) {
        this.handList = new ArrayList<>(Arrays.asList(tokens));
    }

    /**
     * a getter method to get arrayList
     *
     * @return an arraylist of tokens
     */
    public ArrayList<Token> getTokenList() {
        return this.handList;
    }

    /**
     * how many tokens are in the pack now.
     */
    public int getNoOfTokensFromPack() {
        int empty = 0;
        if (!isEmpty()) {
            return this.handList.size();
        }
        return empty;

    }

    /**
     * add a token in a arrayList
     *
     * @param token a given token
     */
    public void addToken(Token token) {
        this.handList.add(token);
    }

    /**
     * add a given token in the specific position in a pack
     *
     * @param index an index of a place to be inserted
     * @param token a given token
     */
    public void addTokenAtGivenIndex(int index, Token token) {
        this.handList.add(index, token);
    }


    /**
     * add an array of tokens
     * this is used for first round of getting four tokens for each player
     *
     * @param tokens a given array of tokens
     */
    public void addTokens(Token[] tokens) {
        if (tokens != null) {
            for (Token token : tokens) {
                this.addToken(token);
            }
        }
    }

    /**
     * true, if the pack is empty
     *
     * @return true, if the pack is empty
     */
    public boolean isEmpty() {

        return handList.isEmpty();

    }

    /**
     * a pack contain 54 tokens
     * 42 symbol tokens and 12 action tokens
     */
    public static Pack createAPackOfEveryTokens() {
        Pack newPack = new Pack();
        Token[] enumArray = Token.values();

        for (int i = Token.SUN.ordinal(); i < Token.REMOVE.ordinal(); i++) {
            newPack.addToken(enumArray[i]);
            newPack.addToken(enumArray[i]);
            newPack.addToken(enumArray[i]);
            newPack.addToken(enumArray[i]);
            newPack.addToken(enumArray[i]);
            newPack.addToken(enumArray[i]);
            newPack.addToken(enumArray[i]);
        }

        for (int i = Token.REMOVE.ordinal(); i < enumArray.length; i++) {
            newPack.addToken(enumArray[i]);
            newPack.addToken(enumArray[i]);
            newPack.addToken(enumArray[i]);
        }

        return newPack;
    }

    /**
     * 6 X 7
     * a pack contain 42 tokens
     * 42 symbol tokens
     */
    public static Pack createAPackOfSymbolTokens() {
        Pack newPack = new Pack();
        Token[] enumArray = Token.values();

        for (int i = Token.SUN.ordinal(); i < Token.REMOVE.ordinal(); i++) {
            newPack.addToken(enumArray[i]);
            newPack.addToken(enumArray[i]);
            newPack.addToken(enumArray[i]);
            newPack.addToken(enumArray[i]);
            newPack.addToken(enumArray[i]);
            newPack.addToken(enumArray[i]);
            newPack.addToken(enumArray[i]);
        }

        return newPack;
    }

    /**
     * a pack contain 12 tokens
     * 12 action tokens
     */
    public static Pack createAPackOfActionTokens() {
        Pack newPack = new Pack();
        Token[] enumArray = Token.values();

        for (int i = Token.REMOVE.ordinal(); i < enumArray.length; i++) {
            newPack.addToken(enumArray[i]);
            newPack.addToken(enumArray[i]);
            newPack.addToken(enumArray[i]);
        }

        return newPack;
    }

    /**
     * checks, if index is valid for the pack or array
     *
     * @param index to check
     * @param size  of the pack or array to fit
     * @return true, if the index is valid
     */
    boolean isValidIndex(int index, int size) {
        int firstIndex = 0;
        return index < size && index >= firstIndex;
    }

    /**
     * get a token from pack by the given index
     *
     * @param index an index of a token
     * @return a token
     */
    public Token getATokenOnIndex(int index) {
        if (isEmpty()) {
            return null;
        } else if (isValidIndex(index, handList.size())) {
            return this.handList.get(index);
        }
        return null;
    }

    /**
     * get an index of the given token from a pack .
     * if the given token is not exist in the hand, then invalid index -1 will be return.
     *
     * @param token a given token
     * @return an index of the given token
     */
    public int getAtTokenIndexFromAHand(Token token) {
        int invalid_index = -1;
        if (this.containTheToken(token)) {
            for (int idx = 0; idx < this.getNoOfTokensFromPack(); idx++)
                if (this.getATokenOnIndex(idx) == null) {
                    return invalid_index;
                } else {
                    if (this.getATokenOnIndex(idx).equals(token)) {
                        return idx;
                    }
                }
        }
        return invalid_index;

    }


    /**
     * gets the card and removes it from the deck
     *
     * @param index the index of the card to pick
     * @return the card at the index, {@code null} if the index is invalid
     */
    private Token pickToken(int index) {
        if (isValidIndex(index, this.getNoOfTokensFromPack())) {
            Token newToken = this.getATokenOnIndex(index);
            this.removeToken(newToken);
            return newToken;
        }
        return null;
    }

    /**
     * get a token randomly from the full pack and remove it
     *
     * @return a random token of this pack; {@code null} if the pack is empty
     */
    public Token getARandomToken() {
        if (!isEmpty()) {
            return this.pickToken(randomGenerator.nextInt(getNoOfTokensFromPack()));
        }
        return null;

    }

    /**
     * get number of  random cards from a pack and store them into a token type array
     *
     * @param noOfCards number of card
     * @return a token array contains random cards
     */
    public Token[] getRandomCards(int noOfCards) {

        int lengthOfArray = Math.min(this.getNoOfTokensFromPack(), noOfCards);

        Token[] newTokens = new Token[lengthOfArray];
        for (int i = 0; i < newTokens.length; i++) {
            newTokens[i] = this.getARandomToken();
        }
        return newTokens;
    }


    /**
     * remove a token from the pack or hand
     * <p>
     * This can be used to remove a token from action pack, symbol token or the full pack
     *
     * @param token the given token
     */
    public void removeToken(Token token) {
        if (!isEmpty()) {
            this.handList.remove(token);
        }
    }

    /**
     * remove a token from the pack or hand
     * <p>
     * This can be used to remove a token from action pack, symbol token or the full pack
     */
    public void removeTokenAtIndex(int index) {
        if (!isEmpty()) {
            this.handList.remove(index);
            this.handList.add(index, null);
        }
    }


    /**
     * remove a pack of tokens from the full pack
     * This can be used to remove a token from action pack, symbol token or the full pack
     */
    public void removeTokens(Pack pack) {
        if (pack != null) {
            int size = pack.getNoOfTokensFromPack();
            for (int i = 0; i < size; i++) {
                this.removeToken(pack.getATokenOnIndex(i));
            }
        }
    }

    /**
     * count how many token left in a pack
     *
     * @param token a given token
     * @return int value of remain token from a pack
     */
    public int getRemainToken(Token token) {
        int remainNum = 0;

        for (int i = 0; i < this.getNoOfTokensFromPack(); i++) {
            if (this.getTokenList().get(i) == token) {
                remainNum++;
            }
        }
        return remainNum;

    }

    /**
     * check if the pack contain the given token
     *
     * @param token a given token
     * @return true, contain the token
     * false, not contain the token
     */
    public boolean containTheToken(Token token) {
        return this.handList.contains(token);
    }

    /**
     * check if a pack contain action token or not.
     *
     * @return true, contain the token
     * false, not contain the token
     */
    public boolean containActionToken() {
        return this.handList.contains(Token.REMOVE) || this.handList.contains(Token.SHIFTER) || this.handList.contains(Token.EXCHANGE) || this.handList.contains(Token.REPLACER);
    }

    /**
     * check if a pack contain symbol token or not.
     *
     * @return true, contain the token
     * false, not contain the token
     */
    public boolean containSymbolToken() {
        return this.handList.contains(Token.SUN) || this.handList.contains(Token.CROSS) || this.handList.contains(Token.TRIANGLE)
               || this.handList.contains(Token.SQUARE) || this.handList.contains(Token.PENTAGON) || this.handList.contains(Token.STAR);

    }

    /**
     * check if the pack is full of symbol token
     * and if there is null in a hand, then does not count in.
     *
     * @return true, contain all symbol token
     * false, not contain all symbol token
     */
    public boolean containAllSymbolToken() {
        int count = 0;
        for (int idx = 0; idx < this.getNoOfTokensFromPack(); idx++) {
            if (this.getTokenList().get(idx) != null) {
                if (this.getTokenList().get(idx).getIndex() <= Token.STAR.getIndex() && this.getTokenList().get(idx).getIndex() >= Token.SUN.getIndex()) {
                    count++;
                }
            }
        }
        return count == this.getNoOfTokensFromPack();
    }

    /**
     * check if the pack is full of action token
     * and if there is null in a hand, then does not count in.
     *
     * @return true, contain all action token
     * false, not contain all action token
     */
    public boolean containAllActionToken() {
        int count = 0;
        for (int idx = 0; idx < this.getNoOfTokensFromPack(); idx++) {
            if (this.getTokenList().get(idx) != null) {
                if (this.getTokenList().get(idx).getIndex() <= Token.REPLACER.getIndex() && this.getTokenList().get(idx).getIndex() >= Token.REMOVE.getIndex()) {
                    count++;
                }
            }
        }
        return count == this.getNoOfTokensFromPack();
    }

    /**
     * check if the pack is full of action token
     * and if there is null in a hand, then does not count in.
     *
     * @return true, contain all action token
     * false, not contain all action token
     */
    public boolean containAllReplaceToken() {
        int maxAmountOfReplace = 3;
        int count = 0;
        for (int idx = 0; idx < this.getNoOfTokensFromPack(); idx++) {
            if (this.getTokenList().get(idx) != null) {
                if (this.getTokenList().get(idx).getIndex() == Token.REPLACER.getIndex()) {
                    count++;
                }
            }

        }
        return count == maxAmountOfReplace;
    }


    /**
     * hand list representation.
     *
     * @return a string value.
     */
    @Override
    public String toString() {
        return handList.toString();
    }

    /**
     * assertEquals call the equals.
     * a pack is equal if it contains the same tokens as the other pack, not
     * more or less.
     *
     * @param obj another pack
     * @return true, if the two packs hold the same tokens
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Pack) {
            if (!this.isEmpty() && !(((Pack) obj).isEmpty() && this.getNoOfTokensFromPack() == ((Pack) obj).getNoOfTokensFromPack())) {
                return this.handList.equals(((Pack) obj).handList);
            } else return this.isEmpty() && ((Pack) obj).isEmpty();
        }
        return false;
    }
}
