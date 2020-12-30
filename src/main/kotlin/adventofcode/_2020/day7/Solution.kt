package adventofcode._2020.day7

import adventofcode._2020.inputLines
import java.io.File
import java.util.*

class Solution {
    fun solve(): Unit {
        println("P1: ${p1()}")
        println("P2: ${p2()}")
    }

    private fun p2(): Int {
        val groups = inputLines("src/main/kotlin/adventofcode/_2020/day7/input.txt")
            .map { it -> it.split("contain") }
            .map { it[0].substring(0, it[0].length - " bags".length).trim() to it[1].trim().split(",") }.toMap()
        return countBags("shiny gold", groups)
    }

    private fun countBags(name: String, groups: Map<String, List<String>>): Int {
        val bags = groups[name]!!
        return if (bags.size == 1 && bags[0].startsWith("no other bags")) 0
        else bags.sumBy {
            val (n, b) = Regex("""(\d+) ([a-zA_Z ]+) bag""").find(it)!!.destructured
            val number = n.toInt()
            number + number * countBags(b, groups)
        }
    }

    private fun p1(): Int {
        val groups = inputLines("src/main/kotlin/adventofcode/_2020/day7/input.txt")
            .map { it.split("contain") }
            .map {
                val value = it[0].substring(0, it[0].length - " bags ".length)
                val key = it[1]//.split(",")
                mapOf(key to value)
            }

        var count = 0
        val foundedColors = mutableMapOf<String, String>()
        val stack = LinkedList<String>()

        stack.offer("shiny gold")
        while (!stack.isEmpty()) {
            val wantedColor = stack.poll()
            for (g in groups) {
                val key = g.keys.first()
                if (key.contains(wantedColor)) {
                    if (!foundedColors.containsKey(g.values.first())) {
                        count++
                        stack.offer(g.values.first())
                        foundedColors.put(g.values.first(), g.keys.first())
                    }
                }
            }
        }
        return count
    }
}

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val solution = Solution()
        solution.solve()
    }
}
