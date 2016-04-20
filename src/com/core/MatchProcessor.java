package com.core;

import java.util.concurrent.BlockingQueue;

import com.ReadFile;
import com.beans.MatchKey;
import com.beans.Review;
import com.search.ApproxSearch;
import com.search.ExactSearch;
import com.search.SearchType;

/**
 * This is designed for processing string matching tasks.
 * 
 * @author Yu Zhang
 * 
 *         MatchProcessor.java
 * 
 *         Apr 16, 2016
 *
 */
public class MatchProcessor extends Processor {

    public MatchProcessor(int rank, int startPos, int total, SearchType tag) {
        super(rank, startPos, total, tag, null);
    }

    public MatchProcessor(int rank, int startPos, int total, SearchType tag,
            BlockingQueue<String> queue) {
        super(rank, startPos, total, tag, queue);
    }

    @Override
    public String runTask() {
        // Reading keys (film titles) in the given range.
        for (int i = startPos; i < total; i++) {
            MatchKey key = new MatchKey(ReadFile.getTitle(i), tag);
            // Traversing all of the review lists to find which review is belong
            // to which film title.
            for (int j = 0; j < ReadFile.REVIEW_SIZE; j++) {
                Review review = ReadFile.getReview(j);
                String text = ReadFile.getInstance()
                        .read(ReadFile.getReviewPath(review.getFileName()));
                switch (tag) {
                    case LOCAL_APPROX:
                    case GLOBAL_APPROX:
                        key.putAlias(review,
                                new ApproxSearch(text, key.getKey(), tag)
                                        .searchPattern());
                        break;
                    case EXACT:
                        key.putAlias(review, new ExactSearch(text, key.getKey())
                                .searchPattern());
                        break;
                    default:
                        break;
                }
            }
            keylist.add(key);
        }

        StringBuilder sb = new StringBuilder();
        for (MatchKey key : keylist) {
            sb.append(key.toString());
        }
        return sb.toString();
    }
}
