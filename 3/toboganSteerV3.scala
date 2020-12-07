import scala.io.Source

object toboganSteer {
  def main(args: Array[String]): Unit = {
    val filename: String       = "map.txt"
    val treesMap: List[String] = Source.fromFile(filename).getLines.toList
    val directions             = List((1, 1), (3, 1), (5, 1), (7, 1), (1, 2))
    println(
      directions
        .map { case (xJump: Int, yJump: Int) =>
          treesMap.zipWithIndex
            .filter { case (line, yCoord: Int) => yCoord % yJump == 0 }
            .zipWithIndex
            .map {
              case ((line: String, yCoord), jumpCount: Int) => {
                line.charAt(((xJump * jumpCount)) % (line.size))
              }
            }
            .foldLeft(0)((count, squareType) => if (squareType == '#') count + 1 else count)
        }
        .foldLeft(1.toLong)((acc: Long, treesCount: Int) => acc * treesCount)
    )
  }
}
