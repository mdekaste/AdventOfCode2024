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
import java.awt.geom.Point2D
import kotlin.collections.plus
import kotlin.math.absoluteValue
import kotlin.math.max

fun main() {
    Day9.part1()
    Day9.part2()
}

object Day9 : AdventOfCode({
    val parsed: List<Point> = input.lines().map { it.split(",").let { it.last().toInt() to it.first().toInt() } }
    part1 {
        parsed.flatMapIndexed { row, point -> parsed.drop(row + 1).map { row2 -> point to row2 } }.maxOf { (p1, p2) ->
            val (x1, y1) = p1
            val (x2, y2) = p2
            ((x2 - x1).absoluteValue + 1L) * ((y2 - y1).absoluteValue + 1L)
        }
    }

    fun Point.directionTo(other: Point): Point {
        val dx = (other.x - this.x).coerceIn(-1, 1)
        val dy = (other.y - this.y).coerceIn(-1, 1)
        return dy to dx
    }

    part2 {

    }

    val NORTH = 1 to 0
    val SOUTH = -1 to 0
    val EAST = 0 to 1
    val WEST = 0 to -1

    part2{
        var a = 0L
        var b = 0L

        for (i in parsed.indices) {
            for (j in i + 1 until parsed.size) {
                val (x1, y1) = parsed[i]
                val (u1, v1) = parsed[j]

                val x = minOf(x1, u1)
                val u = maxOf(x1, u1)
                val y = minOf(y1, v1)
                val v = maxOf(y1, v1)

                val size = (u - x + 1L) * (v - y + 1L)

                a = max(a, size)

                var foundIntersection = false
                val TPlusFirst = parsed + parsed[0]
                for (k in 0 until TPlusFirst.size - 1) {
                    val (p1, q1) = TPlusFirst[k]
                    val (r1, s1) = TPlusFirst[k + 1]

                    val p = minOf(p1, r1)
                    val r = maxOf(p1, r1)
                    val q = minOf(q1, s1)
                    val s = maxOf(q1, s1)

                    if (x < r && u > p && y < s && v > q) {
                        foundIntersection = true
                        break
                    }
                }

                if (!foundIntersection) {
                    b = max(b, size)
                }
            }
        }

        b
    }

//    part2 {
//        val directions = (parsed + parsed.first()).zipWithNext { p1, p2 -> p1.directionTo(p2) }
//        val initialPoly = Polygon()
//        for (point in parsed) {
//            initialPoly.addPoint(point.x, point.y)
//        }
//
//        val ultraPoly = parsed.flatMapIndexed { index, (y, x) ->
//            val preDir: Point = directions[(index - 1 + directions.size) % directions.size]
//            val postDir: Point = directions[index]
//
//            val northEastCorner = Point2D.Double(x.toDouble() + 0.5, y.toDouble() + 0.5)
//            val southEastCorner = Point2D.Double(x.toDouble() + 0.5, y.toDouble() - 0.5)
//            val southWestCorner = Point2D.Double(x.toDouble() - 0.5, y.toDouble() - 0.5)
//            val northWestCorner = Point2D.Double(x.toDouble() - 0.5, y.toDouble() + 0.5)
//
//            when {
//                preDir == NORTH && postDir == EAST && initialPoly.contains(southEastCorner) -> listOf(southWestCorner, northWestCorner, northEastCorner)
//                preDir == NORTH && postDir == EAST && initialPoly.contains(northWestCorner) -> listOf(southEastCorner)
//
//                preDir == NORTH && postDir == WEST && initialPoly.contains(southWestCorner) -> listOf(southEastCorner, northEastCorner, northWestCorner)
//                preDir == NORTH && postDir == WEST && initialPoly.contains(northEastCorner) -> listOf(southWestCorner)
//
//                preDir == EAST && postDir == NORTH && initialPoly.contains(northWestCorner) -> listOf(southWestCorner, southEastCorner, northEastCorner)
//                preDir == EAST && postDir == NORTH && initialPoly.contains(southEastCorner) -> listOf(southEastCorner)
//
//                preDir == EAST && postDir == SOUTH && initialPoly.contains(southWestCorner) -> listOf(northWestCorner, northEastCorner, southWestCorner)
//                preDir == EAST && postDir == SOUTH && initialPoly.contains(northEastCorner) -> listOf(southWestCorner)
//
//                preDir == SOUTH && postDir == EAST && initialPoly.contains(northEastCorner) -> listOf(northWestCorner, southWestCorner, southEastCorner)
//                preDir == SOUTH && postDir == EAST && initialPoly.contains(southWestCorner) -> listOf(northEastCorner)
//
//                preDir == SOUTH && postDir == WEST && initialPoly.contains(northWestCorner) -> listOf(northEastCorner, southEastCorner, southWestCorner)
//                preDir == SOUTH && postDir == WEST && initialPoly.contains(southEastCorner) -> listOf(northWestCorner)
//
//                preDir == WEST && postDir == NORTH && initialPoly.contains(northEastCorner) -> listOf(southEastCorner, southWestCorner, northWestCorner)
//                preDir == WEST && postDir == NORTH && initialPoly.contains(southWestCorner) -> listOf(northEastCorner)
//
//                preDir == WEST && postDir == SOUTH && initialPoly.contains(southEastCorner) -> listOf(northEastCorner, northWestCorner, southWestCorner)
//                preDir == WEST && postDir == SOUTH && initialPoly.contains(northWestCorner) -> listOf(southEastCorner)
//                else -> error("unhandled case for point $index at ($x, $y) with preDir $preDir and postDir $postDir")
//            }
//        }
//
//        val ultraPolygon = Polygon2D()
//        for (point in ultraPoly) {
//            ultraPolygon.add(point.x, point.y)
//        }
//
//        parsed.flatMapIndexed { row, point -> parsed.drop(row + 1).map { row2 -> point to row2 } }
//            .filter { (p1, p2) ->
//                ultraPolygon.contains(p1.x.toDouble(), p2.y.toDouble()) && ultraPolygon.contains(p2.x.toDouble(), p1.y.toDouble())
//            }
//            .maxOf { (p1, p2) ->
//                val (x1, y1) = p1
//                val (x2, y2) = p2
//                ((x2 - x1).absoluteValue + 1L) * ((y2 - y1).absoluteValue + 1L)
//            }
//
//    }
})

class Polygon2D {
    val points = mutableListOf<Point2D>()
    fun add(x: Double, y: Double) {
        points.add(Point2D.Double(x, y))
    }

    fun contains(x: Double, y: Double): Boolean {
        var hits = 0

        var lastx: Double = points.last().x
        var lasty: Double = points.last().y
        var curx: Double
        var cury: Double

        var i = 0
        while (i < points.size) {
            curx = points[i].x
            cury = points[i].y

            if (cury == lasty) {
                lastx = curx
                lasty = cury
                i++
                continue
            }

            val leftx: Double
            if (curx < lastx) {
                if (x >= lastx) {
                    lastx = curx
                    lasty = cury
                    i++
                    continue
                }
                leftx = curx
            } else {
                if (x >= curx) {
                    lastx = curx
                    lasty = cury
                    i++
                    continue
                }
                leftx = lastx
            }

            val test1: Double
            val test2: Double
            if (cury < lasty) {
                if (y < cury || y >= lasty) {
                    lastx = curx
                    lasty = cury
                    i++
                    continue
                }
                if (x < leftx) {
                    hits++
                    lastx = curx
                    lasty = cury
                    i++
                    continue
                }
                test1 = x - curx
                test2 = y - cury
            } else {
                if (y < lasty || y >= cury) {
                    lastx = curx
                    lasty = cury
                    i++
                    continue
                }
                if (x < leftx) {
                    hits++
                    lastx = curx
                    lasty = cury
                    i++
                    continue
                }
                test1 = x - lastx
                test2 = y - lasty
            }

            if (test1 < (test2 / (lasty - cury) * (lastx - curx))) {
                hits++
            }
            lastx = curx
            lasty = cury
            i++
        }

        return ((hits and 1) != 0)
    }
}