package adventofcode._2020.day15

import kotlin.math.absoluteValue

fun main() {
    val input = "9,12,1,4,17,0,18"
    val preinstalled = input.split(",").map(String::toInt)
    p1(2020, preinstalled).run{ println("P1: $this") }
    p1(30000000, preinstalled).run{ println("P2: $this") }
}
fun p1(desired: Int, preinstalled: List<Int>): Int {
    val numbers = preinstalled.toMutableList()
    val history = mutableMapOf<Int, MutableList<Int>>()
    var last = preinstalled.last()

    numbers.mapIndexed { index, num -> history[num] = mutableListOf(index) }
    for (i in preinstalled.size until desired) {
        val next: Int
        next = if (history[last] != null) {
            if (history[last]!!.size == 1)
                0
            else {
                val occur = history[last]
                (occur!![occur.size - 1] - occur[occur.size - 2]).absoluteValue
            }
        } else last

        if (history[next] == null) {
            history[next] = mutableListOf(i)
        } else {
            history[next]!!.add(i)
        }
        numbers.add(next)
        last = next
    }

    return numbers.last()
}