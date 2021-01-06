package adventofcode._2020.day5

import adventofcode._2020.inputLines
import java.io.File

class Solution {
    fun solve(): Unit {
        val input = inputLines("src/main/kotlin/adventofcode/_2020/day5/input.txt")
        val seats = mutableListOf<Int>()
        for (code in input) {
            val row = decode(code, 'B', 'F', 0, 127)
            val col = decode(code.drop(7), 'R', 'L', 0, 7)
            seats.add(row * 8 + col)
        }
        println("P1: " + p1(seats))
        println("P2: " + p2(seats.sorted()))

    }

    private fun p2(list: List<Int>): Int {
        for (i in 0..list.size - 2) {
            if (list[i] + 1 != list[i + 1])
                return list[i] + 1
        }
        return -1
    }

    private fun p1(seats: MutableList<Int>): Int {
        seats.sortDescending()
        return seats.first()
    }

    private fun decode(code: String, upper_char: Char, lower_char: Char, start: Int, end: Int): Int {
        val pos = code.first()
        if (pos != upper_char && pos != lower_char)
            return -1
        if (end - start == 1) {
            return when (pos) {
                lower_char -> start
                else -> end
            }
        }

        val mid = (start + end) / 2;
        return when (pos) {
            lower_char -> decode(code.drop(1), upper_char, lower_char, start, mid)
            else -> decode(code.drop(1), upper_char, lower_char, mid + 1, end)
        }
    }
}

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val solution = Solution()
        solution.solve()
    }
}
