package adventofcode._2020.day3

import java.io.File

class Solution {
    private val lines = File("/Users/cataska/Documents/Projects/adventofcode2020/src/main/kotlin/adventofcode2020/day3/day9.txt").readLines().map { it.trim() }
    fun problem1(input: List<String> = lines): Int {
        return countTrees(input, 3, 1)
    }

    private fun countTrees(input: List<String>, dx: Int, dy: Int): Int {
        var x = 0
        var y = 0
        var trees = 0
        while (y < lines.size) {
            if (lines[y][x % lines.first().length] == '#') trees++
            x += dx
            y += dy
        }
        return trees
    }

    fun problem2(input: List<String> = lines): Long {
        return countTrees(input,1, 1).toLong() *
               countTrees(input,3, 1).toLong() *
               countTrees(input,5, 1).toLong() *
               countTrees(input,7, 1).toLong() *
               countTrees(input,1, 2).toLong()
    }
}

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val solution = Solution()
        //println(solution.problem1())
        println(solution.problem2())
    }
}