package com.beans;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.ReadFile;
import com.search.ExactSearch;
import com.search.SearchType;

/**
 * MatchKey is a data structure for presenting matched results in this system.
 * It contains a key and a map which is an one-to-many relationship for storing
 * multiple data records. For example, it will be used to represent each film
 * title in the system and each film title will be the key, while the map will
 * store matched reviews and their info.
 * 
 * @author Yu Zhang
 * 
 *         MatchKey.java
 * 
 *         Apr 16, 2016
 *
 */
public final class MatchKey {

    /*
     * The caller should provide which search type is preferred to do the match
     * task. More options are defined in the SearchType class.
     */
    private SearchType tag = SearchType.EXACT;
    /* The format for printing results */
    private static final String PRINT_TITLE = "#<%s>_Reviewed by:"
            + ReadFile.NEW_LINE;
    private static final String PRINT_NAME_FORMAT = "<%s> ";
    /* The key which will be treated as the searching key. */
    private String key;
    /* One-to-many relationship for recording matched reviews and their info */
    private Map<Review, List<String>> matchedInfo;

    public MatchKey(String title, SearchType tag) {
        this.key = title;
        this.tag = tag;
        matchedInfo = new HashMap<>();
    }

    public String getKey() {
        return key;
    }

    /**
     * Put matched review info into the map
     * 
     * @param fileName
     *            review's file name.
     * @param alias
     *            key's alias.
     */
    public void putAlias(Review fileName, List<String> alias) {
        if (alias == null || alias.size() == 0) {
            return;
        }
        if (matchedInfo.containsKey(fileName)) {
            matchedInfo.get(fileName).addAll(alias);
            return;
        }
        matchedInfo.put(fileName, alias);
    }

    /**
     * Formatted printing the matched result.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(PRINT_TITLE, key));
        Iterator<Entry<Review, List<String>>> iterator = matchedInfo.entrySet()
                .iterator();
        while (iterator.hasNext()) {
            Entry<Review, List<String>> entry = iterator.next();
            String printInfo = PRINT_NAME_FORMAT
                    + (tag == SearchType.EXACT ? ExactSearch.OUT_PUT : "");
            sb.append(String.format(printInfo, entry.getKey().toString()));
            for (int i = 0; i < entry.getValue().size(); i++) {
                if (i == entry.getValue().size() - 1) {
                    sb.append(entry.getValue().get(i));
                }
                else {
                    sb.append(entry.getValue().get(i) + ReadFile.SPLIT);
                }
            }
            sb.append(ReadFile.NEW_LINE);
        }
        return sb.toString();
    }
}
