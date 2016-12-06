package week1

import scala.annotation.tailrec

/**
  * Created by ken on 12/5/2016 AD.
  *
  * This implement use Vector which is an immutable indexedSeq from Scala.
  */
object Assignment2 {

  type result = (Vector[Int], Long)  // create a data structure

  def sortAndCount(xs: Vector[Int]): result = {
    if (xs.length <= 1) (xs, 0)
    else {
      val (leftVec, rightVec) = xs splitAt (xs.length / 2)
      mergeAndCount(sortAndCount(leftVec), sortAndCount(rightVec))
    }
  }

  def mergeAndCount(xs: result, ys: result): result = {

    @tailrec
    def looper(xs: result, ys: result, acc: result): result = {
      (xs, ys, acc) match {
        case ((Vector(), xsCount), (ysVec, ysCount), (accVec, accCount)) =>
          (accVec ++ ysVec, accCount + xsCount + ysCount)
        case ((xsVec, xsCount), (Vector(), ysCount), (accVec, accCount)) =>
          (accVec ++ xsVec, accCount + xsCount + ysCount)
        case ((x +: xtail, xsCount),
              (y +: ytail, ysCount),
              (accVec, accCount)) =>
          if (x <= y) {
            looper((xtail, xsCount), ys, (accVec :+ x, accCount))
          } else {
            looper(xs,
                   (ytail, ysCount),
                   (accVec :+ y, accCount + xtail.length + 1))
          }
      }
    }

    looper(xs, ys, (Vector(), 0))
  }

  def main(args: Array[String]): Unit = {
    val input = scala.io.Source
      .fromFile("IntegerArray00.txt")
      .getLines()
      .foldLeft(Vector[Int]())((acc, elem) => acc :+ elem.toInt)
    val result = sortAndCount(input)
    println(s"Correctly sorted? ${result._1 == input.sorted}")
    println(s"Number of inversion? ${result._2}")
  }
}
