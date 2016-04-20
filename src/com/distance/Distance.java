package com.distance;

/**
 * An abstract super class for representing the implementation of different Edit
 * distance algorithms.
 * 
 * @author Yu Zhang
 * 
 *         Distance.java
 * 
 *         Apr 16, 2016
 *
 */
public abstract class Distance {

    public static final double MAX_SIMILARITY = 1.0;
    /* 3 operations, insertion, deletion, substitution */
    protected static final int GAP = -1;
    protected static final int MATCH = 1;
    protected static final String FIRST_POS = "-";

    protected final String s1;
    protected final String s2;
    protected int distance[][];
    protected int maxDistance = 0;

    public int getDistance() {
        return maxDistance;
    }

    public Distance(String s1, String s2) {
        this.s1 = FIRST_POS + s1.toLowerCase();
        this.s2 = FIRST_POS + s2.toLowerCase();
        distance = new int[s1.length() + 1][s2.length() + 1];
        editDistance();
    }

    /**
     * The descendant class should to implement the algorithm of the edit
     * distance by using different approaches.
     */
    protected abstract void editDistance();

    protected abstract int maxValue(int i, int j, int op);

    /**
     * Return the similarity between the key and the text.
     * 
     * @return
     */
    public double similarity() {
        double sim = (double) maxDistance
                / Math.min(s1.length() - 1, s2.length() - 1);
        return sim > MAX_SIMILARITY ? MAX_SIMILARITY : sim;
    }
}
