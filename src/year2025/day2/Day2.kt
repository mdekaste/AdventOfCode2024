package year2025.day2

import core.AdventOfCode

fun main(){
    Day2.part1()
    Day2.part2()
}


object Day2 : AdventOfCode({
    fun String.toLongRange() = split("-")
        .map(String::toLong)
        .let { (a,b) -> a..b }

    val parsed = input
        .split(",")
        .map(String::toLongRange)

    fun solve(regex: Regex) = parsed.flatMap { range ->
        range.filter { number -> number.toString().matches(regex) }
    }.sum()

    part1{
        solve("""(\d+)\1""".toRegex())
    }

    part2{
        solve( """(\d+)\1+""".toRegex())
    }
})