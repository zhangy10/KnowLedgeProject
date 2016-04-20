package com.search;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Exact searching approach by using regular expression.
 * 
 * @author Yu Zhang
 * 
 *         ExactSearch.java
 * 
 *         Apr 16, 2016
 *
 */
public class ExactSearch extends Search {

    public static final String OUT_PUT = "Excat Alias: ";
    private static final String EXACT_PATTERN = "\\b(?i)%s\\b";

    public ExactSearch(String text, String key) {
        super(text, key);
    }

    private String getPattern(String title) {
        return String.format(EXACT_PATTERN, toRegex(title));
    }

    @Override
    public List<String> searchPattern() {
        List<String> list = new ArrayList<>();
        Pattern p = Pattern.compile(getPattern(key));
        Matcher m = p.matcher(text);
        while (m.find()) {
            list.add(m.group());
        }
        return list;
    }

}
