import scala.io.Source
import scala.collection.immutable.HashMap

object bootLoop {
  def main(args: Array[String]): Unit = {
    val filename: String        = "bootCode.txt"
    val instructions: List[(String, String)] = 
      Source.fromFile(filename).getLines.toList
            .map(instruction => (instruction.split(" ").head, instruction.split(" ")(1)))

    def stepThrough(instructions: List[(String, String)]): (Boolean, Int) = {
      def stepThroughRec(index: Int, acc: Int, visited: List[Int]): (Boolean, Int) = {
        if (visited.contains(index)) return (false, acc)
        if (index >= instructions.size) return (true, acc)
        instructions(index) match {
          case (iType, argument) if iType == "acc" => stepThroughRec(index + 1, acc + argument.toInt, index :: visited)
          case (iType, argument) if iType == "jmp" => stepThroughRec(index + argument.toInt, acc, index :: visited)
          case _ => stepThroughRec(index + 1, acc, index :: visited)
        }
      }
      stepThroughRec(0, 0, List())
    }

    def switchiType(iType: String): String = if (iType == "jmp") "nop" else "jmp"

    def computeFixedBoot: Int = {
      for ((instruction, index) <- instructions.zipWithIndex) {
        instruction match {
          case (iType, argument) if iType == "nop" || iType == "jmp" => {
            stepThrough(instructions.updated(index, (switchiType(iType), instruction._2))) match {
              case (true, res) => return res
              case _ =>
            }
          }
          case _ =>
        }
      }
      return 0
    }
    println(computeFixedBoot)
  }
}
