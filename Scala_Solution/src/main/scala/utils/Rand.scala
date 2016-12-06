package utils

/**
  * Created by ken on 12/4/2016 AD.
  */
object Rand {
  var random: scala.util.Random = null
  def reinitRand(): Unit ={
    random = new scala.util.Random((System.currentTimeMillis()))
  }
  reinitRand()
  def r = random.nextInt
  def r(p: Int) = random.nextInt(p)
}
