package com.cluster;

import mpi.Comm;
import mpi.MPI;

/**
 * MPJHelper will be used to communicate with each processor based on the MPJ
 * express library.
 * 
 * @author Yu Zhang
 * 
 *         MPJHelper.java
 * 
 *         Apr 16, 2016
 *
 */
public class MPJHelper {

    public static final int MAIN_PROCESSOR = 0;
    public static final int MESSAGE_TAG = 100;
    private static MPJHelper processors;

    public static synchronized MPJHelper getInstance() {
        if (processors == null) {
            processors = new MPJHelper();
        }
        return processors;
    }

    public void IsendMessage(Object msg, Comm com) throws Exception {
        // Send a message to the master processor
        Object[] sendObj = new Object[1];
        sendObj[0] = msg;
        com.Isend(sendObj, 0, sendObj.length, MPI.OBJECT, MAIN_PROCESSOR,
                MESSAGE_TAG);
    }

    public Object receiveMessage(Comm com) throws Exception {
        return receiveMessage(com, MPI.ANY_SOURCE);
    }

    public Object receiveMessage(Comm com, int source) throws Exception {
        Object[] receiveObj = new Object[1];
        com.Recv(receiveObj, 0, receiveObj.length, MPI.OBJECT, source,
                MESSAGE_TAG);
        return receiveObj[0];
    }

    public String gatherMessages(int size, Comm com) throws Exception {
        StringBuilder sb = new StringBuilder();
        while (size > 0) {
            Object msg = receiveMessage(com);
            if (msg != null) {
                sb.append((String) msg);
            }
            size--;
        }
        return sb.toString();
    }

}
