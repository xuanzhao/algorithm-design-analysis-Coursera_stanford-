package week2

/**
  * Created by ken on 12/5/2016 AD.
  */
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import week2.Assignment._


class QuickSortTestSuite extends FunSuite {

  trait TestSet1 {
    val array = util.Random.shuffle(1 to 1000).toArray
    def firstElem = (array: Array[Int], l: Int, r: Int) => l
  }

  trait TestSet2 {
    val array = util.Random.shuffle(1 to 100).toArray
    def firstElem = (array: Array[Int], l: Int, r: Int) => l
  }


  test("quick sort must correctly sort the array 1") {
    new TestSet2 {
      val comparisons = quickSort(array, firstElem)
      assert(array === (1 to 100).toArray)
    }

  }

  test("quick sort must correctly sort the array 2") {
    new TestSet1 {
      val comparisons = quickSort(array, firstElem)
      assert(array === (1 to 1000).toArray)
    }

  }


}