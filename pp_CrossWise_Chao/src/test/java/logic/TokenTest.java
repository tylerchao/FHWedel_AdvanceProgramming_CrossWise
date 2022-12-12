package logic;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * test token enum class
 */
public class TokenTest {

    /**
     * test getIndex method
     */
    @Test
    public void testEveryToken() {
        Token token = Token.NONE;
        Token token1 = Token.SUN;
        Token token2 = Token.CROSS;
        Token token3 = Token.TRIANGLE;
        Token token4 = Token.SQUARE;
        Token token5 = Token.PENTAGON;
        Token token6 = Token.STAR;
        Token token7 = Token.REMOVE;
        Token token8 = Token.SHIFTER;
        Token token9 = Token.EXCHANGE;
        Token token10 = Token.REPLACER;

        assertEquals(0, token.getIndex());
        assertEquals(1, token1.getIndex());
        assertEquals(2, token2.getIndex());
        assertEquals(3, token3.getIndex());
        assertEquals(4, token4.getIndex());
        assertEquals(5, token5.getIndex());
        assertEquals(6, token6.getIndex());
        assertEquals(7, token7.getIndex());
        assertEquals(8, token8.getIndex());
        assertEquals(9, token9.getIndex());
        assertEquals(10, token10.getIndex());

    }

}
