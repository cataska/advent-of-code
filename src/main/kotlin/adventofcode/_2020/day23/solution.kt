package adventofcode._2020.day23

fun main() {
    val input = "496138527".toCharArray().map { it - '0' }.toList()
    var result = cupGame(input, 100)
    var current = result[1]?.next
    print("P1: ")
    while (current != result[1]) {
        print(current?.label)
        current = current?.next
    }
    println("")

    result = cupGame(input + (10..1_000_000).toList(), 10_000_000)
    val a = result[1]?.next?.label!!.toLong()
    val b = result[1]?.next?.next?.label!!.toLong()
    println("P2: ${a * b}")

}

fun cupGame(input: List<Int>, times: Int): Map<Int, Cup> {
    val cups = input.map { Cup(it, null) }
    for (i in cups.indices) {
        cups[i].next = cups[(i+1) % cups.size]
    }

    val map = mutableMapOf<Int, Cup>()
    for (c in cups) {
        map[c.label] = c
    }

    val min = input.min()
    val max = input.max()
    var cur = cups[0]
    repeat(times) {
        // Remove 3 cups after current cup
        val removed = cur.next
        cur.next = removed?.next?.next?.next

        // find destination cup
        var destination = if (cur.label == min) max else cur.label - 1
        // does destination in cups that just picked up
        while (destination == removed?.label || destination == removed?.next?.label || destination == removed?.next?.next?.label) {
            destination = destination!! - 1
            if (destination < min!!)
                destination = max
        }

        // insert cups that just picked up after destination
        val dest = map[destination!!]
        removed?.next?.next?.next = dest?.next
        dest?.next = removed

        // to next cup
        cur = cur.next!!
    }

    return map
}

data class Cup(val label: Int, var next: Cup?)
