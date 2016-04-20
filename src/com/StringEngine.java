package com;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.core.MatchProcessor;
import com.core.ProcessorType;
import com.core.SentimentProcessor;
import com.search.SearchType;

/**
 * The StringEngine will start to execute the String matching task by given
 * options in the multi-thread environment. It will assign the task to multiple
 * threads and gather return messages from those threads to the master thread.
 * 
 * @author Yu Zhang
 * 
 *         StringEngine.java
 * 
 *         Apr 16, 2016
 *
 */
public class StringEngine {

    /*
     * This is used to collect and gather messages from threads to the master.
     */
    private BlockingQueue<String> messageQueue;
    /* The default size of threads */
    private int taskSize = 1;
    private int fileSize = 0;
    private final ProcessorType type;
    private static long startTime = 0;
    private boolean running = false;

    static {
        startTime = System.currentTimeMillis();
    }

    public StringEngine(int fileSize, int taskSize, ProcessorType type) {
        this.fileSize = fileSize;
        this.type = type;
        switch (type) {
            case MATCH:
                this.taskSize = Math.min(ReadFile.TITLE_SIZE, taskSize);
                break;
            case SENTIMENT:
                this.taskSize = Math.min(ReadFile.REVIEW_SIZE, taskSize);
                break;
        }
        messageQueue = new LinkedBlockingQueue<>(this.taskSize);
    }

    public int getTaskSize() {
        return taskSize;
    }

    /**
     * Multiple processing to do the searching task by the given search type.
     * 
     * @param tag
     */
    public void multiProcess(SearchType tag) {
        if (!running) {
            running = true;
            int subLength = fileSize / taskSize;
            int remainLength = fileSize % taskSize;
            for (int i = 0; i < taskSize; i++) {
                int start = i * subLength;
                int end = (i == taskSize - 1 ? subLength + remainLength
                        : subLength) + start;
                switch (type) {
                    case MATCH:
                        new MatchProcessor(i, start, end, tag, messageQueue)
                                .start();
                        break;
                    case SENTIMENT:
                        new SentimentProcessor(i, start, end, tag, messageQueue)
                                .start();
                        break;
                    default:
                        break;
                }

            }
        }
    }

    /**
     * Gather results from different threads.
     * 
     * @return
     */
    public String gatherResult() {
        if (!running) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int restTaskSize = taskSize;
        while (restTaskSize > 0) {
            try {
                sb.append(messageQueue.take());
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            restTaskSize--;
        }
        switch (type) {
            case MATCH:
                sb.append("Title Match Time: ");
                break;
            case SENTIMENT:
                sb.append("Sentiment Analysis Time: ");
                break;
        }
        sb.append(getRunTime(startTime) + "s");
        running = false;
        return sb.toString();
    }

    public static float getRunTime(long startTime) {
        return (float) (System.currentTimeMillis() - startTime) / 1000;
    }
}