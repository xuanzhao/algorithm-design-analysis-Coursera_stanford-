package week3

/**
  * Created by ken on 12/5/2016 AD.
  *
  * This implement use Union Find data structure to speedy up the process of vertices merging.
  * Although This Graph data structure is Array which is a mutable,
  * this implement use "G.edges.toList" to copy a graph for each iteration of the algorithm.
  */
object Assignment {

  case class Graph(numV: Int, numE: Int, edges: Array[(Int, Int)]) {
    override def toString: String = edges.mkString(", ")
  }

  def buildGraph(fileName: String): Graph = {

    // get an Array which is each elem is a string (one line, like "1 2 3", "2 1 3 4")
    val adjLists = (
      for (line <- scala.io.Source.fromFile(fileName).getLines())
        yield line
    ).toArray

    val numV = adjLists.length

    // construct set of edges, no parallel edges
    val setEdges = (
      for {
        line <- adjLists
        vertices = line.split("\\s+").map(_.toInt) // vertices = [1,2,3]
        u = vertices.head // u = 1
        v <- vertices.tail // v = [2, 3]
      } yield {
        if (u < v) (u, v) // yield [(1,2), (1,3)]
        else (v, u)
      }
    ).toSet

    Graph(numV, setEdges.size, setEdges.toArray)
  }

  def minCut(G: Graph): Int = {

    def iterate(): Int = {
      val uf = new UF(G.numV)

      // Karger's algorithm iterate
      var numV = G.numV
      // each time get a copy of edges and shuffle the edges list
      val edges = util.Random.shuffle(G.edges.toList)
//      val edges = G.edges.toList

      // edge merge
      for ((u, v) <- edges) {
        if (numV > 2)
          if (!uf.find(u, v)) {
            uf.union(u, v)
            numV -= 1
          }
      }

      // count number of edges between two vertices
      edges.foldLeft(0) {
        case (acc, (u, v)) =>
          if (!uf.find(u, v)) {
            acc + 1
          } else acc
      }
    }

    // number of interation
    val numIterate = G.numV * G.numV
    println("Number of iteration " + numIterate)

    // run the algorithm numIterate times and determine the minimum cut
    (1 to numIterate).foldLeft(Int.MaxValue)((min, _) => {
      val result = iterate()
      if (result < min) result
      else min
    })

  }

  def main(args: Array[String]): Unit = {
    val res = minCut(buildGraph("KargerMinCut00.txt"))
    println(res)
  }
}
