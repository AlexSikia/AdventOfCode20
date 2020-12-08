import scala.io.Source
import scala.collection.immutable.HashMap

object bagSorter {
  def main(args: Array[String]): Unit = {
    val filename: String        = "bagRules.txt"
    val rules: List[String] = Source.fromFile(filename).getLines.toList

    
    def addInvertBagMap(rule: String, initialMap: HashMap[String, List[String]]): HashMap[String, List[String]] = {
      val containedRegex = "\\d+([\\w ])+[bags|bag][, |.]".r

      val outerColour: String = rule.split(" ").slice(0, 2).mkString(" ")
      val containedColourRules: List[String] = containedRegex.findAllIn(rule).toList
      containedColourRules.foldLeft(initialMap)((currentMap, containedRule) => {
        val containedColour: String = containedRule.split(" ").slice(1, 3).mkString(" ")
        currentMap.get(containedColour) match {
          case Some(existingColours) if existingColours.contains(containedColour) => currentMap
          case Some(existingColours) => currentMap.updated(containedColour, outerColour :: existingColours)
          case None => currentMap.updated(containedColour, List(outerColour))
        }
      })
    }

    val emptyMap: HashMap[String, List[String]] = HashMap()
    val invertedMap: HashMap[String, List[String]] = rules.foldLeft(emptyMap)((accMap, rule) => addInvertBagMap(rule, accMap))
    
    val myBagColour = "shiny gold"

    // Note: we need not fear cycles, as bag rules are absolute and cycles would make them un-implementable
    def countContainers(outerColour: String, invertedMap: HashMap[String, List[String]]): Long = {
      val emptySet: Set[String] = Set()
      def findContainersRec(currentColour: String, acc: Set[String]): Set[String] = {
        invertedMap.get(currentColour) match {
          case Some(containedColours) => acc ++ containedColours.foldLeft(emptySet)((a, nextColour) => findContainersRec(nextColour, a + nextColour))
          case None => acc
        }
      }
      findContainersRec(outerColour, emptySet).size
    }
    println(countContainers(myBagColour, invertedMap))
  }
}
