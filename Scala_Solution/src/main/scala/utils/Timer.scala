package utils

/**
  * Created by ken on 12/4/2016 AD.
  */
object Timer {
  def time[R](block: => R): R = {
    val t0 = System.nanoTime()
    val result = block
    println("Elapsed time: " + (System.nanoTime() - t0) + "ns")
    result
  }
}
