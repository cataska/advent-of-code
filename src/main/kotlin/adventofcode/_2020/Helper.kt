package adventofcode._2020

import java.io.File

fun resourceString(name: String): String {
    val res = object {}.javaClass.classLoader.getResource(name)
    return res.readText()
}

fun inputText(name: String): String = File(name)
        .readText()
        .trim()

fun inputLines(name: String): List<String> = inputText(name).split("\n")