package joltageChainer

import scala.io.Source

object joltageChainer {
  // def main(args: Array[String]): Unit = {
  //   println(countJoltageDifferences("./src/main/scala/joltageChain.txt"))
  // }

  def getSortedAdapters(filePath: String): List[Int] = {
    return 0 :: Source.fromFile(filePath).getLines.toList.map(_.toInt).sorted
  }

  def countJoltageDifferences(inputFilePath: String): Int = {
    val sortedAdapters: List[Int] = getSortedAdapters(inputFilePath)
    val joltageDiffs: List[Int] = List.range(1, sortedAdapters.size).map(index => sortedAdapters(index) - sortedAdapters(index - 1))
    return joltageDiffs.filter(_ == 1).size * (joltageDiffs.filter(_ == 3).size + 1)
  }
}
