package year2024.day0

import core.*
import core.twoDimensional.MutableGrid
import core.twoDimensional.Point


fun main() {
    Day0().part1().let(::println)
    Day0().part2().let(::println)
    Day0().solve().let(::println)
}

class Day0 : Challenge("This day serves as an example and template") {
    val parsed = input.lines()
    override fun part1(): Any? {
        val grid: MutableGrid<Char> = mutableMapOf<Point, Char>().apply {
            parsed.forEachIndexed { y, line ->
                line.forEachIndexed { x, c ->
                    this[y to x] = c
                }
            }
        }

        return "Hello, World! " + parsed[0]
    }

    override fun part2(): Any? {
        return "Goodbye, World! " + parsed[1]
    }
}