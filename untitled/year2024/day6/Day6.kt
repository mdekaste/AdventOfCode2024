package year2024.day6

import core.AdventOfCode
import core.inputParsing.toGrid
import core.twoDimensional.*

fun main(){
    val day6 = Day6()
    day6.solve()
}

class Day6: AdventOfCode({
    val grid = input.toGrid()
    val guard = grid.entries.first { it.value == '^' }.key

    fun solve(obstacle: Point? = null): Set<Point>? = buildMap<Point, MutableSet<Point>>{
        var cur = guard
        var dir = NORTH
        while(grid[cur] != null){
            when{
                !getOrPut(cur, ::mutableSetOf).add(dir) -> return null
                cur + dir == obstacle || grid[cur + dir] == '#' -> dir = dir.right()
                else -> cur += dir
            }
        }
    }.keys

    part1{
        solve()?.size
    }

    part2{
        solve()?.drop(1)?.count { solve(it) == null }
    }
})