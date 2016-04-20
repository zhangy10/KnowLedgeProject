package com;

import java.util.HashMap;

/**
 * Defined the resources for sentiment analysis.
 * 
 * @author Yu Zhang
 * 
 *         SentimentUtils.java
 * 
 *         Apr 16, 2016
 *
 */
public class SentimentUtils {

    /* The output format for the result of the sentiment analysis. */
    public static final String GOOD = "positive\"+\"";
    public static final String BAD = "negative\"-\"";
    public static final String MIDDLE = "normal\"~\"";
    public static final String NO_FEELING = "";

    public static final int TAG_GOOD = 20;
    public static final int TAG_BAD = TAG_GOOD + 1;
    public static final int TAG_NO_FEELING = TAG_GOOD + 2;
    public static final int TAG_MIDDLE = TAG_GOOD + 3;

    private static final HashMap<Integer, String> resourceMap = new HashMap<>();

    /* Basic positive words */
    public static final String[] POSITIVE = { "good", "nice", "excellent",
            "positive", "fortunate", "correct", "superior", "great", "happy",
            "impressive" };
    /* Basic negative words */
    public static final String[] NEGATIVE = { "bad", "nasty", "poor",
            "negative", "unfortunate", "wrong", "inferior", "awful", "strange",
            "unhappy" };

    static {
        resourceMap.put(TAG_GOOD, GOOD);
        resourceMap.put(TAG_BAD, BAD);
        resourceMap.put(TAG_NO_FEELING, NO_FEELING);
        resourceMap.put(TAG_MIDDLE, MIDDLE);
    }

    public static String getStringRes(int goodness) {
        return resourceMap.get(goodness);
    }

}
