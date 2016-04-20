package com;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Comparator;

import com.beans.Review;

/**
 * This class is designed for reading file and extracting the file name of each
 * file.
 * 
 * The data-set folder path should be updated before running this project only
 * if the the target path is changed or updated.
 * 
 * @author Yu Zhang
 * 
 *         ReadFile.java
 * 
 *         Apr 16, 2016
 *
 */
public class ReadFile {

    // public static final String FOLDER_PATH =
    // "/home/subjects/comp90049/submission/zhangy10/";
    // public static final String FOLDER_PATH =
    // "/Users/zhangyu/Desktop/KTData/";

    /* The absolute data-set folder path. */
    public static final String FOLDER_PATH = "/Users/zhangyu/Desktop/TestData/";
    public static final String TITLE_PATH = FOLDER_PATH + "film_titles.txt";
    public static final String REVIEW_PATH = FOLDER_PATH + "revs/";
    public static final String OUTPUT_PATH = FOLDER_PATH + "result.txt";

    private static final Review[] REVIEWS;
    private static final String[] FILM_TITLES;
    public static final int TITLE_SIZE;
    public static final int REVIEW_SIZE;
    public static final String FILE_SUFFIX = ".txt";
    public static final String FILE_ALIAS = "(";
    public static final String SPLIT = ";";
    public static final String NEW_LINE = "\n";

    private static final String FILE_FORMAT = "UTF-8";
    private static int buffedSize = 1024;
    private static ReadFile instance;

    static {
        // Read all of the film titles.
        FILM_TITLES = ReadFile.getInstance().read(TITLE_PATH).split(SPLIT);
        TITLE_SIZE = FILM_TITLES.length;
        // Read all of the film reviews' filenames filtering by the file suffix
        // as a few unknown files (e.g. .DS_Store on Mac OS) will appear in the
        // folder on different OS.
        String[] list = new File(REVIEW_PATH).list(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String filename) {
                return filename.endsWith(FILE_SUFFIX);
            }
        });
        // Sorting reviews' file names by ascending order.
        Arrays.sort(list, new Comparator<String>() {

            @Override
            public int compare(String s1, String s2) {
                return ReadFile.parseFileName(s1) - ReadFile.parseFileName(s2);
            }
        });
        Review[] reviews = new Review[list.length];
        for (int i = 0; i < list.length; i++) {
            reviews[i] = new Review(list[i]);
        }
        REVIEWS = reviews;
        REVIEW_SIZE = REVIEWS.length;
    }

    /**
     * Convert the String file name to an integer for sorting.
     * 
     * @param fileName
     * @return
     */
    private static int parseFileName(String fileName) {
        String tempStr = "";
        if (fileName.contains(FILE_ALIAS)) {
            tempStr = fileName.substring(0, fileName.indexOf(FILE_ALIAS))
                    .trim();
        }
        else {
            tempStr = fileName.substring(0, fileName.indexOf(FILE_SUFFIX));
        }

        if (tempStr.length() == 0 || "".equals(tempStr)) {
            return 0;
        }
        return Integer.parseInt(tempStr);
    }

    public static synchronized ReadFile getInstance() {
        if (instance == null) {
            instance = new ReadFile();
        }
        return instance;
    }

    public static String getReviewPath(String fileName) {
        return REVIEW_PATH + fileName;
    }

    public static Review getReview(int pos) {
        return REVIEWS[pos];
    }

    public static String getTitle(int pos) {
        return FILM_TITLES[pos];
    }

    /**
     * Read a file by the given file path. Returning a String object.
     * 
     * @param path
     * @return
     */
    public String read(String path) {
        BufferedReader br = null;
        StringBuilder sr = new StringBuilder();

        try {
            br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(path), FILE_FORMAT), buffedSize);
            String line = null;
            while ((line = br.readLine()) != null) {
                sr.append(line);
                sr.append(SPLIT);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (br != null) {
                try {
                    br.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sr.toString();
    }

    /**
     * Writing the output into a given file.
     * 
     * @param text
     * @param path
     */
    public void write(String text, String path) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(path), FILE_FORMAT), buffedSize);
            bw.write(text);
            bw.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (bw != null) {
                try {
                    bw.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
