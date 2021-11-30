package adventofcode._2020.day1

import java.io.File

class Solution {
    fun multiply2SumTo(sum: Int): Int {
        val (map: MutableMap<Int, Int>, list: MutableList<Int>) = init()

        for (num in list) {
            val complement = sum - num
            if (map.getOrDefault(complement, -1) != -1) {
                return num * complement
            }
        }
        return -1
    }

    fun multiply3SumTo(sum: Int): Int {
        val (_, list: MutableList<Int>) = init()
        list.sort()
        val seen = HashSet<Int>()
        (0 until list.size).forEach { i ->
            if (i == 0 || list[i - 1] != list[i]) {
                (i+1 until list.size).forEach { j ->
                    val complement = sum - (list[i] + list[j])
                    if (seen.contains(complement)) {
                        return list[i] * list[j] * complement
                    }
                    seen.add(list[j])
                }
            }
        }
        return -1
    }

    private fun init(): Pair<MutableMap<Int, Int>, MutableList<Int>> {
        val map: MutableMap<Int, Int> = mutableMapOf()
        val list: MutableList<Int> = mutableListOf()
        var index = 0
        File("/Users/cataska/Documents/Projects/adventofcode2020/src/main/kotlin/adventofcode2020/day1/day9.txt").forEachLine {
            val n = it.toInt()
            map[n] = index
            list.add(index, n)
            index++
        }
        return Pair(map, list)
    }
}