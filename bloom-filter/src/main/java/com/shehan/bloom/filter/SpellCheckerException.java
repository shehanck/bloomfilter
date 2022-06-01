package com.shehan.bloom.filter;

/**
 * Uses to throw custom exceptions specific to SpellChecker library class
 * @author Shehan Charuka
 */
public class SpellCheckerException extends Exception {

    public SpellCheckerException(String s, Throwable e) {
        super(s, e);
    }

    public SpellCheckerException(String s) {
        super(s);
    }
}
