package week4

import scala.collection.mutable.ArrayBuffer

/**
  * Created by ken on 12/5/2016 AD.
  */
object Assignment {

  /**
    *
    * @param numV number of Vertices
    * @param adjLists array of adjacency lists
    */
  case class DirectedGraph(numV: Int, adjLists: Array[ArrayBuffer[Int]]) {

    def reverseGraph: DirectedGraph = {
      val adjRevLists: Array[ArrayBuffer[Int]] =
        Array.fill(numV + 1)(ArrayBuffer())

      for {
        u <- 1 to numV
        v <- adjLists(u)
      } { adjRevLists(v) += u }

      DirectedGraph(numV, adjRevLists)
    }
  }

  /**
    * read from a file and build a directed graph data structure
    *
    * @param numV
    * @param fileName  in the file , each line like this "7 1"
    * @return  DirectedGraph
    */
  def buildGraph(numV: Int, fileName: String): DirectedGraph = {
    val adjLists: Array[ArrayBuffer[Int]] = Array.fill(numV + 1)(ArrayBuffer())

    for {
      line <- scala.io.Source.fromFile(fileName).getLines()
      vertices = line.split("\\s+").map(_.toInt)
      (u, v) = (vertices(0), vertices(1))
    } {
      adjLists(u) += v
    }

    DirectedGraph(numV, adjLists)
  }

  /**
    * SCC algorithm first step:
    * Compute the order by which the second pass of DFS use iterate.
    * First reverse the graph, then use DFS to compute the finished time of each vertex.
    *
    * @param G
    * @return order of vertices by finished time, the index is finished time
    */
  def computeOrder(G: DirectedGraph): Array[Int] = {

    // finished time
    val ft: Array[Int] = new Array(G.numV + 1)
    val isExplored: Array[Boolean] = Array.fill(G.numV + 1)(false)

    // current time
    var currentFt = 0

    val reversedG = G.reverseGraph
    for (i <- 1 to reversedG.numV) {
      if (!isExplored(i))
        dfs(reversedG, i)
    }

    def dfs(G: DirectedGraph, v: Int): Unit = {
      isExplored(v) = true
      for (u <- G.adjLists(v)) {
        if (!isExplored(u)) {
          dfs(G, u)
        }
      }
      // increase current time and set vertex finished time
      currentFt += 1
      ft(currentFt) = v
    }

    // return result
    ft
  }

  /**
    * SCCs algorithm second step:
    * compute SCCs for given directed graph
    *
    * @param G
    * @return Array of leader of each SCCs
    */
  def computeSCCs(G: DirectedGraph): Array[Int] = {

    // compute the "magic number"
    val order = computeOrder(G)
    val isExplored = Array.fill(G.numV + 1)(false)
    val leaders = Array.fill(G.numV + 1)(0)

    // current leader using the DFS loop
    var currentLeader = 0

    for {
      i <- G.numV to 1 by -1
      u = order(i)
      if (!isExplored(u))   // filter has already explored vertex
    } {
      currentLeader = u
      dfs(G, u)
    }

    def dfs(G: DirectedGraph, v: Int): Unit = {
      isExplored(v) = true

      for (u <- G.adjLists(v)) {
        if (!isExplored(u))
          dfs(G, u)
      }

      // set vertex v in current group
      leaders(v) = currentLeader
    }

    // return result
    leaders
  }

  def topSCCs(leaders: Array[Int], n: Int): Array[Int] = {
    val result = Array(n)
    val bin = Array.fill(leaders.length)(0)

    for (ld <- leaders) {
      bin(ld) += 1
    }

    // sort bin with descend then return top n SCCs
    bin.tail.sortWith(_ > _).take(n)
  }

}
