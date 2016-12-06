package week3

import org.scalatest.{FlatSpec, Matchers}
import week3.Assignment1._

/**
  * Created by ken on 12/6/2016 AD.
  */
class Assignment1TestSpec extends FlatSpec with Matchers{

  def runTest(setup: String) = {
    calculateMinCuts(parseGraph(setup), debug = false)
  }

  "Ranomized contraction" should "work for 'two x-ed boxes' connected with two edges" in {
    runTest("""1 2 6 5
              |2 1 5 6 3
              |3 2 7 8 4
              |4 3 7 8
              |5 1 2 6
              |6 5 1 2 7
              |7 6 3 4 8
              |8 7 3 4""".stripMargin('|')) shouldEqual(2)
  }


  "Ranomized contraction" should "work for 'three x-ed boxes' graph" in {
    runTest("""1 2 6 5
              |2 1 5 6 3 7
              |3 2 7 8 4 6
              |4 3 7 8
              |5 1 2 6
              |6 5 1 2 7 3
              |7 6 3 4 8 2
              |8 7 3 4""".stripMargin('|')) shouldEqual 3
  }

  "Ranomized contraction" should "work for tree graph" in {
    runTest("""1 2 3
              |2 1 4 5
              |3 1 6 7
              |4 2
              |5 2
              |6 3
              |7 3""".stripMargin('|')) shouldEqual 1
  }

  "Ranomized contraction" should "work for pyramid graph" in {
    runTest("""1 2 3
              |2 1 4 5 3
              |3 1 6 7 2
              |4 2 5
              |5 2 6 4
              |6 3 5 7
              |7 3 6""".stripMargin('|')) shouldEqual 2
  }
}
