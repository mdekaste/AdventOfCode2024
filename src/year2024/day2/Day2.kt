package year2024.day2

import core.AdventOfCode
import core.inputParsing.extractInts

fun main(){
    val day2 = Day2()
    day2.part1()
    day2.part2()
}

/** Going to do a builder pattern from now on I think */
class Day2: AdventOfCode({
    val parsed = input.lines().map { it.extractInts() }

    part1{
        parsed.filter {
            val list = it.zipWithNext { a, b -> b - a }
            (list.all { it >= 0 } && list.all { it in 1..3 }) || (list.all { it <= 0 } && list.all { it in -3..-1})
        }.count()
    }

    fun isSafe(list: List<Int>): Boolean {
        val list = list.zipWithNext { a, b -> b - a }
        return (list.all { it >= 0 } && list.all { it in 1..3 }) || (list.all { it <= 0 } && list.all { it in -3..-1})
    }

    part2{
        parsed.filter { list ->
            isSafe(list) || list.mapIndexed { index, i -> list.subList(0, index) + list.subList(index + 1, list.size) }.any{ isSafe(it) }
        }.count()
    }
})
