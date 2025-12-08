package year2025.day7

import core.AdventOfCode
import core.groupingBy
import core.inputParsing.toGrid
import core.merge
import core.twoDimensional.Grid
import core.twoDimensional.Point
import core.twoDimensional.east
import core.twoDimensional.south
import core.twoDimensional.west

fun main(){
    Day7.part1()
    Day7.part2()
}

object Day7 : AdventOfCode({
    val parsed = input.toGrid()
    val startPos = parsed.entries.first{ it.value == 'S' }.key

    data class Aggregate(val frontier: Map<Point, Long> = mapOf(startPos to 1L), val splits: Int = 0)

    fun Aggregate.next(): Aggregate? = copy(
        frontier = buildMap {
            frontier.forEach { (pos, count) ->
                val next = pos.south()
                when (parsed[next]) {
                    '^' -> listOf(next.west(), next.east()).forEach {
                        merge(it, count, Long::plus)
                    }
                    '.' -> merge(next, count, Long::plus)
                }
            }
        },
        splits = splits + frontier.count { parsed[it.key.south()] == '^' }
    ).takeIf { it.frontier.isNotEmpty() }

    val result = generateSequence(Aggregate(), Aggregate::next).last()

    part1{
        result.splits
    }

    part2{
        result.frontier.values.sum()
    }
})