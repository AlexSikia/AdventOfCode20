import scala.io.Source
import scala.collection.immutable.HashMap

object bootLoop {
  def main(args: Array[String]): Unit = {
    val filename: String        = "bootCode.txt"
    val instructions: List[(String, String)] = 
      Source.fromFile(filename).getLines.toList
            .map(instruction => (instruction.split(" ").head, instruction.split(" ")(1)))

    def computeAccBeforeLoop(instructions: List[(String, String)]): Int = {
      def computeAccRec(index: Int, acc: Int, visited: List[Int]): Int = {
        if (visited.contains(index)) return acc
        instructions(index) match {
          case (iType, argument) if iType == "acc" => computeAccRec(index + 1, acc + argument.toInt, index :: visited)
          case (iType, argument) if iType == "jmp" => computeAccRec(index + argument.toInt, acc, index :: visited)
          case _ => computeAccRec(index + 1, acc, index :: visited)
        }
      }
      computeAccRec(0, 0, List())
    }
    println(computeAccBeforeLoop(instructions))
  }
}
