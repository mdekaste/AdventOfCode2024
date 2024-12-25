package year2024.day25

import core.AdventOfCode
import core.Challenge
import core.inputParsing.splitOnEmptyLine
import core.inputParsing.toGrid
import core.twoDimensional.ORIGIN
import core.twoDimensional.Point

fun main(){
    Day25().solve().let(::println)
}

typealias KeyOrLock = Pair<Boolean, List<Int>>
val KeyOrLock.isKey get() = first
val KeyOrLock.height get() = second

class Day25 : Challenge(){
    val parsed = input.splitOnEmptyLine().map(String::toGrid)

    private fun Map<Point, Char>.column(x: Int, type: Char): Int =
        generateSequence(0, Int::inc)
            .map { get(it to x) }
            .takeWhile { it == type }
            .count()
            .let { if(type == '.') 6 - it else it - 1 }

    private fun Map<Point, Char>.toKeyOrLock(): KeyOrLock = getValue(ORIGIN).let {
        KeyOrLock(
            it == '.',
            List(5){ x -> column(x, it) }
        )
    }

    override fun part1() = parsed
        .map { it.toKeyOrLock() }
        .partition(KeyOrLock::isKey)
        .let { (keys, locks) ->
            keys.sumOf { key ->
                locks.count { lock ->
                    key.height.zip(lock.height).all { (k, l) -> k + l <= 5 }
                }
            }
        }

    override fun part2() = "Merry Christmas Everyone!"
}