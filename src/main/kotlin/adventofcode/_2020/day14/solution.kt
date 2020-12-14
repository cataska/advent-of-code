package adventofcode._2020.day14

import adventofcode._2020.inputLines
import kotlin.math.pow

fun main() {
    val instructions = inputLines("src/main/kotlin/adventofcode/_2020/day14/input.txt")
            .fold(listOf<Instruction>()) { lst, line ->
                val (l, r) = line.split("=")
                if (l.startsWith("mask"))
                    lst.plus(Instruction(Type.MASK, 0, r.trim()))
                else {
                    val addr = l.trim().drop(4).dropLast(1)
                    lst.plus(Instruction(Type.MEM, addr.toInt(), r.trim()))
                }
            }

    val a1 = p1(instructions)
    println("P1: $a1")

    val a2 = p2(instructions)
    println("P1: $a2")
}

fun p2(instructions: List<Instruction>): Long {
    var mask: String = "X"
    val mem = mutableMapOf<Long, Long>()
    for (i in instructions) {
        if (i.type == Type.MASK) {
            mask = i.value
        } else {
            newValWithMask(Integer.toBinaryString(i.addr), mask).map {
                val addr = it.toLong(2)
                mem.put(addr, i.value.toLong())
            }
        }
    }
    return mem.toList().map { it.second }.sum()
}

fun newValWithMask(value: String, mask: String): List<String> {
    val paddingLen = mask.length - value.length
    val paddingStr = "0".repeat(paddingLen) + value
    var str = ""
    val xpos = mutableListOf<Int>()
    for (i in mask.indices) {
        val bitMask = mask[i]
        if (bitMask == 'X' || bitMask == '1') {
            str += bitMask
            if (bitMask == 'X') xpos.add(i)
        } else
            str += paddingStr[i]
    }

    return generateMemAddress(str, xpos, listOf())
}

fun generateMemAddress(str: String, xpos: List<Int>, lst: List<String>): List<String> {
    if (xpos.isEmpty()) return lst.plus(str)

    val pos = xpos[0]
    var chars = str.toCharArray()
    chars[pos] = '0'
    var string = String(chars)
    var newLst = generateMemAddress(string, xpos.drop(1), lst)

    chars = str.toCharArray()
    chars[pos] = '1'
    string = String(chars)
    newLst = generateMemAddress(string, xpos.drop(1), newLst)

    return newLst
}

fun p1(instructions: List<Instruction>): Long {
    var mask: String = "X"
    val mem = mutableMapOf<Int, Long>()
    for (i in instructions) {
        if (i.type == Type.MASK) {
            mask = i.value
        } else {
            mem[i.addr] = maskValue(i.value, mask)
        }
    }
    return mem.toList().map { it.second }.sum()
}

fun maskValue(value: String, mask: String): Long {
    val m = mask.toCharArray()
    var v = value.toLong()
    var ret: Long = 0
    for ((level, i) in (m.lastIndex downTo 0).withIndex()) {
        val c = m[i]
        var rem = v % 2
        if (c != 'X') {
            rem = Character.getNumericValue(c).toLong()
        }
        ret += (2.toDouble().pow(level) * rem).toLong()

        v /= 2
    }
    return ret
}

enum class Type {
    MEM, MASK
}

data class Instruction(val type: Type, val addr: Int, val value: String)