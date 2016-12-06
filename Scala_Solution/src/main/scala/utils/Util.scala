package utils

import scala.io.Source
import scala.reflect.ClassTag

/**
  * Created by ken on 12/4/2016 AD.
  */

object Util {
  implicit def stringToInt(s: String): Int = java.lang.Integer.parseInt(s)

  implicit def stringToLong(s: String): Long = java.lang.Long.parseLong(s)

  def readArrayFromFIle[T: ClassTag](fileName:String)(implicit convert: String => T): Array[T] =
    Source.fromFile(fileName).getLines().map(x => convert(x)).toArray
}
