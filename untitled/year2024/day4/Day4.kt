package year2024.day4

import core.AdventOfCode
import core.inputParsing.toGrid
import core.toGraph
import core.twoDimensional.*

fun main(){
    val day4 = Day4()
    day4.solve()
}

class Day4 : AdventOfCode({
    with(input.toGrid()){
        fun grow(o1: Point, dir: Point, amount: Int): String = o1.extend(dir, amount).map(::get).joinToString("")
        part1{
            keys.sumOf { key ->
                WINDS.count { wind -> grow(key, wind, 4) == "XMAS" }
            }
        }
        part2{
            keys.count { key ->
                grow(key + NORTH_WEST, SOUTH_EAST, 3) in listOf("SAM", "MAS") &&
                grow(key + NORTH_EAST, SOUTH_WEST, 3) in listOf("SAM", "MAS")
            }
        }
    }
})
