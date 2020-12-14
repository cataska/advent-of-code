package adventofcode._2020.day13

import adventofcode._2020.inputLines

fun main() {
    println("P1: ${p1()}")
    println("P2: ${p2()}")
}

private fun p2(): Long {
    val lines = inputLines("src/main/kotlin/adventofcode/_2020/day13/input.txt")
    val buses = mutableListOf<Pair<Int, Int>>()
    for ((t, s) in lines[1].split(',').withIndex()) {
        if (s != "x") buses.add(s.toInt() to t)
    }

    var curr: Long = 0
    var step = buses[0].first.toLong()
    for (b in buses.drop(1)) {
        while ((curr + b.second) % b.first != 0L)
            curr += step
        step *= b.first
    }
    return curr
}

private fun p1(): Int {
    val lines = inputLines("src/main/kotlin/adventofcode/_2020/day13/input.txt")
    val timestamp = lines[0].toInt()
    val buses = lines[1].split(',').filter { it != "x" }.map { it.toInt() }

    for (t in generateSequence(timestamp) { it + 1 }) {
        val map = buses.mapIndexed { index, i ->
            index to (t != 0 && t % i == 0)
        }.filter { it.second }
        if (map.isNotEmpty()) {
            return buses[map[0].first] * (t - timestamp)
        }
    }
    return -1
}