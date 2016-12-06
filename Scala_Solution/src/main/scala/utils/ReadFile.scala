package utils

import java.io.FileNotFoundException

/**
  * Created by ken on 12/5/2016 AD.
  */
object ReadFile {
  def readResourceFile(p: String): List[String] =
    Option(getClass.getResourceAsStream(p)).map(scala.io.Source.fromInputStream)
      .map(_.getLines.toList)
      .getOrElse(throw new FileNotFoundException(p))
}
