package adventofcode._2020.day10

import adventofcode._2020.inputText

class Solution {
    private lateinit var diffs: List<Int>
    private val input = inputText("src/main/kotlin/adventofcode/_2020/day10/input.txt")
            .split("\n")
            .map(String::toInt)

    fun solve() {
        val (first, second) = p1()
        println("P1: ${first * second}")
        println("P2: ${p2()}")
    }

    private fun p2(): Long {
        var result: Long = 1
        val map = mapOf(0 to 1, 1 to 1, 2 to 2, 3 to 4, 4 to 7)
        var count = 0;
        for (diff in diffs) {
            when (diff) {
                1 -> count += 1
                else -> {
                    result *= map[count]!!
                    count = 0
                }
            }
        }
        return result
    }

    private fun p1(): Pair<Int, Int> {
        val ratings = input.sorted().toMutableList()
        val max = ratings.last()
        ratings.add(0)
        ratings.add(max+3)

        diffs = ratings.sorted().zipWithNext { a, b -> b-a }

        return diffs.count { it == 3 } to diffs.count { it == 1 }
    }
}

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val solution = Solution()
        solution.solve()
    }
}