package year2025.day0

import core.AdventOfCode
import core.inputParsing.toGrid
import core.twoDimensional.Point
import core.twoDimensional.cardinals

fun main() {
    val day10 = Day0()
    day10.solve(1000000)
}

class Day0 : AdventOfCode({
    val parsed = input
        .toGrid()
        .mapValues { (_, v) -> v.digitToInt() }

    fun solve(currentPoint: Point, visited: List<Point> = emptyList(), currentValue: Int = 0): List<Point> = when {
        currentPoint in visited -> listOf()
        currentValue == 9 -> listOf(currentPoint)
        else -> currentPoint
            .cardinals()
            .filter { parsed[it]?.minus(currentValue) == 1 }
            .flatMap { solve(it, visited + currentPoint, currentValue + 1) }
    }

    val solutionPerStart = parsed.filter { it.value == 0 }.keys.map(::solve)

    part1 {
        solutionPerStart.sumOf { it.distinct().size }
    }

    part2 {
        solutionPerStart.sumOf { it.size }
    }
})