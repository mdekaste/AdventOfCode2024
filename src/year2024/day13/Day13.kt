package year2024.day13

import core.AdventOfCode
import core.gcd
import core.inputParsing.extractInts
import core.inputParsing.extractLongs
import core.inputParsing.splitOnEmptyLine
import core.lcm
import core.twoDimensional.*
import java.util.*

fun main(){
    val day13 = Day13()
    day13.solve()
}



class Day13 : AdventOfCode({
    data class Input(val ax: Long, val ay: Long, val bx: Long, val by: Long, val prizeX: Long, val prizeY: Long)
    val parsed = Regex("""-?\d+""")
        .findAll(input)
        .map { it.value.toLong() }
        .chunked(6)
        .map { Input(it[0], it[1], it[2], it[3], it[4], it[5]) }

    fun Input.winPrizeInFewestMoves(): Long? = Pair(
        first = (ax * prizeY - ay * prizeX) / (ax * by - ay * bx),
        second = (bx * prizeY - by * prizeX) / (bx * ay - by * ax)
    ).takeIf { (a, b) -> (ay * a + by * b == prizeY) && (ax * a + bx * b == prizeX) }
        ?.let { (a, b) -> 3 * a + b }

    part1{
        parsed.mapNotNull(Input::winPrizeInFewestMoves)
            .sum()
    }

    part2{
        parsed.map { it.copy(prizeX = it.prizeX + 10000000000000, prizeY = it.prizeY + 10000000000000) }
            .mapNotNull(Input::winPrizeInFewestMoves)
            .sum()
    }
})
