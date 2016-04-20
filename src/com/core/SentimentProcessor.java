package com.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import com.ReadFile;
import com.SentimentUtils;
import com.beans.Review;
import com.search.ApproxSearch;
import com.search.ExactSearch;
import com.search.SearchType;

/**
 * Desigend for processing the Sentiment Identification tasks.
 * 
 * @author Yu Zhang
 * 
 *         SentimentProcessor.java
 * 
 *         Apr 16, 2016
 *
 */
public class SentimentProcessor extends Processor {

    public SentimentProcessor(int rank, int startPos, int total,
            SearchType tag) {
        this(rank, startPos, total, tag, null);
    }

    public SentimentProcessor(int rank, int startPos, int total, SearchType tag,
            BlockingQueue<String> queue) {
        super(rank, startPos, total, tag, queue);
    }

    @Override
    public String runTask() {
        for (int i = startPos; i < total; i++) {
            Review review = ReadFile.getReview(i);
            String text = ReadFile.getInstance()
                    .read(ReadFile.getReviewPath(review.getFileName()));
            // Positive dataset Matching
            List<String> positives = sentimentProcess(SentimentUtils.POSITIVE,
                    text);
            // Negative dataset Matching
            List<String> negatives = sentimentProcess(SentimentUtils.NEGATIVE,
                    text);
            // If the size of the positive words is greater than that of the
            // negative words, the review will be updated as the "Positvie" for
            // the film title.
            if (positives.size() != 0 || negatives.size() != 0) {
                if (positives.size() > negatives.size()) {
                    review.setGoodness(SentimentUtils.TAG_GOOD);
                }
                else if (positives.size() < negatives.size()) {
                    review.setGoodness(SentimentUtils.TAG_BAD);
                }
                else {
                    review.setGoodness(SentimentUtils.TAG_MIDDLE);
                }
            }
        }
        return EMPTY_MSG;
    }

    private List<String> sentimentProcess(String[] basecase, String text) {
        List<String> list = new ArrayList<>();
        for (int j = 0; j < basecase.length; j++) {
            String key = basecase[j];
            List<String> result = search(key, text);
            if (result != null && result.size() != 0) {
                list.addAll(result);
            }
        }
        return list;
    }

    private List<String> search(String key, String text) {
        switch (tag) {
            case LOCAL_APPROX:
            case GLOBAL_APPROX:
                return new ApproxSearch(text, key, tag).searchPattern();
            case EXACT:
                return new ExactSearch(text, key).searchPattern();
            default:
                break;
        }
        return null;
    }
}
