package joltageChainer

import org.scalatest.FunSpec
import scala.io.Source

class joltageChainerSpec extends FunSpec {
    describe("joltageChainer Step 1") {
        val exampleFilePath1 = "./src/test/resources/exampleInput1.txt"
        val exampleFilePath2 = "./src/test/resources/exampleInput2.txt"

        describe("Given a sequence of adapater joltages") {
            it("multiplies the number of 1-joltage difference with 3-joltage differences in the sorted chain") {
                val example1Result = joltageChainer.countJoltageDifferences(exampleFilePath1)
                val example2Result = joltageChainer.countJoltageDifferences(exampleFilePath2)
                assert(example1Result == 35)
                assert(example2Result == 220)
            }

            it("finds windows of removable adapters") {
                val sortedAdapters: List[Int] = joltageChainer.getSortedAdapters(exampleFilePath1)
                val expectedWindows = List(
                    List(19),
                    List(15, 16),
                    List(10, 11, 12),
                    List(4, 5, 6, 7),
                    List(0, 1)
                )
                assert(joltageChainerStep2.isolateWindows(sortedAdapters) == expectedWindows)
            }

            it("counts adapter combinations within a window") {
                assert(joltageChainerStep2.computeWindowCombinations(List(4, 5, 6, 7)) == 4, "| example window")
                assert(joltageChainerStep2.computeWindowCombinations(List(4, 7)) == 1, "| 2-elements window")
                assert(joltageChainerStep2.computeWindowCombinations(List(4, 5, 7)) == 2, "| small window")
                assert(joltageChainerStep2.computeWindowCombinations(List(4, 5, 6, 7, 9, 10)) == 10, "| large window")
            }

            it("counts all possible combinations throughout the adapters chain") {
                assert(joltageChainerStep2.countChainCombinations(exampleFilePath1) == 8)
                assert(joltageChainerStep2.countChainCombinations(exampleFilePath2) == 19208)
            }
        }
    }
}