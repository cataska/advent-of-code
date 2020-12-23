package adventofcode._2020.day22

import adventofcode._2020.inputText

fun main() {
    val input = inputText("src/main/kotlin/adventofcode/_2020/day22/input.txt").split("\n\n")
    val player1Cards = input[0].split("\n").drop(1).map(String::toLong)
    val player2Cards = input[1].split("\n").drop(1).map(String::toLong)

    simplePlay(player1Cards.toList(), player2Cards.toList()).score().run { println("P1: $this") }

    val (_, result) = recursivePlay(player1Cards.toList(), player2Cards.toList())
    println("P2: ${result.score()}")
}

fun List<Long>.score() =
    this.reversed().mapIndexed { index, card -> (index+1) * card }.sum()

fun recursivePlay(player1: List<Long>, player2: List<Long>): Pair<Int, List<Long>> {
    var p1 = player1
    var p2 = player2
    val history1 = mutableSetOf<List<Long>>()
    val history2 = mutableSetOf<List<Long>>()
    while (p1.isNotEmpty() && p2.isNotEmpty()) {
        if (history1.contains(p1) || history2.contains(p2))
            return 1 to emptyList()

        history1 += p1
        history2 += p2

        val card1 = p1.first()
        val card2 = p2.first()
        val whoWin = if (p1.size-1 >= card1 && p2.size-1 >= card2)
            recursivePlay(p1.drop(1).take(card1.toInt()).toList(), p2.drop(1).take(card2.toInt()).toList()).first
        else {
            if (card1 > card2) {
                1
            } else {
                2
            }
        }
        when (whoWin) {
            1 -> {
                p1 = p1.drop(1).plus(listOf(card1, card2))
                p2 = p2.drop(1)
            }
            2 -> {
                p2 = p2.drop(1).plus(listOf(card2, card1))
                p1 = p1.drop(1)
            }
        }
    }

    return if (p1.isEmpty()) 2 to p2 else 1 to p1
}

fun simplePlay(player1: List<Long>, player2: List<Long>): List<Long> {
    var p1 = player1
    var p2 = player2
    while (p1.isNotEmpty() && p2.isNotEmpty()) {
        val card1 = p1.first()
        val card2 = p2.first()
        if (card1 > card2) {
            p1 = p1.drop(1).plus(card1).plus(card2)
            p2 = p2.drop(1)
        } else {
            p2 = p2.drop(1).plus(card2).plus(card1)
            p1 = p1.drop(1)
        }
    }

    return if (p1.isEmpty()) p2 else p1
}
