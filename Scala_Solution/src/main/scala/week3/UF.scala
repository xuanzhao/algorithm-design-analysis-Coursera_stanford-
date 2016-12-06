package week3

/**
  * Created by ken on 12/5/2016 AD.
  *
  * Union Find data structure, nodes are start from 1 to n.
  */
class UF(n: Int) {

  // parent of each node, initial value is itself
  val parent = new Array[Int](n+1).zipWithIndex.map {case(_,index) => index}

  // rank of each node, with initial value 1
  val rank = Array.fill(n + 1)(1)

  /**
    * Union two given nodes
    *
    *@param u first node
    *@param v second node
    */
  def union(u: Int, v: Int): Unit = {
    if (u > n || u <=  0) throw new IndexOutOfBoundsException
    if (v > n || v <= 0) throw new IndexOutOfBoundsException

    val ru = root(u)
    val rv = root(v)

    if (rank(u) >= rank(v)) {
      parent(rv) = ru
      rank(ru) = Math.max(rank(ru), rank(rv)+1)
    } else {
      parent(ru) = rv
      rank(rv) = Math.max(rank(rv), rank(ru)+1)
    }
  }

  /**
    * Check if two nodes are joint
    *
    * @param u first node
    * @param v second node
    * @return true if two node is in the same set, false otherwise.
    */
  def find(u: Int, v: Int): Boolean = {
    if (u > n || u <=  0) throw new IndexOutOfBoundsException
    if (v > n || v <= 0) throw new IndexOutOfBoundsException

    root(u) == root(v)
  }


  def root(u: Int): Int = {
    if (u > n || u <= 0) throw  new IndexOutOfBoundsException

    var i = u
    while (parent(i) != i) {
      i = parent(i)
    }
    i
  }
}
