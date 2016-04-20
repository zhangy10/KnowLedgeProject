# KnowLedgeProject

=======
SUMMARY
=======
Basically, the Maindriver class is the main method for this knowledge technology project. In this project, matching between a list of film titles and a list of film reviews is key task. Meanwhile, it will deal with the simple sentiment analysis to decide how goodness each review is for each film title.

This project can be executed by using either Java command line or the well-known IDE (Eclipse) as a simple Java Project. In particular, two of the multi-thread environments will be provided to improve the performance of doing huge string searching tasks, the simple Java multi-thread environment and the cluster environment whose the execution script can be found in the com/cluster/ folder. More detail regarding usage can be found in the below.

====================
DIRECTORY STRUCUTURE
====================
com/

MainDriver.java        - This is the main driver for this project.

ReadFile.java          - For reading file and extracting the file name of each file.

SentimentUtils.java    - The resources for sentiment analysis.

StringEngine.java      - For starting to execute the String matching task by given options in the multi-thread environment.


com/beans/

MatchKey.java          - A data structure for presenting matched results.

Review.java            - Each film review structure.


com/cluster/

MPJHelper.java         - Being used to communicate with each processor based on the MPJ express library.

MPJSpartan.java        - Provideing a cluster environment that can allow this project execute onto the Spartan Cluster server.

node_1_8.sh            - Running script.

com/core/

MatchProcessor.java    - For processing string matching tasks.

Processor.java         - Provideing a general processing template.

ProcessorType.java     - Provideing 2 process types.

SentimentProcessor.java- For processing the Sentiment Identification tasks.


com/distance/

Distance.java          - For representing the implementation of different Edit distance algorithms.

GlobalDistance.java    - The implementation of the Global Edit Distance algorithm based on the NeedlemanWunsch.

LocalDistance.java     - The implementation of the Local Edit Distance algorithm based on the SmithWaterman.


com/search/

ApproxSearch.java      - For representing Approximate searching approaches.

ExactSearch.java       - Exact searching approach by using regular expression.

Search.java            - A super Search class as an interface.

SearchType.java        - Defineing 3 different approaches to deal with the String matching tasks, Excat, Local and Global.


=====
USAGE
=====








