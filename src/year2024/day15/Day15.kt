package year2024.day15

import core.AdventOfCode
import core.inputParsing.splitOnEmptyLine
import core.inputParsing.toGrid
import core.twoDimensional.*

fun main(){
    val day15 = Day15()
    day15.solve()
}

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

    //Move Logic
    fun MutableMap<Point, Char>.move(origin: Point, dir: Point): Boolean {
        val character = getValue(origin)
        put(origin, '.')
        put(origin + dir, character)
        return true
    }

    fun MutableMap<Point, Char>.nextSet(set: Set<Point>, dir: Point): Set<Point>? = set.flatMapTo(mutableSetOf()) { origin ->
        when(get(origin + dir)){
            '#' -> return null
            '.' -> emptySet()
            '[' -> if(dir in setOf(NORTH, SOUTH)) setOf(origin + dir, origin + dir + EAST) else setOf(origin + dir)
            ']' -> if(dir in setOf(NORTH, SOUTH)) setOf(origin + dir, origin + dir + WEST) else setOf(origin + dir)
            else -> setOf(origin + dir)
        }
    }

    fun MutableMap<Point, Char>.tryMove(dir: Point, origins: Set<Point>): Boolean = when(val nextOrigins = nextSet(origins, dir)){
        null -> false
        emptySet<Point>() -> origins.all { move(it, dir) }
        else -> tryMove(dir, nextOrigins) && origins.all { move(it, dir) }
    }
    // Main algorithm
    fun MutableMap<Point, Char>.solve(box: Char): Int {
        var position = entries.first{ it.value == '@' }.key
        for(op in ops){
            if(tryMove(op, setOf(position))){
                position += op
            }
        }
        return entries.filter { it.value == box }.sumOf { it.key.y * 100 + it.key.x }
    }

    part1 {
        grid.toGrid().toMutableMap().solve('O')
    }

    part2{
        grid.replace("#", "##")
            .replace(".", "..")
            .replace("O", "[]")
            .replace("@", "@.")
            .toGrid().toMutableMap().solve('[')
    }
})