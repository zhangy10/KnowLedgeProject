package com.cluster;

import com.ReadFile;
import com.core.MatchProcessor;
import com.core.ProcessorType;
import com.core.SentimentProcessor;
import com.search.SearchType;

import mpi.Comm;
import mpi.MPI;

/**
 * As this project is a time-cost data analysis project which will deal with big
 * data set to do the exact string search and fuzzy string search tasks, this
 * MPJSpartan provides a cluster environment that can allow this project execute
 * onto the Spartan Cluster server for improving the performance of the system.
 * 
 * There are 2 different ways to test this environment. One is to set up the MPJ
 * express local environment variables and using the Eclipse (preferred) to
 * execute with the vm arguments, -jar ${MPJ_HOME}/lib/starter.jar -np 8. The 8
 * means there are 8 processors simultaneously to do the same searching task.
 * 
 * Another way is to upload the code onto the Spartan server and using the
 * script file (node_1_8.sh) provided under this folder to run this. Due to
 * package compiling issues, all of the codes should be refactor and moved into
 * the default package, otherwise the ClassNotFoundException will occur during
 * the compiling process.
 * 
 * @author Yu Zhang
 * 
 *         MPJSpartan.java
 * 
 *         Apr 16, 2016
 *
 */
public class MPJSpartan {
    /*
     * There are 3 different options defined in the SearchType class, the Exact
     * searching, the Local Approximated searching and the Global approach.
     */
    public static final SearchType tag = SearchType.LOCAL_APPROX;
    private static long runningTime = 0;

    static {
        runningTime = System.currentTimeMillis();
    }

    public static void main(String[] args) throws Exception {
        MPI.Init(args);
        Comm com = MPI.COMM_WORLD;
        int currentRank = com.Rank();
        int taskSize = com.Size();
        // Processing the sentiment part for each reviews.
        String result = multiProcess(ReadFile.REVIEW_SIZE, taskSize, tag,
                ProcessorType.SENTIMENT);
        String callBack = messaging(currentRank, taskSize, com, result);
        if (currentRank == MPJHelper.MAIN_PROCESSOR) {
            System.out.println(callBack);
        }
        // Processing the string matching part for each film title.
        result = multiProcess(ReadFile.TITLE_SIZE, taskSize, tag,
                ProcessorType.MATCH);
        callBack = messaging(currentRank, taskSize, com, result);

        if (currentRank == MPJHelper.MAIN_PROCESSOR) {
            ReadFile.getInstance().write(callBack, ReadFile.OUTPUT_PATH);
            System.out.println(callBack);
        }
        MPI.COMM_WORLD.Barrier();
        MPI.Finalize();
    }

    /**
     * Distributed the file list to each processor.
     * 
     * @param fileSize
     * @param taskSize
     * @param tag
     * @param type
     * @return
     */
    public static String multiProcess(int fileSize, int taskSize,
            SearchType tag, ProcessorType type) {
        int subLength = fileSize / taskSize;
        int remainLength = fileSize % taskSize;
        for (int i = 0; i < taskSize; i++) {
            int start = i * subLength;
            int end = (i == taskSize - 1 ? subLength + remainLength : subLength)
                    + start;
            switch (type) {
                case MATCH:
                    return new MatchProcessor(i, start, end, tag).runTask();
                case SENTIMENT:
                    return new SentimentProcessor(i, start, end, tag).runTask();
                default:
                    break;
            }
        }
        return null;
    }

    /**
     * Sending message to master processor or receiving message from each
     * processor.
     * 
     * @param rank
     * @param taskSize
     * @param com
     * @param result
     * @return
     * @throws Exception
     */
    public static String messaging(int rank, int taskSize, Comm com,
            String result) throws Exception {
        String msg = null;
        MPJHelper pr = MPJHelper.getInstance();
        if (rank != MPJHelper.MAIN_PROCESSOR) {
            pr.IsendMessage(result, com);
        }
        else {
            StringBuilder sb = new StringBuilder();
            sb.append(result);
            sb.append(pr.gatherMessages(taskSize - 1, com));
            sb.append("MPJ Time: " + getRunTime(runningTime) + "s");
            msg = sb.toString();
        }
        return msg;
    }

    public static float getRunTime(long startTime) {
        return (float) (System.currentTimeMillis() - startTime) / 1000;
    }
}
