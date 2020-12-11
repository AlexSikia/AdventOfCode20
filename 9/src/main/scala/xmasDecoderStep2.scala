package xmasDecoder

import scala.io.Source

object xmasDecoderStep2 {
  def main(args: Array[String]): Unit = {
    val faultyValue: Long = xmasDecoder.findNotASum("./src/main/scala/xmasInput.txt", 25)
    println("Answer to step1: ", faultyValue)
    println("Answer to step2: ", sumOfFaultyBlock("./src/main/scala/xmasInput.txt", faultyValue))
  }

  def isASumOfPreviousN(value: Long, sumsWindow: Set[Long]): Boolean = sumsWindow.contains(value)

  def parseInput(filePath: String): List[Long] = {
    return Source.fromFile(filePath).getLines.toList.map(_.toLong)
  }

  def findBlockSumIndices(input: List[Long], value: Long): (Int, Int) = {
    val inputSize = input.size
    val sums: List[scala.collection.mutable.ListBuffer[Long]] = List.range(0, inputSize).map(_ => scala.collection.mutable.ListBuffer.fill(inputSize)(0))
    sums(0)(0) = input(0)
    var indices = (-1, -1)
    def findBlockSumIndicesRec(startIndex: Int, endIndex: Int): Unit = {
      if (sums(startIndex)(endIndex) == 0) {
        val nextSum = sums(startIndex)(endIndex - 1) + input(endIndex)
        sums(startIndex)(endIndex) = nextSum
        if (nextSum == value && endIndex - startIndex >= 2) indices = (startIndex, endIndex)
      }
      if (endIndex < inputSize - 1) findBlockSumIndicesRec(startIndex, endIndex + 1)
      else if (startIndex < inputSize - 1) findBlockSumIndicesRec(startIndex + 1, startIndex + 1)
    }
    findBlockSumIndicesRec(0, 1)
    return indices
  }

  def sumOfFaultyBlock(inputFilePath: String, value: Long): Long = {
    val xmasInput: List[Long] = parseInput(inputFilePath)
    val indices = findBlockSumIndices(xmasInput, value)
    val contiguousBlock = xmasInput.slice(indices._1, indices._2 + 1).sorted
    return contiguousBlock.head + contiguousBlock.reverse.head
  }
}