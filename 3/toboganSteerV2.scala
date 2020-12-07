import scala.io.Source

object toboganSteer {
  def main(args: Array[String]): Unit = {
    val filename: String  = "map.txt"
    val map: List[String] = Source.fromFile(filename).getLines.toList

    val directions: List[(Int, Int)] = List((1, 1), (3, 1), (5, 1), (7, 1), (1, 2))

    val treesEncounteredPerDirection: List[Int] = directions.map { case (xJump: Int, yJump: Int) =>
      val treesEncountered: Int = {
        val squaresEncountered: List[Char] = map.zipWithIndex.map {
          case (line: String, y: Int) => {
            if (yJump == 1) {
              val x: Int = ((xJump * y)) % (line.size)
              line.charAt(x)
            } else {
              val x: Int = ((xJump * (y / 2))) % (line.size)
              if (y % yJump == 0) line.charAt(x) else 'O'
            }
          }
        }
        squaresEncountered.foldLeft(0)((count, squareType) =>
          if (squareType == '#') count + 1 else count
        )
      }
      treesEncountered
    }
    println(treesEncounteredPerDirection)
    println(treesEncounteredPerDirection.foldLeft(1.toLong)((acc: Long, trees: Int) => acc * trees))
  }
}
