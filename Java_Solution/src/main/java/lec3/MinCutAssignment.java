package lec3;

import javax.sql.rowset.Predicate;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by ken on 11/26/2016 AD.
 */
public class MinCutAssignment {

    static class Graph {

        private HashMap<Integer, ArrayList<Integer>> adjList;

        public Graph(String inputFileName) {
            adjList = new HashMap<>();
            try {
                Scanner in = new Scanner(new File(inputFileName));
                while (in.hasNextLine()) {
                    String[] line = in.nextLine().trim().split("\\s+");
                    int v = Integer.parseInt(line[0]);
                    ArrayList<Integer> list = new ArrayList<Integer>();
                    for (int i = 1; i< line.length; i++) {
                        int u = Integer.parseInt(line[i]);
                        list.add(u);
                    }
                    adjList.put(v, list);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        private static HashMap<Integer, ArrayList<Integer>> copyAdjList(HashMap<Integer, ArrayList<Integer>> adjList)
        {
            HashMap<Integer, ArrayList<Integer>> graphCopy = new HashMap<Integer, ArrayList<Integer>>();

            Iterator it = adjList.keySet().iterator();

            while(it.hasNext())
            {
                Integer currentKey = new Integer((Integer) it.next());
                ArrayList<Integer> currentItemList = adjList.get(currentKey);

                graphCopy.put(currentKey, new ArrayList<Integer>(currentItemList));
            }

            return graphCopy;
        }

        public int randomContract() {
//             notice that deep copy here is a huge trap...
//            HashMap<Integer, ArrayList<Integer>> cloneAdjList = new HashMap<>(this.adjList);
////            HashMap<Integer, ArrayList<Integer>> cloneAdjList = new HashMap<>();
////            cloneAdjList.putAll(this.adjList);
////            cloneAdjList = (HashMap<Integer, ArrayList<Integer>>) this.adjList.clone();
//            for (Integer key : this.adjList.keySet()) {
////                cloneAdjList.put(new Integer(key), (ArrayList<Integer>) this.adjList.get(key).clone());
//                cloneAdjList.put(new Integer(key), new ArrayList<>(this.adjList.get(key)));
//            }

            HashMap<Integer, ArrayList<Integer>> cloneAdjList = copyAdjList(this.adjList);

            Random generator = new Random();
            while (cloneAdjList.size() > 2) {
                ArrayList<Integer> keys = new ArrayList<>(cloneAdjList.keySet());
                int vecA = keys.get(generator.nextInt(keys.size()));
                int vecB = cloneAdjList.get(vecA).get(generator.nextInt(cloneAdjList.get(vecA).size()));

                ArrayList<Integer> linkToAList = cloneAdjList.get(vecA);
                ArrayList<Integer> linkToBList = cloneAdjList.get(vecB);

                linkToAList.addAll(linkToBList);

                linkToAList.removeIf(x -> (x == vecA || x == vecB));
                cloneAdjList.replace(vecA, linkToAList);
                cloneAdjList.remove(vecB);

                for (ArrayList<Integer> vs : cloneAdjList.values()) {
                    vs.forEach(v -> {if (v.intValue() == vecB) vs.set(vs.indexOf(v), vecA);});
//                    vs.replaceAll();
//                    for (Integer v : vs) {
//                        if (v.intValue() == vecB) {
//                            vs.set(vs.indexOf(v), vecA);
//                        }
//                    }
//                    for (int i = 0; i < vs.size(); i++) {
//                        if (vs.get(i) == vecB) {
//                            vs.set(i, vecA);
//                        }
//                    }
                }
            }
//            Object[] finalKeys = cloneAdjList.keySet().toArray();
//            return cloneAdjList.get(finalKeys[0]).size();
            return cloneAdjList.get(cloneAdjList.keySet().toArray()[0]).size();
        }

        public int findMinCut() {
            int ntrails = adjList.size();
            int minCut = Integer.MAX_VALUE;
            for (int i = 0; i < ntrails; i++) {
                int crossEdges = randomContract();
                if (crossEdges < minCut) {
                    minCut = crossEdges;
                }
            }
            return minCut;
        }
    }
    public static void main(String[] args) {
        Graph g = new Graph("kargerMinCut00.txt");
        System.out.println(g.findMinCut());
    }
}
