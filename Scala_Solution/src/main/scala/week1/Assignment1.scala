package week1

/**
  * Created by ken on 12/5/2016 AD.
  *
  * This implement use List which is a immutable linearSeq from Scala.
  * Because this implement is not tail recursive, so it will be overflow.
  *
  */
object Assignment1 {

  type result = (Vector[Int], Long)

  def sortAndCount(list: List[Int]): (List[Int], Long) = {
    if (list.size <= 1) (list, 0)
    else {
      val (leftArr, rightArr) = list splitAt (list.length / 2)
      val (leftSorted, leftInversionNum) = sortAndCount(leftArr)
      val (rightSorted, rightInversionNum) = sortAndCount(rightArr)
      val (mergedArr, splitInversionNum) =
        mergeAndCount(leftSorted, rightSorted)
      (mergedArr, splitInversionNum + leftInversionNum + rightInversionNum)
    }
  }

  def mergeAndCount(left: List[Int], right: List[Int]): (List[Int], Long) = {

    def looper(left: List[Int], right: List[Int]): (List[Int], Long) = {
      (left, right) match {
        case (Nil, _) => (right, 0)
        case (_, Nil) => (left, 0)
        case (x :: xs1, y :: ys1) =>
          if (x <= y) {
            val (tail, count) = looper(xs1, right)
            (x :: tail, count)
          } else {
            val (tail, count) = looper(left, ys1)
            (y :: tail, count + xs1.length + 1)
          }
      }

    }

    (looper(left, right))
  }

  def main(args: Array[String]) {
    val nums = scala.io.Source
      .fromFile("IntegerArray.txt")
      .getLines()
      .toList
      .map(_.toInt)
    val (sortedArray, inversionNum) = sortAndCount(nums)
    println(s"correctly sorted? ${sortedArray == nums.sorted}")
    println(s"number of inversions is $inversionNum")
  }
}
