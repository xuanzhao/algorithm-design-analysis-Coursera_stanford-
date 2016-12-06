package week5

import org.scalatest.FunSuite
/**
  * Created by ken on 12/6/2016 AD.
  */
class AssignmentTestSuite extends FunSuite {

  test("test 1") {
    val filePath = getClass.getResource("/dijkstraTest.txt").getPath
    val G = graph.buildGraph(filePath, 8)

    assert(Assignment.computeSP(G, 1) === Array(0, 1, 2, 3, 4, 4, 3, 2))
  }

}
