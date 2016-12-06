package week3

import scala.annotation.tailrec
import scala.util.Random

/**
  * Created by ken on 12/6/2016 AD.
  *
  * Because this Graph data structure use Vector which is an immutable indexed seq.
  * So, for each iteration, the graph in randomizedContract will create a new graph,
  * By this way, it implicitly copy the original graph from one loop of the algorithm.
  *
  * This implement is FP style and scala style.
  */
object Assignment1 {

  case class Graph(
      vertices: Vector[Set[Int]],
      edges: Vector[(Int, Int)]
  )

  def loadGraph(fileName: String): Graph = {
    parseGraph(scala.io.Source.fromFile(fileName).mkString)
  }

  // the graph like this "1 2 3"
  def parseGraph(graphStr: String): Graph = {
    graphStr.lines.foldLeft(Graph(Vector(), Vector())) { (graph, inputLine) =>
      // get a Vector(1,2,3)
      val list =
        inputLine.split(Array(' ', '\t', ',')).foldLeft(Vector[Int]()) {
          (v, vertexStr) =>
            v :+ vertexStr.toInt
        }
      // vertex = 1, type is Int
      val vertex = list.head
      // adjecency = Vector(2,3)
      val adjecency = list.tail
      // edges = Vector((1,2), (1,3))
      val edges = adjecency map { destVertex =>
        (vertex, destVertex)
      }
      Graph(graph.vertices :+ Set(vertex), graph.edges ++ edges)
    }
  }

  @tailrec
  def randomizedContract(graph: Graph)(implicit rng: Random): Graph = {
    if (graph.vertices.size <= 2)
      graph
    else {
      //pick an edge at random
      val (v1, v2) = graph.edges(rng.nextInt(graph.edges.size))
      val v1Idx = graph.vertices.indexWhere(_ contains v1)
      val v2Idx = graph.vertices.indexWhere(_ contains v2)

      // contract v1 and v2 into a single vertex, here is a Set(v1, v2)
      val contractedVertex = graph.vertices(v1Idx) ++ graph.vertices(v2Idx)

      // merge two vertices
      // replace v1Idx -> Set(v1Idx, v2Idx)
      // delete element in the Vector with index of v2Idx
      val newVertices = graph.vertices.updated(v1Idx, contractedVertex).patch(from = v2Idx, patch = Nil, replaced= 1)

      // Filter out any loops, which is v1 -> v2 or v2 -> v1
      val newEdges = graph.edges.filterNot {
        case (from, to) => (contractedVertex contains(from)) && (contractedVertex contains(to))
      }

      // construct a new graph and iterate
      randomizedContract(Graph(newVertices, newEdges))
    }
  }

  def countCuts[T](graph: Graph): Int = {
    graph.edges count{
      case (x, y) => (graph.vertices.head contains(x)) & !(graph.vertices.head contains(y))
    }
  }

  def calculateMinCuts(graph: Graph, debug: Boolean = false): Int = {
    val n = graph.vertices.size
    val iterations = (Math.pow(n, 2) * Math.log(n)).toInt
    if (debug) println(s"Looking for min cut in {$iterations} iterations")

    implicit val rng = new Random()
    var minCuts = Int.MaxValue

    for (iter <- 0 until iterations) {
      rng.setSeed(iter)
      if (debug & (iter % 100 == 0))
        println(s"Iteration #{$iter}...")
      val cuts = countCuts(randomizedContract(graph))
      if (cuts < minCuts) {
        minCuts = cuts
        if (debug)
          println(s"Found fewer cuts (${minCuts}) in iteration #{$iter}")
      }
    }
    minCuts
  }

  def main(args: Array[String]): Unit = {
    val fileName = if (args.isEmpty) "kargerMinCut00.txt" else args.head
    val input = loadGraph(fileName)
    val minCuts = calculateMinCuts(input, debug = true)
    println(s"Min Cuts = ${minCuts}")
  }

}
