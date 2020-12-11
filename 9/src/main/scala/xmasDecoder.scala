package xmasDecoder

import scala.io.Source

object xmasDecoder {
  // def main(args: Array[String]): Unit = {
  //   println(findNotASum("./src/main/scala/xmasInput.txt", 25))
  // }

  def isASumOfPreviousN(value: Long, sumsWindow: Set[Long]): Boolean = sumsWindow.contains(value)

  def makeWindow(input: List[Long], startIndex: Int, windowSize: Int): Set[Long] = {
    val sumsLists = List.range(startIndex, startIndex + windowSize).map(index => {
      val subList: List[Long] = input.slice(index, startIndex + windowSize)
      subList.tail.map(_ + subList.head)
    })
    sumsLists.reverse.tail.flatten.toSet
  }

  def parseInput(filePath: String): List[Long] = {
    return Source.fromFile(filePath).getLines.toList.map(_.toLong)
  }

  def findNotASum(inputFilePath: String, windowSize: Int): Long = {
    val xmasInput: List[Long] = parseInput(inputFilePath)
    val faultyValue: Option[(Long, Int)] = xmasInput.drop(windowSize).zipWithIndex.find(numberAndIndex =>
      !isASumOfPreviousN(numberAndIndex._1, makeWindow(xmasInput, numberAndIndex._2, windowSize))
    )
    faultyValue match {
      case Some(value) => value._1
      case None => 0
    }
  }
}
