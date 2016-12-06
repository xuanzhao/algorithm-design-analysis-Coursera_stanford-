package week5
import week5.graph._
/**
  * Created by ken on 12/6/2016 AD.
  */
object Assignment {

  /**
    * Compute the shortest path of undirected graph using Dijkstra algorithm
    *
    * @param G Unidrected Weighted Graph
    * @param source vertex
    * @return Array of shortest path of all vertices from 1 to G.numV
    */
  def computeSP(G: UndirectedWeightedGraph, source: Int): Array[Int] = {
    val pq = new IndexedMinPQ[Int](G.numV, _ < _)

    // initial value
    val shortestPath: Array[Int] = Array.fill(G.numV + 1)(Int.MaxValue)
    shortestPath(source) = 0
    pq.insert(source, 0)

    // Dijkstra's algorithm
    while (!pq.isEmpty) {
      // Choose the one with minimum distance to source
      val u = pq.extractMin
      for (e <- G.adjLists(u)) {
        val v = e.other(u)
        if (shortestPath(v) > shortestPath(u) + e.weight) {
          shortestPath(v) = shortestPath(u) + e.weight
          if (!pq.isContain(v)) {
            pq.insert(v, shortestPath(v))
          } else {
            pq.delete(v)
            pq.insert(v, shortestPath(v))
          }
        }
      }
    }
    shortestPath.tail
  }

}
