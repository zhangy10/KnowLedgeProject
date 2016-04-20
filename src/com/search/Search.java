package com.search;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.List;

/**
 * Designed the super Search class as an interface. The descendant class should
 * contain these essential variables and implement the searchPattern() method.
 * 
 * @author Yu Zhang
 * 
 *         Search.java
 * 
 *         Apr 16, 2016
 *
 */
public abstract class Search {

    protected String text;
    protected String key;

    public Search(String text, String key) {
        this.text = text;
        this.key = key;
    }

    public abstract List<String> searchPattern();

    /**
     * Converting the pure text with the special characters to the regular
     * expression text format.
     * 
     * @param s1
     * @return
     */
    protected static String toRegex(String s1) {
        final StringBuilder result = new StringBuilder();

        final StringCharacterIterator iterator = new StringCharacterIterator(
                s1);
        char character = iterator.current();
        while (character != CharacterIterator.DONE) {
            if (character == '.') {
                result.append("\\.");
            }
            else if (character == '\\') {
                result.append("\\\\");
            }
            else if (character == '?') {
                result.append("\\?");
            }
            else if (character == '*') {
                result.append("\\*");
            }
            else if (character == '+') {
                result.append("\\+");
            }
            else if (character == '&') {
                result.append("\\&");
            }
            else if (character == ':') {
                result.append("\\:");
            }
            else if (character == '{') {
                result.append("\\{");
            }
            else if (character == '}') {
                result.append("\\}");
            }
            else if (character == '[') {
                result.append("\\[");
            }
            else if (character == ']') {
                result.append("\\]");
            }
            else if (character == '(') {
                result.append("\\(");
            }
            else if (character == ')') {
                result.append("\\)");
            }
            else if (character == '^') {
                result.append("\\^");
            }
            else if (character == '$') {
                result.append("\\$");
            }
            else {
                result.append(character);
            }
            character = iterator.next();
        }
        return result.toString();
    }
}
