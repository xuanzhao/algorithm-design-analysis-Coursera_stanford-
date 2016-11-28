/******************************************************************************
 * Programming Assignment #4.1
 *
 * Download the following text file:
 * SCC.txt
 *
 * The file contains the edges of a directed graph. Vertices are labeled as
 * positive integers from 1 to 875714. Every row indicates an edge, the vertex
 * label in first column is the tail and the vertex label in second column is
 * the head (recall the graph is directed, and the edges are directed from the
 * first column vertex to the second column vertex). So for example, the 11th
 * row looks like : "2 47646". This just means that the vertex with label 2 has
 * an outgoing edge to the vertex with label 47646
 *
 * Your task is to code up the algorithm from the video lectures for computing
 * strongly connected components (SCCs), and to run this algorithm on the given
 * graph.
 *
 * Output Format: You should output the sizes of the 5 largest SCCs in the given
 * graph, in decreasing order of sizes, separated by commas (avoid any spaces).
 * So if your algorithm computes the sizes of the five largest SCCs to be 500,
 * 400, 300, 200 and 100, then your answer should be "500,400,300,200,100"
 * (without the quotes). If your algorithm finds less than 5 SCCs, then write 0
 * for the remaining terms. Thus, if your algorithm computes only 3 SCCs whose
 * sizes are 400, 300, and 100, then your answer should be "400,300,100,0,0"
 * (without the quotes). (Note also that your answer should not have any spaces
 * in it.)
 *
 * WARNING: This is the most challenging programming assignment of the course.
 * Because of the size of the graph you may have to manage memory carefully. The
 * best way to do this depends on your programming language and environment, and
 * we strongly suggest that you exchange tips for doing this on the discussion
 * forums.
 *
 *****************************************************************************/
package lec4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by ken on 11/27/2016 AD.
 * The arguments tab has a text box Vm arguments, enter -Xss50m.
 * <p>
 * Notice that get adjList will lose the vertex which has no out degree,
 * for this reason. adjList.size() != number of vertices.
 * So, exploredFinishedTime = new int[adjList.size()] will lose some vextex.
 * After first round DFS, we need order the exploredFinishedTime, So we set that type is "TreeMap"
 * which could implement comparator interface to order the vextices by value (explore finished time).
 * Another way could use a Set to store all of the vertex, then use size() get the number.
 * <p>
 * <p>
 * % java -Xss50m Question1 SCC.txt
 */
public class SCCAssignment {

    static class Graph {

        private HashMap<Integer, ArrayList<Integer>> SCCs;
        private HashMap<Integer, ArrayList<Integer>> adjListRev;
        private HashMap<Integer, ArrayList<Integer>> adjList;
        private TreeMap<Integer, Integer> exploredFinishedTime = new TreeMap<>();
        private int curTime = 0;
        private int source;


        private void printGraph() {
            for (HashMap.Entry<Integer, ArrayList<Integer>> entry : adjList.entrySet()) {
                System.out.println("keys :" + entry.getKey() + "\tvalues :" + entry.getValue());
            }
        }

        private void printGraphRev() {
            for (HashMap.Entry<Integer, ArrayList<Integer>> entry : adjListRev.entrySet()) {
                System.out.println("keys :" + entry.getKey() + "\tvalues :" + entry.getValue());
            }
        }

        private void getAdjList(String file) throws FileNotFoundException {
            adjList = new HashMap<Integer, ArrayList<Integer>>();
            Scanner in = new Scanner(new File(file));
            while (in.hasNextLine()) {
                String[] line = in.nextLine().trim().split("\\s+");
                int tailV = Integer.parseInt(line[0]);
                int headV = Integer.parseInt(line[1]);
                if (!adjList.containsKey(tailV)) {
                    adjList.put(tailV, new ArrayList<>());
                    adjList.get(tailV).add(headV);
                } else {
                    adjList.get(tailV).add(headV);
                }
            }
        }

        private void getAdjListRev() {
            adjListRev = new HashMap<Integer, ArrayList<Integer>>();

            for (Integer key : adjList.keySet()) {
                ArrayList<Integer> adj_vs = adjList.get(key);
                for (Integer vex : adj_vs) {
                    if (!adjListRev.containsKey(vex)) {
                        adjListRev.put(vex, new ArrayList<>());
                        adjListRev.get(vex).add(key);
                    } else {
                        adjListRev.get(vex).add(key);
                    }
                }
            }
        }

        private void DFS_loop1() {
            HashMap<Integer, Boolean> explored_loop1 = new HashMap<>();

            for (Integer key : adjListRev.keySet()) {
                explored_loop1.putIfAbsent(key, false);
                if (explored_loop1.get(key) == false) {
                    DFS1(key, explored_loop1);
                }
            }
        }

        private void DFS1(Integer key, HashMap<Integer, Boolean> explored_loop1) {
            explored_loop1.put(key, true);

            // not like python, if adjListRev.get(key) == null, will throw null point exception
            if (adjListRev.containsKey(key)) {
                for (Integer v : adjListRev.get(key)) {
                    explored_loop1.putIfAbsent(v, false);
                    if (explored_loop1.get(v) == false) {
                        DFS1(v, explored_loop1);
                    }
                }

            }
            exploredFinishedTime.put(key, curTime++);
        }

        private TreeMap<Integer, Integer> getSortedExploredTime() {
//            HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

            // notice that this comparator need pass a HashMap.
            ValueComparator bvc = new ValueComparator(exploredFinishedTime);
            TreeMap<Integer, Integer> sortedExploredTime = new TreeMap<>(bvc);
            sortedExploredTime.putAll(exploredFinishedTime);
            return sortedExploredTime;
        }

        private void DFS_loop2() {
            HashMap<Integer, Boolean> explored_loop2 = new HashMap<>();
            TreeMap<Integer, Integer> sortedExploredTime = getSortedExploredTime();
            for (Integer key : sortedExploredTime.keySet()) {
                explored_loop2.putIfAbsent(key, false);
                if (!explored_loop2.get(key)) {
                    source = key;
                    SCCs.put(key, new ArrayList<>());
                    SCCs.get(key).add(key);
                    DFS2(key, explored_loop2);
                }
            }
//            for (int i = adjList.size(); i > 0; i--) {
//                explored_loop2.putIfAbsent(sortedFinishedTime[i], false);
//                if (!explored_loop2.get(sortedFinishedTime[i])) {
//                    SCCs.put(i, 0);
//                    DFS2(i);
//                }
//            }
        }

        private void DFS2(Integer key, HashMap<Integer, Boolean> explored_loop2) {
            explored_loop2.put(key, true);

            // not like python, if adjListRev.get(key) == null, will throw null point exception
            if (adjList.containsKey(key)) {
                for (Integer v : adjList.get(key)) {
                    explored_loop2.putIfAbsent(v, false);
                    if (!explored_loop2.get(v)) {
//                    SCCs.put(v, new ArrayList<>());
                        SCCs.get(source).add(v);
                        DFS2(v, explored_loop2);
                    }
                }
            }
//            sccSize += 1;
        }

        private void calcSCCs() {
            SCCs = new HashMap<>();

            // first run DFS on adjListRev, to found out the DFS search order in second DFS run on adjList
            DFS_loop1();

            // second run DFS on adjList, to found out all of the SCCs in the graph.
            DFS_loop2();
        }

        private int[] getSCCsSizeInOrder() {
            ArrayList<Integer> sizeSCCs = new ArrayList<>();

            for (Integer key : SCCs.keySet()) {
                sizeSCCs.add(SCCs.get(key).size());
            }

            sizeSCCs.sort(Collections.reverseOrder());

//            int[] top5 = new int[5];
//            for (int i = 0; i < 5; i++) {
//                top5[i] = sizeSCCs.get(i);
//            }
//            return top5;

//            Arrays.sort(new int[][]{sizeSCCs}, Collections.reverseOrder());
//            Arrays.sort(new int[][]{sizeSCCs}, new MyCompator());

            Iterator iter = sizeSCCs.iterator();
            int[] top5 = new int[5];
            for (int i = 0; i < 5; i++) {
                if (iter.hasNext()) {
                    top5[i] = (int) iter.next();
                }
            }
            return top5;
//            return sizeSCCs.toArray(new Integer[sizeSCCs.size()]);

        }

        private class MyCompator implements Comparator {
            @Override
            public int compare(Object o1, Object o2) {
                int val1 = (int) o1;
                int val2 = (int) o2;
                if (val1 != val2) {
                    return val1 > val2 ? -1 : 1;
                }
                return 0;
            }
        }

        private class ValueComparator implements Comparator<Integer> {

            Map<Integer, Integer> base;

            public ValueComparator(Map<Integer, Integer> base) {
                this.base = base;
            }

            @Override
            public int compare(Integer a, Integer b) {
                if (base.get(a) >= base.get(b)) {
                    return -1;
                } else {
                    return 1;
                }
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Graph g = new Graph();
        g.getAdjList("SCC.txt");
        g.getAdjListRev();
        g.calcSCCs();
        System.out.println("the top 5 SCC is :");
        //g.getSCCsSizeInOrder();
        int[] orderedSCCs = g.getSCCsSizeInOrder();
        for (int i = 0; i < 5; i++) {
            System.out.println(orderedSCCs[i]);
        }
    }
}
