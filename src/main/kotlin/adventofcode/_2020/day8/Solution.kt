package adventofcode._2020.day8

import adventofcode._2020.inputText
import java.io.File

class Solution {
    private val instructions = inputText("src/main/kotlin/adventofcode/_2020/day8/input.txt")
        .trim()
        .split("\n")
        .map {
            val (op, arg) = Regex("""(\w+) ([+-]\d+)""").find(it)!!.destructured
            Instruction(op, arg.toInt())
        }

    fun solve(): Unit {
        println("P1: ${p1()}")
        println("P2: ${p2()}")
    }

    private fun p2(): Int {
        for (i in this.instructions.indices) {
            val instructions = this.instructions.toMutableList()
            if (instructions.get(i).op == "jmp" ||
                instructions.get(i).op == "nop") {
                val op = if (instructions.get(i).op == "jmp") "nop" else "jmp"
                val arg = instructions.get(i).arg
                instructions[i] = Instruction(op, arg)
            }


            val walked = mutableListOf<Int>()
            var acc = 0
            var idx = 0
            while (true) {
                if (walked.contains(idx) || idx > instructions.size-1) {
                    if (walked.contains(idx)) break;
                    return acc
                }
                walked.add(idx)

                val inst = instructions.get(idx)
                when (inst.op) {
                    "acc" -> {
                        acc += inst.arg
                        idx++
                    }
                    "jmp" -> idx += inst.arg
                    else -> idx++
                }
            }
        }
        return -1
    }

    private fun p1(): Int {
        val walked = mutableListOf<Int>()
        var acc: Int = 0
        var idx: Int = 0
        while (true) {
            if (walked.contains(idx)) {
                break
            }
            walked.add(idx)

            val inst = instructions.get(idx)
            when (inst.op) {
                "acc" -> {
                    acc += inst.arg
                    idx++
                }
                "jmp" -> idx += inst.arg
                else -> idx++
            }
        }
        return acc
    }

    data class Instruction(val op: String, val arg: Int)
}

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val solution = Solution()
        solution.solve()
    }
}
