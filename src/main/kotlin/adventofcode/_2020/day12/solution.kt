package adventofcode._2020.day12

import adventofcode._2020.inputLines
import kotlin.math.absoluteValue
import kotlin.math.cos
import kotlin.math.sin

fun main() {
    val directions = inputLines("src/main/kotlin/adventofcode/_2020/day12/input.txt")
            .fold(listOf<Direction>()) { lst, line ->
                val action = line[0]
                val value = line.substring(1, line.lastIndex+1).toInt()
                lst.plus(Direction(action, value))
            }

    var ans = p1(directions)
    println("P1: $ans")

    ans = p2(directions)
    println("P2: $ans")
}

fun p2(directions: List<Direction>): Int {
    var sx = 0; var sy = 0
    var wx = 10; var wy = 1
    directions.map {
        val action = it.action
        val value = it.value
        when (action) {
            'N' -> wy += value
            'S' -> wy -= value
            'E' -> wx += value
            'W' -> wx -= value
            'L' -> {
                var degree = value
                while (degree > 0) {
                    val tmpX = wx
                    wx = -wy
                    wy = tmpX
                    degree -= 90
                }
                /*
                wx = (cos(value.toDouble()) * wx - sin(value.toDouble()) * wy).toInt()
                wy = (sin(value.toDouble()) * wx + cos(value.toDouble()) * wy).toInt()
                 */
            }
            'R' -> {
                var degree = value
                while (degree > 0) {
                    val tmpX = wx
                    wx = wy
                    wy = -tmpX
                    degree -= 90
                }
                //wx = (cos(-value.toDouble()) * wx - sin(-value.toDouble()) * wy).toInt()
                //wy = (sin(-value.toDouble()) * wx + cos(-value.toDouble()) * wy).toInt()
                /*
                wx = (cos(value.toDouble()) * wx + sin(value.toDouble()) * wy).toInt()
                wy = -(sin(value.toDouble()) * wx + cos(value.toDouble()) * wy).toInt()

                 */
            }
            else -> {
                sx += value * wx
                sy += value * wy
            }
        }
    }
    return sx.absoluteValue + sy.absoluteValue
}

private fun p1(directions: List<Direction>): Int {
    var x = 0
    var y = 0
    var degree = 0
    directions.map {
        val action = it.action
        val value = it.value
        when (action) {
            'N' -> y += value
            'S' -> y -= value
            'E' -> x += value
            'W' -> x -= value
            'L' -> {
                degree += value
                if (degree > 360) degree -= 360
            }
            'R' -> {
                degree -= value
                if (degree < 0) degree += 360
            }
            else -> {
                when (degree) {
                    90 -> y += value
                    180 -> x -= value
                    270 -> y -= value
                    else -> x += value
                }
            }
        }
    }
    return x.absoluteValue + y.absoluteValue
}

data class Direction(val action: Char, val value: Int)