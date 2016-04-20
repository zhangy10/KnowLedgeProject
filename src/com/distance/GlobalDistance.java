package com.distance;

/**
 * The implementation of the Global Edit Distance algorithm based on the
 * NeedlemanWunsch.
 * 
 * @author Yu Zhang
 * 
 *         GlobalDistance.java
 * 
 *         Apr 16, 2016
 *
 */
public final class GlobalDistance extends Distance {

    public GlobalDistance(String s1, String s2) {
        super(s1, s2);
    }

    @Override
    protected void editDistance() {
        for (int i = 0; i < s1.length(); i++)
            distance[i][0] = -i;
        for (int j = 1; j < s2.length(); j++)
            distance[0][j] = -j;

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
                }
                if (i == s1.length() - 1 && j == s2.length() - 1) {
                    maxDistance = distance[i][j];
                }
            }
        }
    }

    @Override
    protected int maxValue(int i, int j, int op) {
        int sub = distance[i - 1][j - 1];
        int del = distance[i][j - 1];
        int insert = distance[i - 1][j];
        return Math.max(sub + op, Math.max(insert + op, del + op));
    }
}
