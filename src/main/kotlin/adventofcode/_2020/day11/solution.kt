package adventofcode._2020.day11

import adventofcode._2020.inputText
import java.io.File

fun main() {
    val mapOfSeats = mutableListOf<List<Int>>()
    inputText("src/main/kotlin/adventofcode/_2020/day11/input.txt")
            .split("\n")
            .map {
                val line = mutableListOf<Int>()
                it.map {
                    val seat = when (it) {
                        'L' -> 0
                        '#' -> 1
                        else -> -1
                    }
                    line.add(seat)
                }
                mapOfSeats.add(line)
            }

    val a1: Int = solve(mapOfSeats, adjacentOccupied, 4)
    println("P1: $a1")

    val a2 = solve(mapOfSeats, firstSeenOccupied, 5)
    println("P2: $a2")
}

fun solve(map: List<List<Int>>, findOccupied: (List<List<Int>>, Int, Int) -> Int, least: Int): Int {
    var prev: List<List<Int>> = map
    while (true) {
        val firstStep = changeSeatState(prev, 0, findOccupied) { count -> count == 0 }
        val secondStep = changeSeatState(firstStep, 1, findOccupied) { count -> count >= least }

        if (prev == secondStep) {
            prev = secondStep
            break
        } else
            prev = secondStep
    }

    //toAscII(prev)
    return prev.fold(0, { acc, seats -> acc + seats.count { it == 1 } })
}

fun toAscII(newMap: List<List<Int>>) {
    newMap.map {
        it.map {
            val c = when (it) {
                0 -> 'L'
                1 -> '#'
                else -> '.'
            }
            print(c)
        }
        println()
    }
}

fun changeSeatState(mapOfSeats: List<List<Int>>, forChage: Int, adjacentOccupied: (List<List<Int>>, Int, Int) -> Int, condition: (Int) -> Boolean): List<List<Int>> {
    val newMap = mutableListOf<List<Int>>()
    for ((i, line) in mapOfSeats.withIndex()) {
        val newLine = mutableListOf<Int>()
        for ((j, seat) in line.withIndex()) {
            val newSeat = if (condition(adjacentOccupied(mapOfSeats, i, j))) {
                if (seat == forChage) flipSeat(seat) else seat
            } else seat
            newLine.add(newSeat)
        }
        newMap.add(newLine)
    }
    return newMap
}

fun flipSeat(seat: Int): Int {
    return when (seat) {
        0 -> 1
        1 -> 0
        else -> -1
    }
}

fun genDirection(): List<Pair<Int, Int>> {
    return listOf(-1, 0, 1).flatMap { ii ->
        listOf(-1, 0, 1).map {
            ii to it
        }
    }.filter { it != 0 to 0 }
}

val adjacentOccupied = { mapOfSeats: List<List<Int>>, i: Int, j: Int ->
    genDirection().map {
        i + it.first to j + it.second
    }.filter {
        it.first in 0..mapOfSeats.indices.maxOrNull()!! && it.second in 0..mapOfSeats[0].indices.maxOrNull()!!
    }.map {
        mapOfSeats[it.first][it.second]
    }.count { it == 1 }
}

val firstSeenOccupied = { mapOfSeats: List<List<Int>>, row: Int, col: Int ->
    genDirection().map {
        var (i, j) = row to col
        var occupiedCount = 0
        while (i in mapOfSeats.indices && j in mapOfSeats[0].indices) {
            i += it.first
            j += it.second
            if (i in mapOfSeats.indices && j in mapOfSeats[0].indices) {
                val seat = mapOfSeats[i][j]
                if (seat == 1 || seat == 0) {
                    if (seat == 1)
                        occupiedCount += 1
                    break
                }
            }
        }
        occupiedCount
    }.sum()
}
