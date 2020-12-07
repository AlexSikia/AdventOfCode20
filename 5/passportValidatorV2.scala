import scala.io.Source
import scala.collection.mutable.ListBuffer

object toboganSteer {
  def main(args: Array[String]): Unit = {
    val filename: String        = "passports.txt"
    val passports: List[String] = Source.fromFile(filename).getLines.toList

    val mandatoryFields: List[String] = List("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
    def isInt(str: String): Boolean = {
      try { str.toInt }
      catch { case _: Throwable => false }
      true
    }
    val hclRegex = "#([0-9]|[a-f]){6}".r
    val eclRegex = "(amb|blu|brn|gry|grn|hzl|oth)".r

    def validateField(fieldKey: String, fieldVal: String): Boolean = fieldKey match {
      case "byr" =>
        fieldVal.size == 4 && isInt(fieldVal) && fieldVal.toInt >= 1920 && fieldVal.toInt <= 2002
      case "iyr" =>
        fieldVal.size == 4 && isInt(fieldVal) && fieldVal.toInt >= 2010 && fieldVal.toInt <= 2020
      case "eyr" =>
        fieldVal.size == 4 && isInt(fieldVal) && fieldVal.toInt >= 2020 && fieldVal.toInt <= 2030
      case "hgt" =>
        isInt(fieldVal.dropRight(2)) &&
          (fieldVal.endsWith("cm") && fieldVal.dropRight(2).toInt >= 150
            && fieldVal.dropRight(2).toInt <= 193) ||
          (fieldVal.endsWith("in") && fieldVal.dropRight(2).toInt >= 59
            && fieldVal.dropRight(2).toInt <= 76)
      case "hcl" => fieldVal match { case hclRegex(_) => true; case _ => false }
      case "ecl" => fieldVal match { case eclRegex(_) => true; case _ => false }
      case "pid" => fieldVal.size == 9 && isInt(fieldVal)
      case "cid" => true
      case _     => false
    }

// byr (Birth Year) - four digits; at least 1920 and at most 2002.
// iyr (Issue Year) - four digits; at least 2010 and at most 2020.
// eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
// hgt (Height) - a number followed by either cm or in:
// If cm, the number must be at least 150 and at most 193.
// If in, the number must be at least 59 and at most 76.
// hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
// ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
// pid (Passport ID) - a nine-digit number, including leading zeroes.
// cid (Country ID) - ignored, missing or not.

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

    def passportHasAllFields(passport: String): Boolean = {
      val passportParams: Array[String] = passport.split(" ")
      val passportFields: Array[String] = passportParams.map(_.split(":").head)
      mandatoryFields.foldLeft(true)((acc, field) => acc && passportFields.contains(field))
    }

    def fieldsAreValid(passport: String): Boolean = {
      val passportParams: Array[String] = passport.trim.split(" ")
      passportParams.foldLeft(true)((acc, field) => {
        val fieldKeyVal: (String, String) = (field.split(":").head, field.split(":").tail.head)
        acc & validateField(fieldKeyVal._1, fieldKeyVal._2)
      })
    }

    println(processedPassports.size)
    println(processedPassports.filter(passportHasAllFields).filter(fieldsAreValid).size)
  }
}
