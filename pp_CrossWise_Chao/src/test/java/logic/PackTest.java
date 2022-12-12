package logic;

import org.junit.Test;

import static org.junit.Assert.*;

public class PackTest {

    /**
     * a constructor contains a size
     */
    @Test
    public void test_PackConstructor_Empty() {
        Pack pack = new Pack();

        assertTrue(pack.isEmpty());
        assertEquals(0, pack.getNoOfTokensFromPack());

    }

    /**
     * a constructor contains a size
     */
    @Test
    public void test_PackConstructor_withSize() {
        Pack pack = new Pack();

        assertTrue(pack.isEmpty());

        pack.addToken(Token.CROSS);
        pack.addToken(Token.CROSS);
        pack.addToken(Token.CROSS);

        assertEquals(3, pack.getNoOfTokensFromPack());
        assertFalse(pack.isEmpty());
    }

    /**
     * a constructor contains several existed tokens
     */
    @Test
    public void test_PackConstructor_TokenExisted() {
        Pack pack = new Pack(new Token[]{Token.STAR, Token.SUN});

        assertEquals(2, pack.getNoOfTokensFromPack());

    }


    /**
     * test add method
     */
    @Test
    public void test_PackAdd() {
        Pack pack = new Pack();

        pack.addToken(Token.STAR);
        pack.addToken(Token.CROSS);
        pack.addToken(Token.CROSS);
        pack.addToken(Token.CROSS);

        assertEquals(4, pack.getNoOfTokensFromPack());
    }

    /**
     * test add method
     */
    @Test
    public void test_PackAddTokens() {
        Pack pack = new Pack(new Token[]{Token.STAR, Token.CROSS, Token.CROSS, Token.CROSS});

        pack.addTokens(new Token[]{Token.STAR, Token.CROSS, Token.CROSS, Token.CROSS});
        assertEquals(8, pack.getNoOfTokensFromPack());

    }

    @Test
    public void testAddTokens_ArrayWithNulls() {
        Pack pack = new Pack();
        pack.addTokens(new Token[]{});

        assertEquals("addTokens(new Token[] {null, null}) should have no effect on the pack", 0, pack.getNoOfTokensFromPack());
    }

    @Test
    public void testAddTokens_NotSame() {
        Pack pack1 = new Pack();
        Pack pack2 = new Pack();
        pack1.addTokens(new Token[]{Token.STAR, Token.CROSS, Token.CROSS, Token.CROSS});
        pack2.addTokens(new Token[]{Token.SUN, Token.STAR, Token.CROSS, Token.EXCHANGE});

        assertNotEquals("addTokens: The tokens in the pack need to be added in the correct order", pack1, pack2);
    }

    /**
     *
     */
    @Test
    public void testAddTokens_Same() {
        Pack pack1 = new Pack();
        Pack pack2 = new Pack();
        pack1.addTokens(new Token[]{Token.STAR, Token.CROSS, Token.CROSS, Token.CROSS});
        pack2.addTokens(new Token[]{Token.STAR, Token.CROSS, Token.CROSS, Token.CROSS});

        assertEquals("addTokens: The tokens in the pack need to be added in the correct order", pack1, pack2);
    }

    /**
     * test a full pack contains every tokens
     */
    @Test
    public void testFullPack_everyTokens() {
        Pack pack = new Pack(new Token[]{
                Token.SUN, Token.SUN, Token.SUN, Token.SUN, Token.SUN, Token.SUN, Token.SUN,
                Token.CROSS, Token.CROSS, Token.CROSS, Token.CROSS, Token.CROSS, Token.CROSS, Token.CROSS,
                Token.TRIANGLE, Token.TRIANGLE, Token.TRIANGLE, Token.TRIANGLE, Token.TRIANGLE, Token.TRIANGLE, Token.TRIANGLE,
                Token.SQUARE, Token.SQUARE, Token.SQUARE, Token.SQUARE, Token.SQUARE, Token.SQUARE, Token.SQUARE,
                Token.PENTAGON, Token.PENTAGON, Token.PENTAGON, Token.PENTAGON, Token.PENTAGON, Token.PENTAGON, Token.PENTAGON,
                Token.STAR, Token.STAR, Token.STAR, Token.STAR, Token.STAR, Token.STAR, Token.STAR,
                Token.REMOVE, Token.REMOVE, Token.REMOVE,
                Token.SHIFTER, Token.SHIFTER, Token.SHIFTER,
                Token.EXCHANGE, Token.EXCHANGE, Token.EXCHANGE,
                Token.REPLACER, Token.REPLACER, Token.REPLACER,
        });

        Pack pack2 = Pack.createAPackOfEveryTokens();
        assertEquals(54, pack2.getNoOfTokensFromPack());
        assertEquals(pack, pack2);
    }


    /**
     * test a full pack contains symbol tokens
     */
    @Test
    public void testFullPack_symbolTokens() {
        Pack pack = new Pack(new Token[]{
                Token.SUN, Token.SUN, Token.SUN, Token.SUN, Token.SUN, Token.SUN, Token.SUN,
                Token.CROSS, Token.CROSS, Token.CROSS, Token.CROSS, Token.CROSS, Token.CROSS, Token.CROSS,
                Token.TRIANGLE, Token.TRIANGLE, Token.TRIANGLE, Token.TRIANGLE, Token.TRIANGLE, Token.TRIANGLE, Token.TRIANGLE,
                Token.SQUARE, Token.SQUARE, Token.SQUARE, Token.SQUARE, Token.SQUARE, Token.SQUARE, Token.SQUARE,
                Token.PENTAGON, Token.PENTAGON, Token.PENTAGON, Token.PENTAGON, Token.PENTAGON, Token.PENTAGON, Token.PENTAGON,
                Token.STAR, Token.STAR, Token.STAR, Token.STAR, Token.STAR, Token.STAR, Token.STAR,
        });

        Pack pack2 = Pack.createAPackOfSymbolTokens();

        assertEquals(42, pack2.getNoOfTokensFromPack());
        assertEquals(pack, pack2);
    }

    /**
     * test a full pack contains action tokens
     */
    @Test
    public void testFullPack_ActionTokens() {
        Pack pack = new Pack(new Token[]{
                Token.REMOVE, Token.REMOVE, Token.REMOVE,
                Token.SHIFTER, Token.SHIFTER, Token.SHIFTER,
                Token.EXCHANGE, Token.EXCHANGE, Token.EXCHANGE,
                Token.REPLACER, Token.REPLACER, Token.REPLACER,
        });

        Pack pack2 = Pack.createAPackOfActionTokens();

        assertEquals(12, pack2.getNoOfTokensFromPack());
        assertEquals(pack, pack2);
    }

    /**
     * test an empty pack
     */
    @Test
    public void testAEmptyPack() {
        Pack pack = new Pack();
        assertTrue(pack.isEmpty());

    }

    /**
     * test not an empty pack
     */
    @Test
    public void testAEmptyPack_false() {
        Pack pack = new Pack(new Token[]{Token.SUN, Token.PENTAGON});
        assertFalse(pack.isEmpty());
    }

    /**
     * test get number of pack with no token pack
     */
    @Test
    public void testGetNoOfPack_EmptyPack() {
        Pack pack = new Pack();
        assertEquals(0, pack.getNoOfTokensFromPack());
    }

    /**
     * test get number of pack with 2 tokens pack
     */
    @Test
    public void testGetNoOfPack_twoToken() {
        Pack pack = new Pack(new Token[]{Token.SUN, Token.PENTAGON});
        assertEquals(2, pack.getNoOfTokensFromPack());
    }

    /**
     * test get number of pack with 4 tokens in a hand
     */
    @Test
    public void testGetNoOfPack_Hand() {
        Hand hand = new Hand(new Token[]{Token.PENTAGON, Token.SHIFTER, Token.SQUARE, Token.SQUARE});
        assertEquals(4, hand.getNoOfTokensFromPack());
    }

    /**
     * test a method to add a token
     */
    @Test
    public void testAddToken_1_2_3_4() {
        Pack pack = new Pack();
        pack.addToken(Token.SUN);
        assertEquals(1, pack.getNoOfTokensFromPack());

        pack.addToken(Token.SQUARE);
        assertEquals(2, pack.getNoOfTokensFromPack());

        pack.addToken(Token.SHIFTER);
        assertEquals(3, pack.getNoOfTokensFromPack());


        pack.addToken(Token.CROSS);
        assertEquals(4, pack.getNoOfTokensFromPack());

    }

    /**
     * test a method to add a tokens array which has several tokens
     */
    @Test
    public void testAddTokensArray() {
        Pack pack = new Pack();
        pack.addTokens(new Token[]{Token.CROSS});
        assertEquals(1, pack.getNoOfTokensFromPack());
        assertFalse(pack.isEmpty());

    }

    /**
     * Test of isValidIndex method, of class Pack.
     */
    @Test
    public void testIsValidIndex_InBounds() {
        Pack pack = new Pack();

        assertTrue("The index should be valid", pack.isValidIndex(0, 2));
        assertTrue("The index should be valid", pack.isValidIndex(3, 4));
    }

    /**
     * test of isValidIndex method, index is invalid
     */
    @Test
    public void testIsValidIndex_OutBounds() {
        Pack pack = new Pack();

        assertFalse("The index is higher than the array size", pack.isValidIndex(4, 4));
        assertFalse("The index is higher than the array size", pack.isValidIndex(5, 3));
        assertFalse("The index is out of bound ", pack.isValidIndex(-1, 3));
    }

    /**
     * test empty size has no valid index
     */
    @Test
    public void testIsValidIndex_Empty() {
        Pack pack = new Pack();

        assertFalse(pack.isValidIndex(0, 0));
    }

    /**
     * Test of get a token on a given index method, of class Pack.
     */
    @Test
    public void testGetTokenOnIndex() {
        Pack pack = new Pack(new Token[]{Token.SUN, Token.SQUARE});
        assertEquals("new Pack(new Token[]{Token.SUN, Token.SQUARE}): The pack should contain 2 tokens", 2, pack.getNoOfTokensFromPack());

        Token token = pack.getATokenOnIndex(0);
        assertEquals("getATokenOnIndex(0): Number of tokens left in the pack", 2, pack.getNoOfTokensFromPack());
        assertEquals("getATokenOnIndex(0): The token ", Token.SUN, token);

        token = pack.getATokenOnIndex(1);

        assertEquals("getATokenOnIndex(1): Number of tokens left in the pack", 2, pack.getNoOfTokensFromPack());
        assertEquals("getATokenOnIndex(1): The token ", Token.SQUARE, token);

        token = pack.getATokenOnIndex(11);
        assertNull("getATokenOnIndex(11): The token should be null (the index is bigger than the size of the pack)", token);
    }

    /**
     * test a get random method in a pack that, no token can be given
     */
    @Test
    public void test_getARandomToken_oneOfEmpty() {
        Pack pack = new Pack();
        Token token = pack.getARandomToken();

        assertNull(token);
        assertEquals(0, pack.getNoOfTokensFromPack());
    }

    /**
     * test get random token method in only one token in pack
     */
    @Test
    public void test_getARandomToken_oneOfOne() {
        Pack pack = new Pack(new Token[]{Token.STAR});
        Token token = pack.getARandomToken();

        assertNotNull(token);
        assertEquals(0, pack.getNoOfTokensFromPack());

    }

    /**
     * test pack has five tokens.
     */
    @Test
    public void test_getARandomToken_oneOfFive() {
        Pack pack = new Pack(new Token[]{Token.STAR, Token.SUN, Token.SHIFTER, Token.SQUARE, Token.TRIANGLE});
        Token token = pack.getARandomToken();

        assertNotNull(token);
        assertEquals(4, pack.getNoOfTokensFromPack());

    }

    /**
     * test the method of  a given token needs to remove from a pack.
     */
    @Test
    public void testRemoveToken_OneToken() {
        Pack pack = new Pack(new Token[]{Token.SUN});
        pack.removeToken(Token.SUN);
        Pack expected = new Pack(new Token[]{});


        assertEquals(expected, pack);
    }

    /**
     * test one token is removed from a pack
     */
    @Test
    public void testRemoveToken_OneOfTwoToken() {
        Pack pack = new Pack(new Token[]{Token.SUN, Token.PENTAGON});
        pack.removeToken(Token.PENTAGON);
        Pack expected = new Pack(new Token[]{Token.SUN});

        assertEquals(expected, pack);
        assertEquals(1, pack.getNoOfTokensFromPack());
    }

    /**
     * test remove a token that has two same in a pack
     */
    @Test
    public void testRemoveToken_TwoSameTokens() {
        Pack pack = new Pack(new Token[]{Token.SUN, Token.PENTAGON, Token.PENTAGON});
        pack.removeToken(Token.PENTAGON);
        Pack expected = new Pack(new Token[]{Token.SUN, Token.PENTAGON});

        assertEquals(expected, pack);
        assertEquals(2, pack.getNoOfTokensFromPack());

    }

    /**
     * test a given remove token not in a a pack
     */
    @Test
    public void testRemoveToken_notInPack() {
        Pack pack = new Pack(new Token[]{Token.SUN, Token.PENTAGON, Token.PENTAGON});
        pack.removeToken(Token.REMOVE);
        Pack expected = new Pack(new Token[]{Token.SUN, Token.PENTAGON, Token.PENTAGON});

        assertEquals(expected, pack);
        assertEquals(3, pack.getNoOfTokensFromPack());
    }


    /**
     * test removed tokens
     */
    @Test
    public void testRemoveTokens_APack() {
        Pack pack = new Pack(new Token[]{Token.SUN, Token.PENTAGON, Token.PENTAGON});
        Pack removedPack = new Pack(new Token[]{Token.SUN, Token.PENTAGON});
        pack.removeTokens(removedPack);
        Pack expected = new Pack(new Token[]{Token.PENTAGON});

        assertEquals(expected, pack);
        assertEquals(1, pack.getNoOfTokensFromPack());
    }

    @Test
    public void testRemoveTokens_TwoTokensInPack() {
        Pack pack = new Pack(new Token[]{Token.SUN, Token.PENTAGON});
        Pack removedPack = new Pack(new Token[]{Token.SUN, Token.PENTAGON});
        pack.removeTokens(removedPack);
        Pack expected = new Pack(new Token[]{});

        assertEquals(expected, pack);
        assertEquals(0, pack.getNoOfTokensFromPack());
    }

    /**
     * test get remain tokens by giving a token
     */
    @Test
    public void testGetRemainToken() {
        Pack pack = new Pack(new Token[]{
                Token.REMOVE, Token.REMOVE, Token.REMOVE,
                Token.SHIFTER, Token.SHIFTER, Token.SHIFTER,
                Token.EXCHANGE, Token.EXCHANGE, Token.EXCHANGE,
                Token.REPLACER, Token.REPLACER, Token.REPLACER,
        });

        assertEquals(3, pack.getRemainToken(Token.REMOVE));
        assertEquals(3, pack.getRemainToken(Token.SHIFTER));
        assertEquals(3, pack.getRemainToken(Token.EXCHANGE));
        assertEquals(3, pack.getRemainToken(Token.REPLACER));
        assertEquals(0, pack.getRemainToken(Token.SUN));
    }

    /**
     * test a get a toke index from a hand
     */
    @Test
    public void testGetTokenIndexFromHand() {
        Pack pack = new Pack(new Token[]{Token.STAR, Token.SUN, Token.REMOVE, Token.SQUARE});

        assertEquals(0, pack.getAtTokenIndexFromAHand(Token.STAR));

    }

    /**
     *  test a pack contain any symbol token
     */
    @Test
    public void test_boolean_containSymbolToken() {
        Pack pack = new Pack(new Token[]{Token.STAR, Token.SUN, Token.REMOVE, Token.SQUARE});

        assertTrue(pack.containSymbolToken());
    }

    /**
     * test a pack contain any action token
     */
    @Test
    public void test_boolean_containActionToken() {
        Pack pack = new Pack(new Token[]{Token.REMOVE, Token.SHIFTER, Token.REMOVE, Token.EXCHANGE});

        assertTrue(pack.containActionToken());
    }

    /**
     * test if you require to get no card
     */
    @Test
    public void test_getRandom_noCard(){
        Pack pack = new Pack(new Token[]{Token.STAR, Token.SUN, Token.SHIFTER, Token.SQUARE, Token.TRIANGLE});
        Token[] token = pack.getRandomCards(0);
        assertEquals(0,token.length);

    }

}
