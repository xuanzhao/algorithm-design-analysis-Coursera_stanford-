package week6
import utils.Util._

import scala.collection.mutable.PriorityQueue

/**
  * Created by ken on 12/6/2016 AD.
  *
  * The goal of this problem is to implement the "Median Maintenance" algorithm.
  * The text file contains a list of the integers from 1 to 10000 in unsorted order; you should treat this as a stream
  * of numbers, arriving one by one.
  * Letting xi denote the ith number of the file, the kth median mk is defined as the median of the numbers x1,…,xk.
  * (So, if k is odd, then mk is ((k+1)/2)th smallest number among x1,…,xk; if k is even, then mk is the (k/2)th
  * smallest number among x1,…,xk.)
  * In the box below you should type the sum of these 10000 medians, modulo 10000 (i.e., only the last 4 digits).
  * That is, you should compute (m1+m2+m3+⋯+m10000)mod10000
  */

object Median {

  def main(args: Array[String]): Unit = {
    val array = readArrayFromFIle[Int]("/Users/ken/Downloads/algorithm-design-analysis(Coursera_stanford)/Scala_Solution/src/main/resources/Median.txt")
    val low = PriorityQueue[Int]()
    val high = PriorityQueue[Int]().reverse
    var sum = 0
    for (i <- array.indices) {
      if ((low.size + high.size) % 2== 0) low += array(i)
      else high += array(i)
      if (high.nonEmpty && low.head > high.head) {
        val tmp = low.dequeue()
        low += high.dequeue()
        high += tmp
      }
      sum += low.head
    }
    println(sum % 10000)
    println(low.head == 5000)
  }
}
