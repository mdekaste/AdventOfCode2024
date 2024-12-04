package year2024.day4

import core.AdventOfCode
import core.toGraph
import core.twoDimensional.*

fun main(){
    val day4 = Day4()
    day4.solve(100)
}


fun Grid<*>.grow(o1: Point, dir: Point, amount: Int): String = List(amount){ get(o1 + dir * it) }.joinToString("")
class Day4 : AdventOfCode({
    val parsed = input.lines().toGraph()

    part1{
        parsed.keys.sumOf { key ->
            WINDS.count { wind -> parsed.grow(key, wind, 4) == "XMAS" }
        }
    }


    part2{
        parsed.keys.count { key ->
            parsed.grow(key + NORTH_WEST, SOUTH_EAST, 3) in listOf("SAM", "MAS") &&
            parsed.grow(key + NORTH_EAST, SOUTH_WEST, 3) in listOf("SAM", "MAS")
        }
    }
})
