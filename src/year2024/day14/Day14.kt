package year2024.day14

import core.AdventOfCode
import core.groupingBy
import core.inputParsing.extractInts
import core.twoDimensional.*

fun main(){
    val day14 = Day14()
    day14.solve()
}

const val HEIGHT = 103
const val WIDTH = 101
class Day14 : AdventOfCode({
    val parsed: List<Segment> = input.lines().map {
        it.extractInts().let { (p1, p2, v1, v2) -> (p2 to p1) to (v2 to v1) }
    }

    fun robotGenerator(): Sequence<List<Segment>> = generateSequence(parsed){ segments ->
        segments.map { (point, velocity) -> (point + velocity) % (HEIGHT to WIDTH) to velocity }
    }

    part1{
       robotGenerator().take(101).last().run {
           listOf(
               count { (point, _) -> point.x < WIDTH / 2 && point.y < HEIGHT / 2 },
               count { (point, _) -> point.x > WIDTH / 2 && point.y < HEIGHT / 2 },
               count { (point, _) -> point.x < WIDTH / 2 && point.y > HEIGHT / 2 },
               count { (point, _) -> point.x > WIDTH / 2 && point.y > HEIGHT / 2 }
           ).reduce(Int::times)
       }
    }

    part2{
        robotGenerator().withIndex().firstOrNull { (_, segments) ->
            segments.distinctBy { it.first } == segments
        }?.index
    }
})