package adventofcode._2020.day2

import java.io.File

class Solution {
    fun solveProblem1(input: List<String> = File("/Users/cataska/Documents/Projects/adventofcode2020/src/main/kotlin/adventofcode2020/day2/day9.txt").readLines()): Int {
        val valids = input.map { validateProblem1(it) }
        return valids.count { it }
    }

    fun solveProblem2(input: List<String> = File("/Users/cataska/Documents/Projects/adventofcode2020/src/main/kotlin/adventofcode2020/day2/day9.txt").readLines()): Int {
        val valids = input.map { validateProblem2(it) }
        return valids.count { it }
    }

    private fun validateProblem1(line: String): Boolean {
        val (start, end, ch, password) = extractLine(line)
        val rangeStart = start.toInt()
        val rangeEnd = end.toInt()
        val times = password.toCharArray().count { it == ch[0] }
        return times in rangeStart..rangeEnd
    }

    private fun validateProblem2(line: String): Boolean {
        val (start, end, ch, password) = extractLine(line)
        val p1 = start.toInt() - 1
        val p2 = end.toInt() - 1
        return listOf(password[p1] == ch[0], password[p2] == ch[0]).count { it } == 1
    }

    private fun extractLine(line: String): MatchResult.Destructured {
        val regex = """(\d+)-(\d+) ([a-zA-Z]): ([a-zA-Z]+)""".toRegex()
        val matchResult = regex.find(line)
        return matchResult!!.destructured
    }
}