package year2024.day4

import core.AdventOfCode
import core.inputParsing.extractInts
import core.interval.Interval
import core.toGraph
import core.twoDimensional.*

fun main(){
    val day4 = Day4()
    day4.part1()
    day4.part2()
}



class Day4 : AdventOfCode({
    val parsed = input.lines().toGraph()

    fun Grid<String>.grow(origin: Point, amount: Int, vararg directions: Point): List<String> = buildList {
        for(direction in directions) {
            add(generateSequence(origin, direction::plus).take(amount).map(this@grow::get).joinToString(""))
        }
    }

    part1{
        parsed.keys.sumOf { key ->
            parsed.grow(key, 4, *WINDS).count("XMAS"::equals)
        }
    }


    part2{
        parsed.keys.count { key ->
            parsed.grow(key + NORTH_WEST, 3, SOUTH_EAST).any { it in listOf("SAM", "MAS") } &&
            parsed.grow(key + NORTH_EAST, 3, SOUTH_WEST).any { it in listOf("SAM", "MAS") }
        }
    }
})
