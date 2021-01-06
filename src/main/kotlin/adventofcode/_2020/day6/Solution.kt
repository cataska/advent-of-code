package adventofcode._2020.day6

import adventofcode._2020.resourceString
import kotlin.collections.HashSet

class Solution {
    fun solve(): Unit {
        println("P1: " + p1())
        println("P2: " + p2())
    }

    private fun p2(): Int = resourceString("day6.txt")
            .trim()
            .split("\n\n")
            .map {
                it.split("\n").map(String::toSet)
            }
            .map {
                it.reduce(Set<Char>::intersect)
            }
            .sumBy { it.count() }

    private fun p1(): Int = resourceString("day6.txt")
            .split("\n\n")
            .map { it.split("\n") }
            .map {
                val set: MutableSet<Char> = HashSet()
                for (e in it) for (c in e) set.add(c)
                set.count()
            }
            .sum()
}

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val solution = Solution()
        solution.solve()
    }
}
