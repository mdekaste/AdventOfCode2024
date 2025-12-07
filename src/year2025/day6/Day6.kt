package year2025.day6

import core.AdventOfCode
import core.inputParsing.splitOnEmptyLine
import core.unzip
import javax.swing.Spring.height

fun main() {
    Day6.part1()
    Day6.part2()
}

object Day6 : AdventOfCode({
    part1 {
        input.lines()
            .map { it.split("""\s+""".toRegex()) }
            .unzip()
            .sumOf { (a, b, c, d, op) ->
                when (op) {
                    "+" -> a.toLong() + b.toLong() + c.toLong() + d.toLong()
                    else -> a.toLong() * b.toLong() * c.toLong() * d.toLong()
                }
            }
    }
    part2 {
        fun List<String>.flipDiagonal(): List<String> = first().indices.map { x ->
            indices.map { y -> get(y)[x] }.joinToString("").trim()
        }

        data class Aggregate(val sum: Long, val agg: Long, val op: Char)

        fun Aggregate.submit(line: String): Aggregate = when {
            line.isEmpty() -> copy(sum = sum + agg, agg = 0)
            !line.last().isDigit() -> copy(agg = line.dropLast(1).trim().toLong(), op = line.last())
            else -> copy(agg = if (op == '*') agg * line.toLong() else agg + line.toLong())
        }

        input.lines()
            .flipDiagonal()
            .fold(Aggregate(0, 0, '-'), Aggregate::submit)
            .let { it.sum + it.agg }
    }
})