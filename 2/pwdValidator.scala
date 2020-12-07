import scala.io.Source

case class PwdRule(minCount: Int, maxCount: Int, letter: Char)
case class PwdRecord(rule: PwdRule, pwd: String)

object pwdValidator {
  def validatePwdRecord(record: PwdRecord): Boolean = {
    val letterCount: Int = record.pwd.foldLeft(0)((acc, nextCharacter) =>
      if (nextCharacter == record.rule.letter) acc + 1 else acc
    )
    (letterCount >= record.rule.minCount) && (letterCount <= record.rule.maxCount)
  }

  def main(args: Array[String]): Unit = {
    val filename: String = "pwds.txt"
    def toPwdRecord(record: String): PwdRecord = {
      val splitRecord = record.split(':')
      val pwdRule: PwdRule = {
        splitRecord.head match {
          case rule: String => {
            val ruleSplit = rule.split(' ')
            val minCount  = ruleSplit.head.split('-').head.toInt
            val maxCount  = ruleSplit.head.split('-').tail.head.toInt
            PwdRule(minCount, maxCount, ruleSplit.tail.head.toCharArray().head)
          }
        }
      }
      PwdRecord(pwdRule, splitRecord.tail.head)
    }
    val pwdRecords: List[PwdRecord] = Source.fromFile(filename).getLines.toList.map(toPwdRecord)
    val correctPwdsCount: Int = pwdRecords.foldLeft(0)((acc: Int, record: PwdRecord) =>
      if (validatePwdRecord(record)) acc + 1 else acc
    )
    println(correctPwdsCount)
  }
}
