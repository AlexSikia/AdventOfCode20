import scala.io.Source
import scala.collection.mutable.ListBuffer

object toboganSteer {
  def main(args: Array[String]): Unit = {
    val filename: String        = "passports.txt"
    val passports: List[String] = Source.fromFile(filename).getLines.toList

    val mandatoryFields: List[String] = List("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")

    val processedPassports: ListBuffer[String] = ListBuffer()
    var passportBuffer: String                 = ""
    for (passportSlice: String <- passports) {
      if (passportSlice == "") {
        processedPassports += passportBuffer
        passportBuffer = ""
      } else {
        passportBuffer += s" ${passportSlice}"
      }
    }

    def isValidPassport(passport: String): Boolean = {
      val passportParams: Array[String] = passport.split(" ")
      val passportFields: Array[String] = passportParams.map(_.split(":").head)
      mandatoryFields.foldLeft(true)((acc, field) => acc && passportFields.contains(field))
    }
    println(processedPassports.size)
    println(processedPassports.filter(isValidPassport).size)
  }
}
