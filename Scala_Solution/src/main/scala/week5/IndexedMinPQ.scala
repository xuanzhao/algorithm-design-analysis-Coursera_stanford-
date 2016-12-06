package week5

import scala.reflect.ClassTag

/**
  * Created by ken on 12/6/2016 AD.
  */
class IndexedMinPQ[A](n: Int, lessThan: (A, A) => Boolean)(implicit m: ClassTag[A]) {

  // position of item in the queue
  val positions: Array[Int] = Array.fill(n + 2)(-1)

  // the key of each item, i.e. key[1] is the key of item 1
  val keys = new Array[A](n+1)

  // min pq of items
  val pq: Array[Int] = Array.fill(n +1 )(0)

  var size = 0

  // Indicate whether PQ contains i-th item or not
  def isContain(i: Int): Boolean = {
    positions(i) != -1
  }

  /**
    * Insert i-th item to pq with given key
    */
  def insert(i: Int, key: A): Unit = {
    keys(i) = key
    size += 1
    pq(size) = i
    positions(i) = size

    swim(size)
  }

  def extractMin: Int = {
    // item in the first position (root)
    val res = pq(1)
    // swap with the last item
    swap(1, size)
    //last item is no longer in PQ
    positions(pq(size)) = -1
    size -= 1
    //maintain heap property
    sink(1)

    res
  }

  def delete(i: Int): Unit = {
    if (isContain(i)) {
      val oldPosition = positions(i)
      swap(positions(i), size)
      //last item is no longer in PQ
      positions(pq(size)) = -1
      size -= 1

      sink(oldPosition)
      swim(oldPosition)
    }
  }

  def isEmpty: Boolean = size == 0

  private def swim(position: Int): Unit = {
    var k = position
    while (k > 1 && lessThan(keys(pq(k)), keys(pq(k / 2)))) {
      swap(k , k/ 2)
      k = k /2
    }
  }

  private def swap(position1: Int, position2: Int): Unit = {
    // for example :  pq(1) = 3, pq(2) = 4
    //                position(3) = 1, position(4) = 2
    // after swap :   pq(1) = 4, pq(2) = 3
    //                position(4) = 1, position(3) = 2
    // keep tract of the position
    positions(pq(position1)) = position2
    positions(pq(position2)) = position1

    // swap item
    val temp  = pq(position1)
    pq(position1) = pq(position2)
    pq(position2) = temp
  }

  private def sink(position: Int): Unit = {
    var minPosition = position
    if (2 * position <= size &&
      lessThan(keys(pq(2 * position)), keys(pq(minPosition)))) {
      minPosition = 2 * position
    }
    if (2 * position + 1 <= size &&
      lessThan(keys(pq(2 * position + 1)), keys(pq(minPosition)))) {
      minPosition = 2 * position + 1
    }

    if (position != minPosition) {
      swap(position, minPosition)
      sink(minPosition)
    }
  }

  override def toString: String = {
    pq.tail.take(size).mkString(", ") + "\nPositions: " + positions.tail.mkString(", ") + "\n"
  }
}
