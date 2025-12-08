package year2024.day11

import core.AdventOfCode
import core.inputParsing.extractInts
import core.inputParsing.extractLongs
import java.util.*
import kotlin.math.log10

fun main(){
    val day11 = Day11()
    day11.solve(10000000)
}

class Day11 : AdventOfCode({
    val parsed = input.extractLongs()

    fun Long.isEvenSize() = toString().length % 2 == 0
    fun Long.split() = toString().run { chunked(length / 2).map(String::toLong) }

    val memory = mutableMapOf<Pair<Long, Int>, Long>()
    fun depth(item: Long, curDepth: Int): Long = memory.getOrPut(item to curDepth) {
        when {
            curDepth == 0 -> 0
            item == 0L -> depth(1, curDepth - 1)
            item.isEvenSize() -> item.split().let { (left, right) -> 1 + depth(left, curDepth - 1) + depth(right, curDepth - 1) }
            else -> depth(item * 2024, curDepth - 1)
        }
    }
    part1{
        parsed.sumOf { depth(it, 25) } + parsed.size
    }

    part2{
        parsed.sumOf { depth(it, 75) } + parsed.size
    }
})