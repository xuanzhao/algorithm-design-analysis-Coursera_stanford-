import scala.collection.immutable.HashMap

val s: Seq[Int] = Array(1,2,3,4).toVector

val vertex = s.head
val verStr = s.tail

val edges = verStr map (dest => (vertex, dest))


List(2,3) +: List(4,1)

5 +: List(2,3) :+ 4

val parent: Array[Int] = new Array[Int](3).zipWithIndex.map { case (_, index) => index }
parent.toList

for (line <- s) yield line+1

for (i <- 5 to 1 by -1) yield  i

val hash = s.map(x => x -> x).toMap

s.par.map(_ + 42)
