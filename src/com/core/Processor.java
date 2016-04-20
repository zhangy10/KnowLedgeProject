package com.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import com.beans.MatchKey;
import com.search.SearchType;

/**
 * This is an abstract super class. It provides a general processing template.
 * 
 * @author Yu Zhang
 * 
 *         Processor.java
 * 
 *         Apr 16, 2016
 *
 */
public abstract class Processor extends Thread {

    /*
     * Empty message, if there is no required to return a message, the empty
     * message will be used in the case.
     */
    protected static final String EMPTY_MSG = "";
    protected int rank;
    protected int startPos;
    protected int total;
    protected SearchType tag = SearchType.EXACT;
    protected List<MatchKey> keylist;
    /*
     * This queue reference is provided by the master processor and will be used
     * to allow other processors to communicate with the master processor,
     * sending message or return result to the master.
     */
    private BlockingQueue<String> messageQueue = null;

    public Processor(int rank, int startPos, int total, SearchType tag,
            BlockingQueue<String> messageQueue) {
        this.rank = rank;
        this.tag = tag;
        this.startPos = startPos;
        this.total = total;
        this.messageQueue = messageQueue;
        this.keylist = new ArrayList<>();
    }

    @Override
    public void run() {
        String message = runTask();
        notifyMaster(message);
    }

    public abstract String runTask();

    /**
     * Notify the master processor by using the message queue.
     * 
     * @param message
     */
    protected void notifyMaster(String message) {
        if (messageQueue != null) {
            messageQueue.offer(message);
        }
    }
}
