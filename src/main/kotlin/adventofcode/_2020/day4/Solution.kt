package adventofcode._2020.day4

import java.io.File

class Solution {
    fun solve(): Unit {
        val passports = assemble(File("/Users/cataska/Documents/Projects/adventofcode2020/src/main/kotlin/adventofcode2020/day4/day9.txt").readLines())
            .map { it.split(" ") }
        //countValidPassport(passports)
        //println(countValidPassport(passports))
        println(countValidPassportWithDetail(passports))
    }

    private fun countValidPassportWithDetail(input: List<List<String>>) =
        input.filter { isValidPassport(it) }
            .map { isValidPassportWithDetail(it) }
            .count { it }

    private fun isValidPassportWithDetail(passport: List<String>): Boolean {
        var result = mutableListOf<Boolean>()
        for (s in passport) {
            val kv = s.split(":")
            val map = mapOf(kv[0] to kv[1])
            val keys = map.keys
            when {
                keys.contains("byr") -> {
                    result.add(if (map["byr"]?.length != 4)
                        false
                    else {
                        val byr = (map["byr"] ?: error("")).toInt()
                        byr in 1920..2002
                    })
                }
                keys.contains("iyr") -> {
                    result.add(if (map["iyr"]?.length != 4) false
                    else {
                        val byr = (map["iyr"] ?: error("")).toInt()
                        byr in 2010..2020
                    })
                }
                keys.contains("eyr") -> {
                    result.add(if (map["eyr"]?.length != 4) false
                    else {
                        val byr = (map["eyr"] ?: error("")).toInt()
                        byr in 2020..2030
                    })
                }
                keys.contains("hgt") -> {
                    val hgt = map["hgt"]
                    val format = hgt?.substring(hgt.length - 2)
                    result.add(when (format) {
                        "in" -> {
                            val n = hgt.substring(0, hgt.length - 2).toInt()
                            n in 59..76
                        }
                        "cm" -> {
                            val n = hgt.substring(0, hgt.length - 2).toInt()
                            n in 150..193
                        }
                        else -> false
                    })
                }
                keys.contains("hcl") -> {
                    val hcl = map["hcl"]
                    result.add(hcl?.get(0) == '#')
                }
                keys.contains("ecl") -> {
                    result.add(when (map["ecl"]) {
                        "amb", "blu", "brn", "gry", "grn", "hzl", "oth" -> true
                        else -> false
                    })
                }
                keys.contains("pid") -> {
                    result.add(map["pid"]?.length == 9)
                }
            }
        }
        return result.all { it }
    }

    private fun countValidPassport(passports: List<List<String>>) =
        passports.filter { isValidPassport(it) }.count()

    private fun isValidPassport(passport: List<String>): Boolean {
        if (passport.size == 8) return true
        if (passport.size == 7 && !hasCid(passport)) return true
        return false
    }

    private fun hasCid(passport: List<String>): Boolean {
        for (s in passport)
            if (s.indexOf("cid") != -1) return true
        return false
    }

    private fun assemble(lines: List<String>): List<String> {
        var output = mutableListOf<String>()
        var cur: String? = null
        for (i in lines.indices) {
            if (lines[i].trim().isEmpty()) {
                output.add(cur.toString())
                cur = null
            } else {
                if (cur == null)
                    cur = lines[i].trim()
                else
                    cur += " " + lines[i].trim()
            }

        }

        if (cur != null)
            output.add(cur.toString())

        return output
    }
}

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val solution = Solution()
        solution.solve()
    }
}