package week5

import scala.collection.mutable.ArrayBuffer

/**
  * Created by ken on 12/6/2016 AD.
  */
package object graph {

  case class UndirectedWeightedGraph(
      numV: Int,
      adjLists: Array[ArrayBuffer[WeightedEdge]]) {
    override def toString: String = {
      for ((adj, vertex) <- adjLists.zipWithIndex.tail)
        yield
          vertex + ": " + adj.map {
            case WeightedEdge(u, v, weighted) => (v, weighted)
          }.mkString(", ")
    }.mkString("\n")
  }

  case class WeightedEdge(u: Int, v: Int, weight: Int) {

    def other(x: Int): Int = {
      if (x != u && x != v) throw new IllegalArgumentException
      if (x == u) v
      else u
    }
  }

  def buildGraph(fileName: String, numV: Int): UndirectedWeightedGraph = {
    val adjLists: Array[ArrayBuffer[WeightedEdge]] =
      Array.fill(numV + 1)(ArrayBuffer())

    for {
      line <- scala.io.Source.fromFile(fileName).getLines()
      rawLine = line.split("\\s+")
      u = rawLine.head.toInt
      edge <- rawLine.tail.map { r =>
        val xs = r.split(",").map(_.toInt)
        WeightedEdge(u, xs(0), xs(1))
      }
    } {
      adjLists(u) += edge
    }
    UndirectedWeightedGraph(numV, adjLists)
  }
}
