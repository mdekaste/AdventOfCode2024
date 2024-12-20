package year2024.day20

import core.AdventOfCode
import core.inputParsing.toGrid
import core.twoDimensional.*

fun main(){
    val day20 = Day20()
    day20.solve(1000)
}

class Day20 : AdventOfCode({
    val path = buildList {
        val parsed = input.toGrid()
        var prev: Point? = null
        var cur: Point = parsed.entries.first { it.value == 'S' }.key
        while(add(cur)){
            val next = cur.cardinals().firstOrNull { parsed[it] != '#' && it != prev } ?: break
            prev = cur
            cur = next
        }
    }

    fun solve(cheatTime: Int, saveTime: Int) = path.withIndex().sumOf { (index, point) ->
        path.subList(index, path.size).withIndex().count { (oIndex, oPoint) ->
            val distance = point.manhattanDistance(oPoint)
            distance in 2..cheatTime && oIndex - distance >= saveTime
        }
    }


    part1{
        solve(2, 100)
    }

    part2{
        solve(20, 100)
    }
})