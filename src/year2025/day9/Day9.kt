package year2025.day9

import core.AdventOfCode
import core.twoDimensional.EAST
import core.twoDimensional.NORTH
import core.twoDimensional.Point
import core.twoDimensional.SOUTH
import core.twoDimensional.WEST
import core.twoDimensional.x
import core.twoDimensional.y
import java.awt.Polygon
import java.awt.geom.Line2D
import java.awt.geom.Point2D
import kotlin.collections.plus
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min

fun main() {
    Day9.part1()
    Day9.part2()
}

object Day9 : AdventOfCode({
    fun Pair<Point, Point>.sort(): Pair<Point, Point> = Pair(
        Point(min(first.x, second.x), min(first.y, second.y)),
        Point(max(first.x, second.x), max(first.y, second.y))
    )

    val coordinates: List<Point> = input
        .lines()
        .map { it.split(",").map(String::toInt).let { (x, y) -> y to x } }

    val rectangles = coordinates
        .flatMapIndexed { row, point -> coordinates.drop(row + 1).map { row2 -> point to row2 } }
        .map { it.sort() }

    val polygonLines = coordinates
        .plus(coordinates.first())
        .zipWithNext()
        .map { it.sort() }

    part1 {
        rectangles.maxOf { (p1, p2) ->
            (p2.y - p1.y + 1L) * (p2.x - p1.x + 1L)
        }
    }

    part2 {
        rectangles.filter { (p1, p2) ->
            polygonLines.none { (l1, l2) ->
                p1.x < l2.x && p2.x > l1.x && p1.y < l2.y && p2.y > l1.y
            }
        }.maxOf { (p1, p2) ->
            (p2.y - p1.y + 1L) * (p2.x - p1.x + 1L)
        }
    }
})