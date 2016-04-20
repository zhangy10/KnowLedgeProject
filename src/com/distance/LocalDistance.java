package com.distance;

/**
 * The implementation of the Local Edit Distance algorithm based on the
 * SmithWaterman.
 * 
 * @author Yu Zhang
 * 
 *         LocalDistance.java
 * 
 *         Apr 16, 2016
 *
 */
public final class LocalDistance extends Distance {

    private static final int EMPTY = 0;

    public LocalDistance(String s1, String s2) {
        super(s1, s2);
    }

    @Override
    protected void editDistance() {
        for (int i = 0; i < s1.length(); i++) {
            for (int j = 0; j < s2.length(); j++) {
                if (i != 0 && j != 0) {
                    if (s1.charAt(i) == s2.charAt(j)) {
                        // match
                        distance[i][j] = maxValue(i, j, MATCH);
                    }
                    else {
                        // gap
                        distance[i][j] = maxValue(i, j, GAP);
                    }
                    if (distance[i][j] > maxDistance) {
                        maxDistance = distance[i][j];
                    }
                }
            }
        }
    }

    @Override
    protected int maxValue(int i, int j, int op) {
        int sub = distance[i - 1][j - 1];
        int del = distance[i][j - 1];
        int insert = distance[i - 1][j];
        return Math.max(EMPTY,
                Math.max(sub + op, Math.max(insert + op, del + op)));
    }
}
