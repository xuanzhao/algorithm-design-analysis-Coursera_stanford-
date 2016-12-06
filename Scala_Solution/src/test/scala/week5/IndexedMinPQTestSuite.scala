package week5

import org.scalatest.FunSuite

/**
  * Created by ken on 12/6/2016 AD.
  */
class IndexedMinPQTestSuite extends FunSuite {


  test("test min index pq") {
    val pq = new IndexedMinPQ[Int](4, _ < _)
    pq.insert(1, 24)
    pq.insert(3, 25)
    pq.insert(4, 1)

    println(pq)

    println("Calling delete------")
    pq.delete(4)
    assert(!pq.isContain(4))
    println(pq)
  }

  test("test min index pq delete") {
    val pq = new IndexedMinPQ[Int](100, _ < _)
    for (i <- 1 to 100) pq.insert(i, util.Random.nextInt(100) + 100)

    pq.delete(50)
    pq.insert(50, 12)

    assert(pq.extractMin === 50)

    pq.delete(35)
    assert(!pq.isContain(35))

  }

  test("consecutive extract min calls should return a sorted array") {
    val pq = new IndexedMinPQ[Int](1000, _ < _)
    val shuffledArray = util.Random.shuffle(1 to 1000).toArray

    for (i <- shuffledArray) {
      pq.insert(i, i)
    }

    pq.delete(1000)

    for (i <- 1 to 999) {
      val min = pq.extractMin
      assert(min === i)
    }


  }
}
