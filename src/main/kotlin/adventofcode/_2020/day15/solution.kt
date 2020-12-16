package adventofcode._2020.day15

fun main() {
    val input = "9,12,1,4,17,0,18"
    val preinstalled = input.split(",").map(String::toInt)
    p(2020, preinstalled).run{ println("P1: $this") }
    p(30000000, preinstalled).run{ println("P2: $this") }
}

fun p(desired: Int, preinstalled: List<Int>): Int {
    var last = preinstalled.last()
    val history = IntArray(desired) { -1 }
    for ((i, x) in preinstalled.subList(0, preinstalled.lastIndex).withIndex())
        history[x] = i + 1
    var i = preinstalled.size
    while (i < desired) {
        val next = if (history[last] == -1) 0 else i - history[last]
        history[last] = i++
        last = next
    }
    return last
}
