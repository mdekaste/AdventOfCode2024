package year2024.day2

import core.AdventOfCode
import core.inputParsing.extractInts
import jdk.internal.util.random.RandomSupport.AbstractSpliteratorGenerator.ints
import kotlin.collections.filterIndexed
import kotlin.collections.lastIndex
import kotlin.coroutines.EmptyCoroutineContext.fold
import kotlin.math.abs
import kotlin.math.sign

fun main(){
    val day2 = Day2()
    day2.part1()
    day2.part2()
}

/** Going to do a builder pattern from now on I think */
class Day2: AdventOfCode({
    val parsed = input.lines().map { it.extractInts() }
    part1{
        parsed.count(List<Int>::isSafe)
    }
    part2{
        parsed.count { line -> line.indices.any { i -> line.filterIndexed { i2, _ -> i2 != i }.isSafe() } }
    }
})
private fun List<Int>.isSafe() = zipWithNext{ a, b -> b - a }.let{ it.all{ it in 1..3 } || it.all{ it in -3..-1 } }