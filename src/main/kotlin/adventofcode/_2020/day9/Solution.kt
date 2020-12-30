package adventofcode._2020.day9

import adventofcode._2020.resourceString
import java.io.File

class Solution {
    val input = resourceString("day9.txt")
            .trim()
            .split("\n")
            .map { it.toLong() }

    fun solve() {
        val ans1 = p1(25)
        println("P1: $ans1")

        val sum = p2(ans1)
        println("P2: $sum")
    }

    private fun p2(desired: Long): Long {
        var lo = 0
        var hi = lo + 1
        while (lo < hi || lo < input.size-1) {
            val sum: Long = (lo..hi).fold(0, { sum: Long, idx -> sum + input[idx]})
            when {
                sum == desired -> {
                    val lst = (lo..hi).map { input[it] }.sorted()
                    return lst.first() + lst.last()
                }
                sum < desired -> hi++
                else -> lo++
            }
        }
        return 0L
    }

    private fun p1(count: Int): Long {
        for (i in 0 until input.size - count)
            if (!isValidNext(i, count))
                return input[i + count]

        return -1
    }

    private fun isValidNext(start: Int, count: Int): Boolean {
        if (start + count >= input.size) return false

        val next = input[start + count]
        val lst: MutableList<Map<Long, Pair<Long, Long>>> = mutableListOf()
        for (i in start until start+count) {
            for (j in i+1 until start+count) {
                val sum = input[i] + input[j]
                lst.add(mapOf(sum to (input[i] to input[j])))
            }
        }

        return lst.map {
            it.containsKey(next)
        }.any { it }
    }
}

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val solution = Solution()
        solution.solve()
    }
}
