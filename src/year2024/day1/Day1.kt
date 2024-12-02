package year2024.day1

import core.*
import core.inputParsing.extractInts
import core.interval.IntIncrementable.mutableIntervalMapOf
import kotlin.math.abs

fun main(){
    Day1().part1()
    Day1().part2()
}

class Day1 : AdventOfCode({
    val (left, right) = input.lines()
        .map { it.extractInts() }
        .unzip()
        .map { it.sorted() }

    part1{
        left.zip(right).sumOf { (l, r) -> abs(l - r) }
    }

    part2{
        val leftCount = left.groupingBy().reduce(Int::plus)
        right.sumOf { leftCount.getOrDefault(it, 0) }
    }

})