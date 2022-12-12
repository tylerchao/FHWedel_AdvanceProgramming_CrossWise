package logic;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This test class contains all the necessary methods in hand class.
 */
public class HandTest {

    /**
     * test a hand constructor
     */
    @Test
    public void test_HandConstructor() {
        Hand hand = new Hand();
        assertTrue(hand.isEmpty());
    }

    /**
     * test constructor with a size of the pack
     */
    @Test
    public void test_HandConstructor_withSizeOfPack() {
        Hand hand = new Hand();
        assertTrue(hand.isEmpty());

        hand.addToken(Token.CROSS);
        hand.addToken(Token.CROSS);
        hand.addToken(Token.CROSS);

        assertEquals(3, hand.getTokenList().size());
        assertFalse(hand.isEmpty());
    }


    /**
     * test constructor with existed token
     */
    @Test
    public void test_HandConstructor_TokenExisted() {
        Hand hand = new Hand(new Token[]{Token.STAR, Token.SUN});
        hand.addToken(Token.STAR);
        hand.addToken(Token.CROSS);

        assertEquals(4, hand.getTokenList().size());
        assertFalse(hand.isEmpty());
    }


    /**
     * test get a token from hand
     */
    @Test
    public void test_getTokenFromHand() {
        Hand hand = new Hand(new Token[]{Token.STAR, Token.SUN, Token.SHIFTER, Token.SQUARE});

        assertEquals(Token.STAR, hand.getATokenOnIndex(0));
        assertEquals(4, hand.getNoOfTokensFromPack());
    }
}
