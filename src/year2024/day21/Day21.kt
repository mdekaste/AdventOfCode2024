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
    fun solve(input: String, size: Int): Long {
        return ("A" + input).zipWithNext { a, b -> solve(a, b, size, numberspad) }.sum()
    }
    data class State(val from: Char, val to: Char, val depth: Int, val keypad: Keypad)
    val memoization = mutableMapOf<State, Long>()
    fun solve(from: Char = 'A', to: Char, depth: Int, keypad: Keypad): Long = memoization.getOrPut(State(from, to, depth, keypad)){
        val start = keypad.entries.first { it.value == from }.key
        val target = keypad.entries.first { it.value == to }.key
        val routes = routes(start, target, keypad)
        if(depth == 0){
            routes.first().length.toLong()
        } else {
            routes.map { route ->
                ("A$route").zipWithNext{ a, b -> solve(a, b, depth - 1, directionpad) }.sum()
            }.min()
        }
    }


    override fun part1(): Any? {
        return parsed.map { line ->
           solve(line, 2) to line.extractInts()[0]
        }.onEach { println(it) }.sumOf { (route, size) -> route * size }
    }

    override fun part2(): Any? {
        return parsed.map { line ->
            solve(line, 25) to line.extractInts()[0]
        }.onEach { println(it) }.sumOf { (route, size) -> route * size }
    }
}