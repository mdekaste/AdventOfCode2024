package year2024.day19

import core.AdventOfCode
import core.inputParsing.splitOnEmptyLine

fun main(){
    val day19 = Day19()
    day19.solve()

}

class Day19 : AdventOfCode({
    val (towels, designs) = input.splitOnEmptyLine().let { (a,b) -> a.split(", ").toSet() to b.lines() }

    val memoization = mutableMapOf("" to 1L)
    fun possibleWays(design: String): Long = memoization.getOrPut(design){
        design.indices
            .filter { design.take(it + 1) in towels }
            .sumOf { possibleWays(design.drop(it + 1)) }
    }

    part1{
        designs.count { possibleWays(it) > 0 }
    }

    part2{
        designs.sumOf { possibleWays(it) }
    }
})