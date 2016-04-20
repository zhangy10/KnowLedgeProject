package com.search;

import java.util.ArrayList;
import java.util.List;

import com.distance.Distance;
import com.distance.GlobalDistance;
import com.distance.LocalDistance;

/**
 * Approximate searching approach.
 * 
 * @author Yu Zhang
 * 
 *         ApproxSearch.java
 * 
 *         Apr 16, 2016
 *
 */
public class ApproxSearch extends Search {

    /* The threshold to filter the inaccuracy data */
    public static final double THRESHOLD = 0.6;
    private static final String WORD_SPLIT = " ";
    private static final String OUT_PUT = "%s: similarity (%.2f) distance (%d)";
    private static final String STR_LOCAL = "Local";
    private static final String STR_GLOBAL = "Global";

    private SearchType tag = SearchType.LOCAL_APPROX;

    public ApproxSearch(String text, String key) {
        super(text, key);
    }

    public ApproxSearch(String text, String key, SearchType tag) {
        super(text, key);
        this.tag = tag;
    }

    @Override
    public List<String> searchPattern() {
        List<String> list = new ArrayList<>();
        Distance distance = null;
        String tagStr = "";
        switch (tag) {
            case LOCAL_APPROX:
                tagStr = STR_LOCAL;
                distance = new LocalDistance(key, text);
                break;
            case GLOBAL_APPROX:
                tagStr = STR_GLOBAL;
                // Split the text into each word to improve the accuracy of the
                // Global approach.
                String[] words = text.split(WORD_SPLIT);
                // Find max value of the distance.
                for (int i = 0; i < words.length; i++) {
                    Distance subDistance = new GlobalDistance(key, words[i]);
                    if (distance == null || distance.getDistance() < subDistance
                            .getDistance()) {
                        distance = subDistance;
                    }
                }
                break;
            default:
                break;
        }
        if (distance != null && distance.similarity() >= THRESHOLD) {
            list.add(String.format(OUT_PUT, tagStr, distance.similarity(),
                    distance.getDistance()));
        }
        return list;
    }

}
