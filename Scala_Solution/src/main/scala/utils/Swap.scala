package utils

/**
  * Created by ken on 12/4/2016 AD.
  */
object Swap {
  def swap(arr: Array[Int], i: Int, j: Int): Unit = {
    val tmp = arr(i)
    arr(i) = arr(j)
    arr(j) = tmp
  }
}
