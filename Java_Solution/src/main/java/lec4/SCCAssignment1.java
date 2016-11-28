package lec4;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Input format:
 * The input file contains the edges of a directed graph. Vertices are labeled
 * as positive integers from 1 to 875714. Every row indicates an edge, the
 * vertex label in first column is the tail and the vertex label in second
 * column is the head (recall the graph is directed, and the edges are directed
 * from the first column vertex to the second column vertex). So for example,
 * the 11th row looks like : "2 47646". This just means that the vertex with
 * label 2 has an outgoing edge to the vertex with label 47646.
 * <p>
 * Task:
 * The task is to code up the Kosaraju's Two-Pass algorithm for computing
 * strongly connected components (SCCs), and to run this algorithm on the given
 * graph.
 * <p>
 * Output Format:
 * You should output the sizes of the 5 largest SCCs in the given graph, in
 * decreasing order of sizes, separated by commas (avoid any spaces). So if
 * your algorithm computes the sizes of the five largest SCCs to be 500, 400,
 * 300, 200 and 100, then your answer should be "500,400,300,200,100". If your
 * algorithm finds less than 5 SCCs, then write 0 for the remaining terms. Thus,
 * if your algorithm computes only 3 SCCs whose sizes are 400, 300, and 100,
 * then your answer should be "400,300,100,0,0".
 * <p>
 * Time complexity: O(m + n)
 * <p>
 * Set VM arguments in eclipse: -Xmx1024M -Xss10m
 * <p>
 * This version of solution is bit trick, because it is supposed that all of the vertex number
 * is from 1, and is consecutive. So, the graph information is stored in a array.
 */


public class SCCAssignment1 {

    static class Graph {

        private ArrayList<ArrayList<Integer>> adjList;
        private ArrayList<ArrayList<Integer>> adjListRev;
        private int curTime;
        private boolean[] exploredVs;
        private int[] exploredFinishedTime;
        private int[] SizeOfSCCs;
        private int source;

        public Graph(String inputFileName) throws FileNotFoundException {
            Scanner in = new Scanner(new File(inputFileName));
            adjList = new ArrayList<ArrayList<Integer>>();
            adjListRev = new ArrayList<ArrayList<Integer>>();

            while (in.hasNext()) {
                int tail = in.nextInt();
                int head = in.nextInt();
                int max = Math.max(tail, head);
                while (adjList.size() < max) {
                    adjList.add(new ArrayList<Integer>());
                    adjListRev.add(new ArrayList<Integer>());
                }
                adjList.get(tail - 1).add(head - 1);
                adjListRev.get(head - 1).add(tail - 1);
            }
        }

        private void DFSLoop1() {
            curTime = 0;
            exploredVs = new boolean[adjListRev.size()];
            exploredFinishedTime = new int[adjList.size()];

            for (int i = 0; i < adjListRev.size(); i++) {
                if (exploredVs[i] == false) {
                    DFS1(i);
                }
            }
        }

        private void DFS1(Integer key) {
            exploredVs[key] = true;

            for (Integer head : adjListRev.get(key)) {
                if (exploredVs[head] == false) {
                    DFS1(head);
                }
            }
            exploredFinishedTime[curTime] = key;
            curTime += 1;
        }

        private void DFSLoop2() {
            exploredVs = new boolean[adjList.size()];
            SizeOfSCCs = new int[adjList.size()];

            for (int time = exploredFinishedTime.length-1; time >= 0; time--) {
                int key = exploredFinishedTime[time];
                if (exploredVs[key] == false) {
                    source = key;
                    DFS2(key);
                }
            }
        }

        private void DFS2(int key) {
            exploredVs[key] = true;
            SizeOfSCCs[source] += 1;

            for (int head : adjList.get(key)) {
                if (exploredVs[head] == false) {
                    DFS2(head);
                }
            }
        }

        public int[] computeSCC() {
            int[] top5 = new int[5];
            DFSLoop1();
            DFSLoop2();
            Arrays.sort(SizeOfSCCs);
            for (int i = 0; i < 5; i++) {
                top5[i] = SizeOfSCCs[SizeOfSCCs.length - 1 - i];
            }
            return top5;
        }
    }


    public static void main(String[] args) throws FileNotFoundException {
        Graph g = new Graph("SCC.txt");
        int[] topSCCs = g.computeSCC();
        for (int n : topSCCs) {
            System.out.println(n + "  ");
        }
    }
}
