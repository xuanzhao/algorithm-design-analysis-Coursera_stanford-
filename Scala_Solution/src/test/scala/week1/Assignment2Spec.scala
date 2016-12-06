package week1

/**
  * Created by ken on 12/5/2016 AD.
  */
import org.scalatest._
import utils.ReadFile
import week1.Assignment2._

class Assignment2Spec extends FlatSpec with Matchers {
  "Sort and Count Inversions" should "work for empty set" in {
    sortAndCount(Vector()) shouldEqual (Vector[Int](), 0)
  }
  "Sort and Count Inversions" should "work for an one-element set" in {
    sortAndCount(Vector(1)) shouldEqual (Vector[Int](1), 0)
  }
  "Sort and Count Inversions" should "work for a two element set without inversion" in {
    sortAndCount(Vector(1, 2)) shouldEqual (Vector[Int](1, 2), 0)
  }
  "Sort and Count Inversions" should "work for a two element set with inversion" in {
    sortAndCount(Vector(2, 1)) shouldEqual (Vector[Int](1, 2), 1)
  }
  "Sort and Count Inversions" should "work for a {1, 3, 2} set" in {
    sortAndCount(Vector(1, 3, 2)) shouldEqual (Vector[Int](1, 2, 3), 1)
  }
  "Sort and Count Inversions" should "work for a {3, 1, 2} set" in {
    sortAndCount(Vector(3, 1, 2)) shouldEqual (Vector[Int](1, 2, 3), 2)
  }
  "Sort and Count Inversions" should "work for a {3, 2, 1} set" in {
    sortAndCount(Vector(3, 2, 1)) shouldEqual (Vector[Int](1, 2, 3), 3)
  }

//  val path = getClass.getResource("/IntegerArray.txt").getPath
//  val bigSet =
//    scala.io.Source.fromFile(path).getLines.foldLeft(Vector[Int]()) {
//      (acc, elem) ⇒
//        acc :+ elem.toInt
//    }
  //  val bigSet = scala.io.Source.fromURI(path).getLines.foldLeft(Vector[Int]()){
  //  (acc, elem) => acc:+ elem.toInt
  //  }

  //  "Sort and Count Inversions" should "work for a big set" in {
  //    sortAndCount(bigSet) shouldEqual (bigSet.sorted, 2407905288L)
  //  }

  val bigArray = ReadFile.readResourceFile("/IntegerArray.txt").map(_.toInt)

  "Sort and Count Inversions" should "work for a big set" in {
    sortAndCount(bigArray.toVector) shouldEqual (bigArray.sorted, 2407905288L)
  }

}
