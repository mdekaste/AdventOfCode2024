package year2024.day15

import core.AdventOfCode
import core.inputParsing.splitOnEmptyLine
import core.inputParsing.toGrid
import core.inputParsing.toMutableGrid
import core.twoDimensional.*

fun main(){
    val day15 = Day15()
    day15.solve(10)
}
//Move Logic
context(MutableMap<Point, Char>)
fun Point.move(dir: Point): Boolean = put(this + dir, remove(this)!!) == null

context(MutableMap<Point, Char>)
private fun Set<Point>.nextSet(dir: Point): Set<Point>? = map(dir::plus).flatMapTo(mutableSetOf()) { destination ->
    when (get(destination)) {
        null -> emptyList()
        '#' -> return null
        '[' if dir in setOf(NORTH, SOUTH) -> listOf(destination, destination + EAST)
        ']' if dir in setOf(NORTH, SOUTH) -> listOf(destination, destination + WEST)
        else -> listOf(destination)
    }
}

context(MutableMap<Point, Char>)
private fun Set<Point>.move(dir: Point): Boolean = when(val nextOrigins = nextSet(dir)){
    null -> false
    emptySet<Point>() -> all{ it.move(dir) }
    else if nextOrigins.move(dir) -> all{ it.move(dir) }
    else -> false
}

context(MutableMap<Point, Char>)
private fun Point.moveRobot(dir: Point): Point = if(setOf(this).move(dir)) this + dir else this

class Day15 : AdventOfCode({
    //Input parsing
    fun Char.toMove() = when(this){
        '>' -> EAST
        '<' -> WEST
        '^' -> NORTH
        'v' -> SOUTH
        else -> error("invalid character in moves")
    }
    fun List<String>.toMoves() = flatMap { line -> line.map(Char::toMove) }
    val (grid, ops) = input.splitOnEmptyLine().let { (grid, line) -> grid to line.lines().toMoves() }

    fun MutableMap<Point, Char>.solve(box: Char): Int {
        var position = entries.first{ it.value == '@' }.key
        for(op in ops){
            position = position.moveRobot(op)
        }
        return entries.filter { it.value == box }.sumOf { it.key.y * 100 + it.key.x }
    }

    part1 {
        grid.toMutableGrid('.').solve('O')
    }

    part2{
        grid.replace("#", "##")
            .replace(".", "..")
            .replace("O", "[]")
            .replace("@", "@.")
            .toMutableGrid('.').solve('[')
    }
})