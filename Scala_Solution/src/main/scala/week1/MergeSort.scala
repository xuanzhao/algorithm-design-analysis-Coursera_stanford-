package week1

import utils.Util
/**
  * Created by ken on 12/4/2016 AD.
  */
object MergeSort {

  def sort(arr: Array[Int]): Array[Int] = {
    if (arr.length <= 1) arr
    else {
      val mid = arr.length / 2
      val leftArr = sort(arr.slice(0, mid))
      val rightArr = sort(arr.slice(mid, arr.length))
      merge(leftArr, rightArr)
    }
//    else {
//      val mid = arr.length / 2
//      val (leftArr, rightArr) = arr splitAt mid
//      merge(sort(leftArr), sort(rightArr))
//    }

  }

  private def merge(leftArr: Array[Int], rightArr: Array[Int]): Array[Int] = (leftArr, rightArr) match {
    case (Array(), rightArr) => rightArr
    case (leftArr, Array()) => leftArr
    case (Array(n1, _*), Array(n2, _*)) =>
      if (n1 < n2) n1 +: merge(leftArr.tail, rightArr)
      else n2 +: merge(leftArr, rightArr.tail)
  }
}

object Test extends App{

  println(MergeSort.sort(Array(2,1,5,4,3)).mkString(","))

//  def main(args: Array[String]): Unit = {
//    import Util._
//    val array = readArrayFromFIle[Int]("IntegerArray.txt")
//    println(count(array))
//  }
}
