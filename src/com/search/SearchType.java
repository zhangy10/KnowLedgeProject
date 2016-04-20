package com.search;

/**
 * Defined 3 different approaches to deal with the String matching tasks.
 * 
 * @author Yu Zhang
 * 
 *         SearchType.java
 * 
 *         Apr 16, 2016
 *
 */
public enum SearchType {
    /**
     * Exact Searching Matching by using the Regular Expression.
     */
    EXACT, /**
            * Approximate searching match approach implemented by local edit
            * distance idea.
            */
    LOCAL_APPROX, /**
                   * Approximate searching match approach implemented by Global
                   * edit distance idea.
                   */
    GLOBAL_APPROX;
}
