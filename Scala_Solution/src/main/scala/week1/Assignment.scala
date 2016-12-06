package week1

import scala.annotation.tailrec

/**
  * Created by ken on 12/5/2016 AD.
  *
  * This implement use Array which is a mutable indexedSeq from Java.
  *
  * You could cast your array into a sequence.The array will be implicitly converted to a WrappedArray.
  * And as the type is Seq, update operations will no longer be available.
  * val s: Seq[Int] = Array(1,2,3,4)
  * s: Seq[Int] = WrappedArray(1, 2, 3, 4)
  *
  * You also use Vector which is an immutable indexedSeq from Scala.
  * val s: Seq[Int] = Array(1,2,3,4).toVector
  * s: Seq[Int] = Vector(1, 2, 3, 4)
  *
  * The interfaces in Scala have the same name and different package to distinguish with regards to
  * immutability: Seq, immutable.Seq, mutable.Seq.
  * default, Seq implicitly be converted to immutable.Seq.
  */
object Assignment {

  def sortAndCount(array: Array[Int]): (Array[Int], Long) = {
    if (array.size <= 1) return (array, 0)
    else {
      val (leftArr, rightArr) = array splitAt (array.length / 2)
      val (leftSorted, leftInversionNum) = sortAndCount(leftArr)
      val (rightSorted, rightInversionNum) = sortAndCount(rightArr)
      val (mergedArr, splitInversionNum) =
        mergeAndCount(leftSorted, rightSorted)
      (mergedArr, splitInversionNum + leftInversionNum + rightInversionNum)
    }
  }

  def mergeAndCount(left: Array[Int], right: Array[Int]): (Array[Int], Long) = {
    val mergedArr = Array.fill(left.length + right.length)(0)

    @tailrec
    def looper(lIdx: Int, rIdx: Int, curIdx: Int, count: Long): Long = {
      val isLeftDown = lIdx == left.length
      val isRightDown = rIdx == right.length

      if (isLeftDown && isRightDown) count
      else if (isLeftDown) {
        mergedArr(curIdx) = right(rIdx)
        looper(lIdx, rIdx + 1, curIdx + 1, count)
      } else if (isRightDown || left(lIdx) <= right(rIdx)) {
        mergedArr(curIdx) = left(lIdx)
        looper(lIdx + 1, rIdx, curIdx + 1, count)
      } else {
        mergedArr(curIdx) = right(rIdx)
        looper(lIdx, rIdx + 1, curIdx + 1, count + (left.length - lIdx))
      }
    }

    (mergedArr, looper(0, 0, 0, 0))
  }

  def main(args: Array[String]) {
    val nums = scala.io.Source
      .fromFile("IntegerArray.txt")
      .getLines()
      .toArray
      .map(_.toInt)
    val (sortedArray, inversionNum) = sortAndCount(nums)
    println(s"number of inversions is $inversionNum")
  }
}
