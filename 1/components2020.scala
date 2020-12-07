import scala.io.Source

object components2020 {
  def main(args: Array[String]): Unit = {
    val filename: String = "expenseReport.txt"
    val nums: List[Int] =
      Source.fromFile(filename).getLines.map(_.toInt).toList
    // val nums: List[Int] = List(1, 3, 6, 7, 10)
    val TARGET = 2020

    // def binarySumFind(numbers: List[Int]): (Int, Int) = {
    //   if (numbers.size < 2) (0, 0)
    //   else {
    //     val middleIndex = numbers.size / 2
    //     val middleNums = (numbers(middleIndex - 1), numbers(middleIndex))
    //     val middleSum = middleNums._1 + middleNums._2
    //     println(middleIndex, middleNums, middleSum)
    //     middleSum match {
    //       case TARGET => middleNums
    //       case x if x > TARGET => {
    //         println("Boo")
    //         binarySumFind(numbers.slice(0, middleIndex))
    //       }
    //       case x if x < TARGET => {
    //         println("BAR")
    //         binarySumFind(numbers.drop(middleIndex))
    //       }
    //     }
    //   }
    // }
    // val components2020 = binarySumFind(nums.sorted)

    // def findNumsToSum(
    //     sourceNums: HashSet[Int],
    //     targetSum: Int,
    //     numsToFind: Int,
    //     acc: List[Int],
    //     solutions: List[List[Int]]
    // ): List[List[Int]] = {
    //   if (numsToFind == 1) {
    //     sourceNums.find(num => sourceNums.contains(targetSum - num)) match {
    //       case None        => solutions
    //       case Some(value) => (value :: acc) :: solutions
    //     }
    //   } else {
    //     sourceNums.map(num => {
    //       findNumsToSum(
    //         sourceNums - num,
    //         targetSum - num,
    //         numsToFind - 1,
    //         num :: acc,
    //         solutions
    //       )
    //     })
    //   }
    // }
    val twoCombinations: List[(Int, Int)] = for (a <- nums; b <- nums) yield (a, b)

    val threeNums: List[Int] = twoCombinations
      .map({
        case (a, b) => {
          val missingNum = TARGET - (a + b)
          if (nums.contains(missingNum)) (a * b * missingNum)
          else 0
        }
      })
      .filter(_ != 0)
    println(threeNums)
    // val firstComponentNum: Int =
    //   nums.find(num => nums.contains(TARGET - num)) match {
    //     case None        => 0
    //     case Some(value) => value
    //   }
    // val components2020: (Int, Int) =
    //   (firstComponentNum, TARGET - firstComponentNum)
    // val candidates: List[List[Int]] = findNumsToSum(nums, TARGET, 3, List(), List(List()))
    // val components2020: List[Int]   = if (candidates.size > 0) candidates.head else List(0)
    // println(components2020)
    // println(components2020.reduce(_ * _))
  }
}
