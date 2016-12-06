package week1

import org.scalatest.FunSuite

/**
  * Created by ken on 12/4/2016 AD.
  */
class MergeSortSuite extends FunSuite{
  test("An increasing array should not be changed"){
    assert(MergeSort.sort(Array(1,2,3,4,5)) === Array(1,2,3,4,5))
  }

  test("A decreasing array should be reversed") {
    assert(MergeSort.sort(Array(5,4,3,2,1)) === Array(1,2,3,4,5))
  }

  test("An empty array should not be changed") {
    assert(MergeSort.sort(Array()) === Array())
  }


}
