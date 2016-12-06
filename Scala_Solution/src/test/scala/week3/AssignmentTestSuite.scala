package week3

/**
  * Created by ken on 12/5/2016 AD.
  */
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

import scala.util.Random
import week3.Assignment._


@RunWith(classOf[JUnitRunner])
class AssignmentTestSuite extends FunSuite{
  test("Union find") {
    val uf = new UF(5)
    uf.union(1, 2)
    uf.union(2, 5)
    uf.union(3, 4)

    assert(uf.find(1, 5))
    assert(!uf.find(2, 3))
  }

  test("Union find 2") {
    val N = 100
    val uf = new UF(N)

    for (i <- 1 to 99) {
      uf.union(i, i + 1)
    }

    for (i <- 1 to 213) {
      assert(uf.find(Random.nextInt(N) + 1, Random.nextInt(N) + 1))
    }

  }

  test("Build graph") {
    val G = buildGraph(getClass.getResource("/MinCut1.txt").getPath)

    assert(G.numV === 8)
    assert(G.numE === 14)
  }

  trait TestSet1 {
//    val filename = "MinCut1.txt"
    val filename = getClass.getResource("/MinCut1.txt").getPath
    println(filename)
  }

  trait TestSet2 {
    val filename = getClass.getResource("/MinCut2.txt").getPath
  }

  trait TestSet3 {
    val filename = getClass.getResource("/MinCut3.txt").getPath
  }

  trait TestSet4 {
    val filename = getClass.getResource("/MinCut4.txt").getPath
  }

  test("test 1") {
    new TestSet1 {
      assert(minCut(buildGraph(filename)) === 2)
    }
  }

  test("test 2") {
    new TestSet2 {
      assert(minCut(buildGraph(filename)) === 2)
    }
  }

  test("test 3") {
    new TestSet3 {
      assert(minCut(buildGraph(filename)) === 1)
    }
  }

  test("test 4") {
    new TestSet4 {
      assert(minCut(buildGraph(filename)) === 3)
    }
  }
}
