import scala.io.Source

object toboganSteer {
  def main(args: Array[String]): Unit = {
    val filename: String  = "map.txt"
    val map: List[String] = Source.fromFile(filename).getLines.toList

    val treesEncountered31: Int = {
      val squaresEncountered: List[Char] = map.zipWithIndex.map {
        case (line: String, y: Int) => {
          val x: Int = ((3 * y)) % (line.size)
          line.charAt(x)
        }
      }
      squaresEncountered.foldLeft(0)((count, squareType) =>
        if (squareType == '#') count + 1 else count
      )
    }
    println(treesEncountered31)
  }
}
