package lec5;

import java.awt.image.AreaAveragingScaleFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;

/**
 * @author Ken91
 *
 * Input format:
 * The file contains an adjacency list representation of an undirected weighted
 * graph with 200 vertices labeled 1 to 200. Each row consists of the node
 * tuples that are adjacent to that particular vertex along with the length of
 * that edge. For example, the 6th row has 6 as the first entry indicating that
 * this row corresponds to the vertex labeled 6. The next entry of this row
 * "141,8200" indicates that there is an edge between vertex 6 and vertex 141
 * that has length 8200. The rest of the pairs of this row indicate the other
 * vertices adjacent to vertex 6 and the lengths of the corresponding edges.
 *
 * Task:
 * Run Dijkstra's shortest-path algorithm on this graph, using 1 (the first
 * vertex) as the source vertex, and to compute the shortest-path distances
 * between 1 and every other vertex of the graph. If there is no path between a
 * vertex v and vertex 1, we'll define the shortest-path distance between 1 and
 * v to be 1000000.
 *
 * Output format:
 * Report the shortest-path distances to the following ten vertices, in order:
 * 7,37,59,82,99,115,133,165,188,197. You should encode the distances as a
 * comma-separated string of integers. So if you find that all ten of these
 * vertices except 115 are at distance 1000 away from vertex 1 and 115 is 2000
 * distance away, then your answer should be 1000,1000,1000,1000,1000,2000,1000,
 * 1000,1000,1000.
 *
 * Time complexity: O(n m) - In the "heap" directory, there is an optimized
 * O(m log(n)) algorithm.
 */

public class Dijkstra {

    private HashMap<Integer, ArrayList<int[]>> vertices;
    HashMap<Integer, Integer> distanceToSource = new HashMap<Integer, Integer>();


    public Dijkstra(String file) throws FileNotFoundException {
        vertices = new HashMap<>();
        Scanner in = new Scanner(new File(file));
        while (in.hasNextLine()) {
            String[] line = in.nextLine().trim().split("\\s+");
            int vex = Integer.parseInt(line[0]);
            vertices.put(vex, new ArrayList<>());
            for (int i = 1; i < line.length; i++) {
                String[] vw = line[i].split(",");
                int[] edge = new int[2];
                edge[0] = Integer.parseInt(vw[0]);
                edge[1] = Integer.parseInt(vw[1]);
                vertices.get(vex).add(edge);
            }
        }
    }

    private int extractMinWeight(ArrayList<Integer> restVs) {
        int minum = Integer.MAX_VALUE;
        int minV = 0;
        for (Integer v : restVs) {
            distanceToSource.putIfAbsent(v, Integer.MAX_VALUE);
            if (distanceToSource.get(v) < minum) {
                minum = distanceToSource.get(v);
                minV = v;
            }
        }
        return minV;
    }

    private void relaxWeight(int u, int v, int w) {
        distanceToSource.putIfAbsent(u, Integer.MAX_VALUE);
        distanceToSource.putIfAbsent(v, Integer.MAX_VALUE);
        if (distanceToSource.get(v) > distanceToSource.get(u) + w) {
            distanceToSource.put(v, distanceToSource.get(u) + w);
        }
    }

    public void calcShortestPath() {
        ArrayList<Integer> visitedVs = new ArrayList<Integer>();
        ArrayList<Integer> restVs = new ArrayList<Integer>(vertices.keySet());

//        for (int i = 0; i< vertices.size(); i++) {
//            restVs.add(i);
//        }
//        Iterator iter = vertices.iterator();
//        while (iter.hasNext()) {
//            restVs.add((Integer) iter.next());
//        }
        distanceToSource.put(1, 0);

        while (restVs.size() > 0) {
            Integer u = extractMinWeight(restVs);

            for (int[] vs : vertices.get(u)) {
                int v = vs[0];
                int w = vs[1];
                relaxWeight(u, v, w);
            }
            visitedVs.add(u);
            restVs.remove(u);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Dijkstra dijkstra = new Dijkstra("dijkstraData00.txt");
        dijkstra.calcShortestPath();
//        System.out.println(dijkstra.distanceToSource);
        int[] target = new int[]{7,37,59,82,99,115,133,165,188,197};
        for (int i = 0; i < target.length; i++) {
            System.out.println(dijkstra.distanceToSource.get(target[i]));
        }

    }
}
