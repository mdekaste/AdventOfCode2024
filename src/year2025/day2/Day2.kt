package year2025.day2

import core.AdventOfCode

fun main(){
    Day2.part1()
    Day2.part2()
}



object Day2 : AdventOfCode({
    val parsed = input
        .split(",")
        .map { it.split("-").let { (a,b) -> a.toLong()..b.toLong() } }

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