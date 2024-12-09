package year2024.day8

import core.AdventOfCode
import core.inputParsing.toGrid
import core.product
import core.twoDimensional.*

fun main(){
    val day8 = Day8()
    day8.solve(1000000)
}

class Day8 : AdventOfCode({
    val grid: Map<Point, Char> = input.toGrid()

    val antennaPairs: List<Segment> = grid.entries
        .filter { it.value != '.' }
        .groupBy ({ it.value }, { it.key })
        .values
        .flatMap { it.product() }

    fun solve(antinodeGenerator: (Segment) -> Sequence<Point>): Int = antennaPairs
        .flatMapTo(HashSet()){ antinodeGenerator(it) }.size

    part1 {
        solve { (a, b) -> sequenceOf(a * 2 - b, b * 2 - a).filter { it in grid } }
    }

    part2 {
        solve { (a, b) ->
            generateSequence(a) { it - b + a }.takeWhile { it in grid } +
            generateSequence(b) { it + b - a }.takeWhile { it in grid }
        }
    }
})