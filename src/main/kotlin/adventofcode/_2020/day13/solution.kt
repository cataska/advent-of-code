package adventofcode._2020.day13

import adventofcode._2020.inputLines

fun main() {
    println("P1: ${p1()}")
    println("P2: ${p2()}")
}

fun multInv(a: Long, b: Long): Long {
    if (b == 1L) return 1
    var aa = a
    var bb = b
    var x0: Long = 0
    var x1: Long = 1
    while (aa > 1) {
        val q: Long = aa / bb
        var t: Long = bb
        bb = aa % bb
        aa = t
        t = x0
        x0 = x1 - q * x0
        x1 = t
    }
    if (x1 < 0) x1 += b
    return x1
}

fun chineseRemainder(nums: List<Pair<Int, Int>>): Long {
    val n = nums.map { it.first.toLong() }
    val a = nums.map { it.second.toLong() }
    val prod = n.fold(1L) { acc, i -> acc * i }
    var sum = 0L
    for (i in 0 until n.size.toInt()) {
        val p = prod / n[i]
        sum += a[i] * multInv(p, n[i]) * p
    }
    return sum % prod
}

private fun p2(): Long {
    val lines = inputLines("src/main/kotlin/adventofcode/_2020/day13/input.txt")
    val buses = mutableListOf<Pair<Int, Int>>()
    for ((t, s) in lines[1].split(',').withIndex()) {
        if (s != "x") buses.add(s.toInt() to (s.toInt() - t) % s.toInt())
    }

    return chineseRemainder(buses)

    /*
    var curr: Long = 0
    var step = buses[0].first.toLong()
    for (b in buses.drop(1)) {
        while ((curr + b.second) % b.first != 0L)
            curr += step
        step *= b.first
    }
    return curr

     */
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