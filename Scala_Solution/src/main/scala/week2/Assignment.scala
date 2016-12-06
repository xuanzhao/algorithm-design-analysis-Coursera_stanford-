package week2

import scala.annotation.tailrec

/**
  * Created by ken on 12/5/2016 AD.
  *
  * implement quick sort using mutable Array.
  */
object Assignment {

  type Result = (Array[Int], Long)

  def firstElem = (array: Array[Int], l: Int, r: Int) => l
  def lastElem = (array: Array[Int], l: Int, r: Int) => r
  def medianElem = (array: Array[Int], l: Int, r: Int) => {
    val first = array(l)
    val last = array(r)
    val mid = array((r - l) + l)
    val max = Math.max(Math.max(first, mid), last)
    val min = Math.min(Math.min(first, mid), last)
    if (first > min && first < max) l
    else if (mid > min && mid < max) r
    else array.indexOf(mid)
  }

  def quickSort(array: Array[Int], pivot: (Array[Int],Int, Int) => Int): Result = {

    def swap(idx1: Int, idx2: Int): Unit = {
      val temp = array(idx1)
      array(idx1) = array(idx2)
      array(idx2) = temp
    }

    def partition(left: Int, right: Int, piv: Int): Int = {
      val pivot = array(piv)
      swap(left, piv)
      var i = left + 1
      for (j <- left + 1 to right) {
        if (array(j) < pivot) {
          swap(i, j)
          i += 1
        }
      }
      swap(left, i - 1)
      i - 1
    }

    def looper(left: Int, right: Int): Long = {
      if (right <= left) 0L
      else {
        val pivotIdx = pivot(array, left, right)
        val partIdx = partition(left, right, pivotIdx)
        (right - left) + looper(left, partIdx - 1) + looper(partIdx + 1, right)
      }
    }

    (array, looper(0, array.length - 1))
  }

  @tailrec
  def isSorted(array: Array[Int]): Boolean = {
    (array.length == 1) || ((array.head <= array.tail.head) && isSorted(
      array.tail))
  }

  def main(args: Array[String]): Unit = {

    val input = scala.io.Source
      .fromFile("QuickSort.txt")
      .getLines()
      .foldLeft(Array[Int]()) { (acc, elem) =>
        acc :+ elem.toInt
      }

    val pivots = List(firstElem, lastElem, medianElem)

    for (pivot <- pivots) {
      quickSort(input.clone(), pivot) match {
        case (array, comparision) =>
          println(s"Correctly sorted? ${isSorted(array)}")
//          println(array.toList)
          println(s"number of comparisions? ${comparision}")
      }
    }
  }

}
