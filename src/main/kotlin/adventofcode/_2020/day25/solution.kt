package adventofcode._2020.day25

import adventofcode._2020.inputLines

val N = 20201227

fun main() {
    val input = inputLines("src/main/kotlin/adventofcode/_2020/day25/input.txt")
    val card_pk = input[0].toInt()
    val door_pk = input[1].toInt()
    val card_loopsize = findLoopSize(7, card_pk)
    val door_loopsize = findLoopSize(7, door_pk)
    val encryptionKey = findEncryptionKey(door_pk, card_loopsize)
    println("P1: $encryptionKey")
}

fun findEncryptionKey(root: Int, size: Int): Long {
    var value = 1L
    repeat (size) {
        value = value * root % N
    }
    return value
}

fun findLoopSize(root: Int, result: Int): Int {
    var value = 1
    var k = 1
    while (true) {
        value = value * root % N
        if (value == result) return k
        k += 1
    }
}
