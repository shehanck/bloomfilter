package com.shehan.bloom.filter;

import org.junit.*;

import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Shehan Charuka
 */
public class BloomFilterTest {
    private static final int DEFAULT_BITMAP_SIZE = 2000000;
    private static final int DEFAULT_HASH_COUNT = 4;

    private BloomFilter bf;
    
    public BloomFilterTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {

    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws NoSuchAlgorithmException {
        bf = new BloomFilter(DEFAULT_BITMAP_SIZE,DEFAULT_HASH_COUNT);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of resetBitMap method, of class BloomFilter.
     */
    @Test
    public void testResetBitMap() {
        bf.resetBitMap();
    }

    /**
     * Test of insert method, of class BloomFilter.
     */
    @Test
    public void testInsert() {
        String word = "test";
        bf.insert(word);
        Assert.assertTrue(bf.lookup(word));
    }

    /**
     * Test of lookup method, of class BloomFilter.
     */
    @Test
    public void testLookup() {
        String word = "test";
        Assert.assertFalse(bf.lookup(word));
        bf.insert(word);
        Assert.assertTrue(bf.lookup(word));
    }
    
}
