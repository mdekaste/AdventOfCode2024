package year2024.day20

import core.AdventOfCode
import core.groupingBy
import core.inputParsing.toGrid
import core.twoDimensional.*
import kotlin.math.absoluteValue

fun main(){
    val day20 = Day20()
    day20.solve()
}

class Day20 : AdventOfCode({
    val parsed = input.toGrid()
    val start = parsed.entries.first { it.value == 'S' }.key

    fun firstMatch(prevPos: Point? = null, curPos: Point) = curPos
        .cardinals()
        .firstOrNull { parsed[it] != '#' && it != prevPos }
        ?.let { curPos to it }

    val path = generateSequence(null as Point? to start) { (prev, cur) -> firstMatch(prev, cur) }
        .map { it.second }
        .withIndex()
        .associate { it.value to it.index }

    fun solve(cheatTime: Int, saveTime: Int) = path
        .keys
        .sumOf { cur -> path.keys
            .count { it.manhattenDistance(cur) in 2..cheatTime && path.getValue(it) - path.getValue(cur) - it.manhattenDistance(cur) >= saveTime }
        }


    part1{
        solve(2, 100)
    }

    part2{
        solve(20, 100)
    }
})