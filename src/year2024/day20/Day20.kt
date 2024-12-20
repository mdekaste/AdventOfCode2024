package year2024.day20

import core.AdventOfCode
import core.inputParsing.toGrid
import core.twoDimensional.*
import java.util.LinkedList

fun main(){
    val day20 = Day20()
    day20.solve(100)
}

typealias Step = Pair<Point?, Point>
class Day20 : AdventOfCode({
    val grid = input.toGrid()
    val start = grid.entries.first { it.value == 'S' }.key

    fun Step.next(): Step? = second.cardinals().firstOrNull { grid[it] != '#' && it != first }?.let { second to it }
    val path = generateSequence(null to start, Step::next).mapTo(mutableListOf(), Step::second)

    fun solve(cheatTime: Int, saveTime: Int) = path.withIndex().sumOf { (index, point) ->
        path.subList(index, path.size).withIndex().count { (oIndex, oPoint) ->
            point.manhattanDistance(oPoint).let { it in 2..cheatTime && oIndex - it >= saveTime }
        }
    }

    part1{
        solve(2, 100)
    }

    part2{
        solve(20, 100)
    }
})