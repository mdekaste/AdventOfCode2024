package year2024.day21

import core.AdventOfCode
import core.Challenge
import core.inputParsing.extractInts
import core.inputParsing.toGrid
import core.twoDimensional.EAST
import core.twoDimensional.NORTH
import core.twoDimensional.ORIGIN
import core.twoDimensional.Point
import core.twoDimensional.SOUTH
import core.twoDimensional.Segment
import core.twoDimensional.WEST
import core.twoDimensional.cardinals
import core.twoDimensional.plus
import core.twoDimensional.x
import core.twoDimensional.y

fun main(){
    val day21 = Day21()
    day21.part1().let(::println)
    day21.part2().let(::println)
}
typealias Keypad = Map<Point, Char>
class Day21 : Challenge(){
    val parsed = input.lines()

    val numberspad = """
        789
        456
        123
        -0A
    """.trimIndent().toGrid()

    val directionpad = """
        -^A
        <v>
    """.trimIndent().toGrid()

    fun routes(start: Point, target: Point, keypad: Keypad): List<String> {
        val yDiff = target.y - start.y
        val xDiff = target.x - start.x
        val yDir = if(yDiff > 0) SOUTH else NORTH
        val xDir = if(xDiff > 0) EAST else WEST
        var frontier = listOf(start to listOf<Point>())
        while(true){
            val nextFrontier = mutableListOf<Pair<Point, List<Point>>>()
            for((end, path) in frontier){
                if(end.x != target.x){
                    val result = end + xDir
                    if(keypad[result] != '-')
                        nextFrontier.add(result to path + xDir)
                }
                if(end.y != target.y){
                    val result = end + yDir
                    if(keypad[result] != '-')
                        nextFrontier.add(result to path + yDir)
                }
            }
            if(nextFrontier.isEmpty()){
                break
            }
            frontier = nextFrontier
        }
        return frontier.map { it.second.map {
            when(it){
                NORTH -> '^'
                SOUTH -> 'v'
                EAST -> '>'
                WEST -> '<'
                else -> error("invalid direction")
            }
        }.joinToString("", postfix = "A") }
    }
    fun String.sizeOfCommands(depth: Int, first: Boolean = true): Long = "A$this".zipWithNext { a, b -> State(a, b, depth).solve(first) }.sum()

    data class State(val from: Char, val to: Char, val depth: Int)
    val memoization = mutableMapOf<State, Long>()
    fun State.solve(first: Boolean): Long = memoization.getOrPut(this){
        val keypad = if(first) numberspad else directionpad
        val routes = routes(keypad)
        when(depth){
            0 -> routes.first().length.toLong()
            else -> routes.minOf { it.sizeOfCommands(depth - 1, false) }
        }
    }

    fun State.routes(keypad: Keypad): List<String> {
        val start = keypad.entries.first { it.value == from }.key
        val target = keypad.entries.first { it.value == to }.key
        return routes(start, target, keypad)
    }

    fun List<String>.solve(size: Int) = sumOf { it.sizeOfCommands(size) * it.extractInts()[0] }
    override fun part1(): Any? = parsed.solve(2)
    override fun part2(): Any? = parsed.solve(25)
}