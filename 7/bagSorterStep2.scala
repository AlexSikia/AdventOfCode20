import scala.io.Source
import scala.collection.immutable.HashMap

object bagSorterStep2 {
  def main(args: Array[String]): Unit = {
    val filename: String        = "bagRules.txt"
    val rules: List[String] = Source.fromFile(filename).getLines.toList

    def addToMap(rule: String, initialMap: HashMap[String, List[(String, Int)]]): HashMap[String, List[(String, Int)]] = {
      val containedRegex = "\\d+([\\w ])+[bags|bag][, |.]".r
      val outerColour: String = rule.split(" ").slice(0, 2).mkString(" ")
      val containedColourRules: List[String] = containedRegex.findAllIn(rule).toList
      initialMap.updated(outerColour, containedColourRules.map(containedRule => {
        val count: Int = containedRule.split(" ").head.toInt
        val containedColour: String = containedRule.split(" ").slice(1, 3).mkString(" ")
        (containedColour, count)
      }))
    }

    val emptyMap: HashMap[String, List[(String, Int)]] = HashMap()
    val bagsMap: HashMap[String, List[(String, Int)]] = rules.foldLeft(emptyMap)((accMap, rule) => addToMap(rule, accMap))
    
    val myBagColour = "shiny gold"

    def countInnerBags(currentColour: String, currentBagCount: Int): Int = {
      val innerBags = bagsMap.get(currentColour)
      innerBags match {
        case Some(bags) if bags.isEmpty => currentBagCount
        case Some(bags) => currentBagCount + currentBagCount * bags.foldLeft(0)((acc, nextBag) => acc + countInnerBags(nextBag._1, nextBag._2))
        case None => currentBagCount
      }
    }
    println(countInnerBags(myBagColour, 1) - 1)
  }
}
