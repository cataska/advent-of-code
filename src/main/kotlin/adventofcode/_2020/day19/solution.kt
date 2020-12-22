package adventofcode._2020.day19

import adventofcode._2020.inputText

fun main() {
    val input = inputText("src/main/kotlin/adventofcode/_2020/day19/input.txt").split("\n\n")
    val rules1 = input[0].split("\n").flatMap { line ->
        line.split(":").zipWithNext { a, b ->
            val body = b.trim()
            a.trim().toInt() to if (body.startsWith("\"")) body.drop(1).dropLast(1) else body
        }
    }.toMap()
    val messages = input[1].split("\n").map { it.trim() }

    messages.map { isMatch(rules1, it, listOf(0)) }.count { it }.run { println("P1: $this") }

    val rules2 = rules1 + listOf(8 to "42 | 42 8", 11 to "42 31 | 42 11 31").toMap()
    messages.map { isMatch(rules2, it, listOf(0)) }.count { it }.run { println("P2: $this") }
}

fun isMatch(allRules: Map<Int, String>, message: String, rules: List<Int>): Boolean {
    if (message.isEmpty()) {
        return rules.isEmpty()
    } else if (rules.isEmpty()) {
        return false
    }

    val rule = allRules.getOrDefault(rules[0], "")
    return if (rule[0] in 'a'..'z') {
        if (message.startsWith(rule[0]))
            isMatch(allRules, message.drop(1), rules.drop(1))
        else false
    }
    else rule.split(" | ").any {
        isMatch(allRules, message, it.split(" ").map(String::toInt).plus(rules.drop(1)))
    }
}
