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

    fun MutableMap<Point, Char>.specialMove(origin: Point, dir: Point): Boolean {
        val visited = LinkedHashSet<Point>()
        val frontier = ArrayDeque(listOf(origin))
        while(frontier.isNotEmpty()){
            val current = frontier.removeFirst()
            visited.add(current)
            when(getValue(current + dir)){
                '#' -> return false
                '[' -> {
                    frontier.add(current + dir)
                    frontier.add(current + dir + EAST)
                }
                ']' -> {
                    frontier.add(current + dir)
                    frontier.add(current + dir + WEST)
                }
            }
        }
        visited.reversed().forEach {
            move(it, dir)
        }
        return true
    }

    fun MutableMap<Point, Char>.tryMove(origin: Point, dir: Point): Boolean = when(get(origin + dir)){
        '.' -> move(origin, dir)
        'O' -> tryMove(origin + dir, dir) && move(origin, dir)
        '[', ']' -> when(dir){
            EAST, WEST -> tryMove(origin + dir, dir) && move(origin, dir)
            else -> specialMove(origin, dir)
        }
        else -> false
    }

    fun MutableMap<Point, Char>.solve(box: Char): Int {
        var position = entries.first{ it.value == '@' }.key
        for(op in ops){
            if(tryMove(position, op)){
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