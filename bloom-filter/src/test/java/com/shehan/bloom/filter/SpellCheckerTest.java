/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shehan.bloom.filter;

import org.junit.*;
import java.io.IOException;


/**
 *
 * @author Shehan Charuka
 */
public class SpellCheckerTest {
    
    public SpellCheckerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of lookup method, of class SpellChecker.
     */
    @Test
    public void testLookup() throws IOException, SpellCheckerException {
        SpellChecker spellChecker = new SpellChecker();
        Assert.assertFalse(spellChecker.lookup("bloomfilter"));
        Assert.assertTrue(spellChecker.lookup("AB"));
    }

    @Test
    public void testLookupWithParams() throws IOException, SpellCheckerException {
        SpellChecker spellChecker = new SpellChecker("2000001", "4", null);
        Assert.assertFalse(spellChecker.lookup("bloomfilter"));
        Assert.assertTrue(spellChecker.lookup("AB"));
    }

    @Test
    public void testLookupWithInvalidPath() throws IOException, SpellCheckerException {
        SpellChecker spellChecker = new SpellChecker("2000001", "4", "/test");
        Assert.assertFalse(spellChecker.lookup("bloomfilter"));
        Assert.assertTrue(spellChecker.lookup("AB"));
    }

    @Test
    public void testLookupWithNumberFormatExceptionForParams() throws IOException, SpellCheckerException {
        SpellChecker spellChecker = new SpellChecker("test", "test", null);
        Assert.assertFalse(spellChecker.lookup("bloomfilter"));
        Assert.assertTrue(spellChecker.lookup("AB"));
    }

    /**
     * Test of isFalsePositive method, of class SpellChecker.
     */
    @Test
    public void testIsFalsePositive() throws IOException, SpellCheckerException {
        SpellChecker spellChecker = new SpellChecker();
        Assert.assertFalse(spellChecker.isFalsePositive("AB"));
        Assert.assertTrue(spellChecker.isFalsePositive("bloomfilter"));
    }
    
}
