package adventofcode._2020.day24

import adventofcode._2020.inputLines

val delta = mapOf(
    "e"  to ( 1 to  0),
    "w"  to (-1 to  0),
    "ne" to ( 1 to -1),
    "nw" to ( 0 to -1),
    "se" to ( 0 to  1),
    "sw" to (-1 to  1)
)

fun main() {
    var tiles = mutableMapOf<Pair<Int, Int>, Boolean>()
    val input = inputLines("src/main/kotlin/adventofcode/_2020/day24/input.txt")
    for (str in input) {
        var (x, y) = (0 to 0)
        var string = str.trim()
        while (string.isNotEmpty()) {
            var line = string.first().toString()
            string = string.drop(1)
            if (line.startsWith("n") || line.startsWith("s")) {
                line += string.first()
                string = string.drop(1)
            }
            x += delta[line]?.first!!
            y += delta[line]?.second!!
        }
        tiles.putIfAbsent(x to y, false)
        tiles[x to y] = !tiles[x to y]!!
    }

    tiles.count { it.value }.run { println("P1: $this") }

    var newTiles = tiles.toMap()
    repeat(100) {
        newTiles = dailyArt(newTiles)
    }
    newTiles.count { it.value }.run { println("P2: $this") }
}

fun dailyArt(tiles: Map<Pair<Int, Int>, Boolean>): Map<Pair<Int, Int>, Boolean> {
    val newTiles = tiles.toMutableMap()
    tiles.keys.forEach { tile ->
        val nr = neighbors(tile)
        nr.forEach {
            newTiles.putIfAbsent(it, false)
        }
    }

    newTiles.entries.forEach { (tile, color) ->
        val count = neighbors(tile).count {
            tiles.getOrDefault(it, false)
        }

        val flip = when {
            color && (count == 0 || count > 2) -> true
            !color && count == 2 -> true
            else -> false
        }

        if (flip)
            newTiles[tile] = !newTiles[tile]!!
    }

    return newTiles
}

fun neighbors(tile: Pair<Int, Int>): List<Pair<Int, Int>> =
    delta.values.map { (dx, dy) ->
        tile.first + dx to tile.second + dy
    }
