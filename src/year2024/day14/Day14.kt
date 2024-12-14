package year2024.day14

import core.AdventOfCode
import core.inputParsing.extractInts
import core.merge
import core.twoDimensional.*
import java.io.File

fun main(){
    val day14 = Day14()
    day14.solve()
}

const val HEIGHT = 103
const val WIDTH = 101
class Day14 : AdventOfCode({
    val parsed: Map<Point, List<Point>> = input.lines().map {
        it.extractInts().let { (p1, p2, v1, v2) -> (p2 to p1) to (v2 to v1) }
    }.groupBy({it.first}, {it.second})

    fun robotGenerator(): Sequence<Map<Point, List<Point>>> = generateSequence(parsed){ grid ->
        grid.entries
            .flatMap { (key, values) -> values.map { (it + key) % (HEIGHT to WIDTH) to it } }
            .groupBy({it.first}, {it.second})
    }

    fun Map<Point, List<Point>>.subdivide(): List<Map<Point, List<Point>>>{
        val verticalslice = WIDTH / 2
        val horizontalslice = HEIGHT / 2
        val northwest = filterKeys { it.x < verticalslice && it.y < horizontalslice }
        val northeast = filterKeys { it.x > verticalslice && it.y < horizontalslice }
        val southwest = filterKeys { it.x < verticalslice && it.y > horizontalslice }
        val southeast = filterKeys { it.x > verticalslice && it.y > horizontalslice }
        return listOf(northwest, northeast, southwest, southeast)
    }

    part1{
       robotGenerator().take(101).last().subdivide().map { it.values.sumOf { it.size } }.reduce(Int::times)
    }

    part2{
        robotGenerator().withIndex().firstOrNull { (_, value) -> value.values.all { it.size <= 1 } }?.index
    }
})