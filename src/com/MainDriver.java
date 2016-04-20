package com;

import com.core.ProcessorType;
import com.search.SearchType;

/**
 * This is the main driver for this knowledge technology project. In this
 * project, matching between a list of film titles and a list of film reviews is
 * key task. Meanwhile, it will deal with the simple sentiment analysis to
 * decide how goodness each review is for each film title.
 * 
 * This can be executed by using either Java command line or the well-known IDE
 * (Eclipse) as a simple Java Project. In particular, both of the multi-thread
 * environments are provided to improve the performance of doing huge string
 * searching tasks, the Java multi-thread environment and the cluster
 * environment which can be found in the cluster/ folder.
 * 
 * @author Yu Zhang
 * 
 *         MainDriver.java
 * 
 *         Apr 16, 2016
 *
 */
public class MainDriver {

    /* The number of the threads */
    public static final int taskSize = 5;
    /* The type of which approach */
    public static final SearchType type = SearchType.GLOBAL_APPROX;

    public static void main(String[] args) {
        // Processing the sentiment part for each reviews.
        StringEngine sEngine = new StringEngine(ReadFile.REVIEW_SIZE, taskSize,
                ProcessorType.SENTIMENT);
        sEngine.multiProcess(type);
        System.out.println(sEngine.gatherResult());

        // Processing the string matching part for each film title.
        sEngine = new StringEngine(ReadFile.TITLE_SIZE, taskSize,
                ProcessorType.MATCH);
        sEngine.multiProcess(type);
        String result = sEngine.gatherResult();
        // Print the result into a file for conveniently reviewing the result.
        ReadFile.getInstance().write(result, ReadFile.OUTPUT_PATH);
        System.out.println(result);
    }
}
