package com.beans;

import com.SentimentUtils;

/**
 * Review is represented as each film review. It contains the name of the review
 * and the goodness of that. More goodness options are defined in the
 * SentimentUtils class.
 * 
 * @author Yu Zhang
 * 
 *         Review.java
 * 
 *         Apr 16, 2016
 *
 */
public class Review implements Comparable<Review> {
    private final String filename;
    private int goodness = SentimentUtils.TAG_NO_FEELING;

    public Review(String filename) {
        this.filename = filename;
    }

    public Review(String filename, int goodness) {
        this(filename);
        this.goodness = goodness;
    }

    public String getFileName() {
        return filename;
    }

    public int getGoodness() {
        return goodness;
    }

    public void setGoodness(int goodness) {
        this.goodness = goodness;
    }

    @Override
    public int compareTo(Review another) {
        return filename.equals(another.getFileName()) ? 0 : 1;
    }

    @Override
    public String toString() {
        return filename + "|" + SentimentUtils.getStringRes(goodness);
    }
}
