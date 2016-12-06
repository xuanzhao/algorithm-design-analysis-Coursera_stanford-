package week1

/**
  * Created by ken on 12/5/2016 AD.
  *
  * This implement uses List with generic type
  */
object Assignment3 {

  def sortAndCount[T](xs: List[T])(
      implicit ord: Ordering[T]): (List[T], Long) = {

    if (xs.length <= 1) (xs, 0)
    else {
      val (leftLst, rightLst) = xs splitAt (xs.length / 2)
      val (sortedLeftLst, leftInversionNum) = sortAndCount(leftLst)
      val (sortedRightLst, rightInversionNum) = sortAndCount(rightLst)
      val (mergedLst, splitInversionNum) =
        mergeAndCount(sortedLeftLst, sortedRightLst)(ord)

      (mergedLst, leftInversionNum + rightInversionNum + splitInversionNum)
    }
  }

  def mergeAndCount[T](xs: List[T], ys: List[T])(
      implicit ord: Ordering[T]): (List[T], Long) = {
    (xs, ys) match {
      case (Nil, ys) => (ys, 0)
      case (xs, Nil) => (xs, 0)
      case (x :: xtail, y :: ytail) =>
        if (ord.lt(x, y)) {
          val (tail, count) = mergeAndCount(xtail, ys)
          (x :: tail, count)
        } else {
          val (tail, count) = mergeAndCount(xs, ytail)
          (y :: tail, count + xtail.length + 1)
        }
    }
  }

  def main(args: Array[String]): Unit = {
    val input = scala.io.Source
      .fromFile("IntegerArray00.txt")
      .getLines()
      .toList
      .map(_.toInt)
    val (sortedList, inversionNum) = sortAndCount(input)
    println(s"correctly sorted? ${sortedList == input.sorted}")
    println(s"number of inversion is ${inversionNum}")
  }

}
