package adventofcode._2020.day17

import adventofcode._2020.inputText

interface Cube {
    fun neighbors(): List<Cube>
}

data class Cube3D(var x: Int, var y: Int, var z: Int): Cube {
    override fun neighbors(): List<Cube3D> =
        (x-1..x+1).flatMap { dx ->
            (y-1..y+1).flatMap { dy ->
                (z-1..z+1).mapNotNull { dz ->
                    Cube3D(dx, dy, dz).takeUnless { it == this }
                }
            }
        }
}

data class Cube4D(var x: Int, var y: Int, var z: Int, var w: Int): Cube {
    override fun neighbors(): List<Cube4D> =
        (x - 1..x + 1).flatMap { dx ->
            (y - 1..y + 1).flatMap { dy ->
                (z - 1..z + 1).flatMap { dz ->
                    (w - 1..w + 1).mapNotNull { dw ->
                        Cube4D(dx, dy, dz, dw).takeUnless { it == this }
                    }
                }
            }
        }
}

fun main() {
    val input = inputText("src/main/kotlin/adventofcode/_2020/day17/input.txt")
        .split("\n")

    val active = mutableMapOf<Cube, Boolean>()

    var y = 0
    for (line in input) {
        var x = 0
        for (p in line) {
            active[Cube3D(x, y, 0)] = p == '#'
            x += 1
        }
        y += 1
    }

    simulate(active, 6).count { it.value }.run { println("P1: $this") }

    active.clear()
    y = 0
    for (line in input) {
        var x = 0
        for (p in line) {
            active[Cube4D(x, y, 0, 0)] = p == '#'
            x += 1
        }
        y += 1
    }

    simulate(active, 6).count { it.value }.run { println("P2: $this") }
}

fun simulate(init: Map<Cube, Boolean>, cycle: Int): Map<Cube, Boolean> {
    var map = init
    repeat (cycle) {
        val newMap = map.toMutableMap()
        map.keys.forEach { cube ->
            val nr = cube.neighbors()
            nr.forEach { newMap.putIfAbsent(it, false) }
        }

        newMap.entries.forEach { (cube, active) ->
            val activeCnt = cube.neighbors().count { map.getOrDefault(it, false) }
            newMap[cube] = when {
                active && activeCnt in setOf(2, 3) -> true
                !active && activeCnt == 3 -> true
                else -> false
            }
        }

        map = newMap
    }
    return map
}
