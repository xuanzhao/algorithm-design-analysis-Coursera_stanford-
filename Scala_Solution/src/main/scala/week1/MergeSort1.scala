package week1

/**
  * Created by ken on 12/4/2016 AD.
  *
  * this implement is from "Programming in scala".
  */
object MergeSort1 {

  def msort[T](less: (T, T) => Boolean)(xs: List[T]): List[T] = {

    def merge(xs: List[T], ys: List[T]): List[T] = {
      (xs, ys) match {
        case (Nil, _) => ys
        case (_, Nil) => xs
        case (x :: xs1, y :: ys1) =>
          if (less(x, y)) x :: merge(xs1, ys)
          else y :: merge(xs, ys1)
      }
    }
    val mid = xs.length / 2
    if (mid == 0) xs
    else {
      val (ys, zs) = xs splitAt mid
      merge(msort(less)(ys), msort(less)(zs))
    }
  }

}
