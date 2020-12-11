package xmasDecoder

import org.scalatest.FunSpec
import scala.io.Source

class xmasDecoderSpec extends FunSpec {
    describe("xmasDecoder Step 1") {
        val exampleFilePath = "./src/test/resources/exampleInput1.txt"

        describe("Given a sequence of numbers and a window size N") {
            it("initialises a sums window of size N correctly") {
                val expectedInitialWindow = Set(
                    List(55, 50, 60, 82),
                    List(35, 45, 67),
                    List(40, 62),
                    List(72)
                ).flatten
                val initialWindow = xmasDecoder.makeWindow(xmasDecoder.parseInput(exampleFilePath), 0, 5)
                assert(initialWindow == expectedInitialWindow)
            }

            it("finds the first number that is not a sum of the previous N") {
                assert(xmasDecoder.findNotASum(exampleFilePath, 5) == 127)
            }
        }
    }
    describe("xmasDecoder Step 2") {
        val exampleFilePath = "./src/test/resources/exampleInput2.txt"

        describe("Given a sequence of numbers and a faulty value V") {
            it("finds the contiguous numbers that sum to V") {
                assert(xmasDecoderStep2.findBlockSumIndices(xmasDecoder.parseInput(exampleFilePath), 127) == (2, 5))
            }

            it("returns the sum of the min and max numbers in the contiguous block") {
                assert(xmasDecoderStep2.sumOfFaultyBlock(exampleFilePath, 127) == 62)
            }
        }
    }
}