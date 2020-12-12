package joltageChainer

import scala.io.Source
import scala.collection.mutable.HashMap

object joltageChainerStep2 {
  def main(args: Array[String]): Unit = {
    println(countChainCombinations("./src/main/scala/joltageChain.txt"))
  }

  def computeWindowCombinations(window: List[Int]): Long = {
    val combinationsFromJoltage: HashMap[Int, Long] = HashMap(window.last -> 1)
    def computeWindowCombinationsRec(currentIndex: Int): Long = {
      val currentJoltage: Int = window(currentIndex)
      if (combinationsFromJoltage.contains(currentJoltage)) combinationsFromJoltage(currentJoltage)
      else {
        val combinationsFromCurrentIndex: Long = List.range(1,4).foldLeft(0.toLong)((acc, indexShift) => acc + {
          if (currentIndex + indexShift >= window.size || window(currentIndex + indexShift) - currentJoltage > 3) 0.toLong
          else computeWindowCombinationsRec(currentIndex + indexShift)
        })
        combinationsFromJoltage(currentJoltage) = combinationsFromCurrentIndex
        combinationsFromCurrentIndex
      }
    }
    computeWindowCombinationsRec(0)
  }

  def isolateWindows(adapters: List[Int]): List[List[Int]] = {
    var window: List[Int] = List()
    var windowsList: List[List[Int]] = List()
    var previousJoltage = 0
    for (adapterJoltage <- adapters) {
      if (adapterJoltage - previousJoltage == 3) {
        windowsList ::= window.sorted
        window = List(adapterJoltage)
      } else window ::= adapterJoltage
      previousJoltage = adapterJoltage
    }
    window :: windowsList
  }

  def countChainCombinations(inputFilePath: String): Long = {
    val sortedAdapters: List[Int] = joltageChainer.getSortedAdapters(inputFilePath)
    return isolateWindows(sortedAdapters).foldLeft(1.toLong)((acc, window) => acc * computeWindowCombinations(window))
  }
}
