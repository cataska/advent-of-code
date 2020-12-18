package adventofcode._2020.day18

import adventofcode._2020.inputLines
import java.util.*

fun main() {
    val expressions = inputLines("src/main/kotlin/adventofcode/_2020/day18/input.txt")
    expressions.map { exp -> Calculator().eval(exp) }.sum().run { println("P1: $this") }
    expressions.map { exp -> Calculator().eval(exp, true) }.sum().run { println("P2: $this") }
}

class Calculator {
    fun eval(exp: String, advance: Boolean = false) =
        parse(exp.filter { it != ' ' }.toCharArray(), advance)

    private fun parse(charArray: CharArray, advance: Boolean): Long {
        var pos = 0
        val opStack = LinkedList<Char>()
        val valueStack = LinkedList<String>()
        while (pos < charArray.size) {
            var ch = charArray[pos]
            if (ch in '0'..'9') {
                val ls = mutableListOf<Char>()
                ch = charArray[pos]
                while (ch in '0'..'9') {
                    ls.add(ch)
                    if (pos+1 >= charArray.size) {
                        ++pos
                        break
                    }
                    ch = charArray[++pos]
                }
                valueStack.push(ls.joinToString(""))
            } else if (ch == '(') {
                opStack.push(ch)
                ++pos
            } else if (ch == ')') {
                ch = opStack.pop()
                while (ch != '(') {
                    valueStack.push(ch.toString())
                    ch = opStack.pop()
                }
                ++pos
            } else if (ch == '+' || ch == '*') {
                if (advance) {
                    if (opStack.peek() == '+') {
                        valueStack.push(opStack.pop().toString())
                    }
                } else {
                    if (opStack.peek() == '+' || opStack.peek() == '*')
                        valueStack.push(opStack.pop().toString())

                }
                opStack.push(ch)
                ++pos
            }
        }

        while (!opStack.isEmpty()) {
            val ch = opStack.pop()
            valueStack.push(ch.toString())
        }

        val outputStack = LinkedList<Long>()
        while (!valueStack.isEmpty()) {
            val v = valueStack.removeLast()
            if (v != "+" && v != "*")
                outputStack.push(v.toLong())
            else {
                val v1 = outputStack.pop()
                val v2 = outputStack.pop()
                if (v == "+") outputStack.push(v1.toLong() + v2.toLong())
                else outputStack.push(v1.toLong() * v2.toLong())
            }
        }
        return outputStack.peek()
    }
}
