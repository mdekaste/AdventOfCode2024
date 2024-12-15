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
    val (grid, lines) = input.splitOnEmptyLine().let { (grid, line) -> grid.toGrid() to line.lines() }

    part1 {
        val grid = grid.toMutableMap()
        var position = grid.entries.first { it.value == '@' }.key
        for(line in lines){
            for(op in line){
                val dir = when(op){
                    '>' -> EAST
                    '<' -> WEST
                    '^' -> NORTH
                    'v' -> SOUTH
                    else -> error("")
                }
                val next = position + dir
                if(grid[next] == 'O'){
                   var box = next
                   while(grid[box + dir] == 'O'){
                       box += dir
                   }
                    when(grid[box + dir]){
                        '#' -> continue
                        '.' -> {
                            grid[box + dir] = 'O'
                            grid[position] = '.'
                            grid[next] = '@'
                        }
                    }
                }
                if(grid[next] == '#') continue
                if(grid[next] == '.'){
                    grid[position] = '.'
                    grid[next] = '@'
                }
                position = next
            }
        }
        grid.entries.filter { it.value == 'O' }.sumOf { it.key.y * 100 + it.key.x }
    }

    fun MutableMap<Point, Char>.move(origin: Point, dir: Point): Boolean{
        val character = getValue(origin)
        put(origin, '.')
        put(origin + dir, character)
        return true
    }

    fun MutableMap<Point, Char>.tryMove(origin: Point, dir: Point): Boolean {
        when(get(origin + dir)){
            '#' -> return false
            '.' -> return move(origin, dir)
            '[',']' -> {
                if(dir == NORTH || dir == SOUTH){
                    val visited = mutableSetOf(origin)
                    val frontier = LinkedHashSet(setOf(origin))
                    while(frontier.isNotEmpty()){
                        val current = frontier.removeFirst()
                        visited += current
                        when(getValue(current + dir)){
                            '#' -> return false
                            '.' -> continue
                            '[',']' -> {
                                frontier.add(current + dir)
                                if(getValue(current + dir) == '['){
                                    frontier += current + dir + EAST
                                } else {
                                    frontier += current + dir + WEST
                                }
                            }
                        }
                    }
                    if(dir == SOUTH){
                        visited.sortedByDescending { it.y }.forEach {
                            move(it, dir)
                        }
                    }
                    if(dir == NORTH){
                        visited.sortedBy { it.y }.forEach {
                            move(it, dir)
                        }
                    }
                    return true
                }
                if(tryMove(origin + dir, dir)){
                    return move(origin, dir)
                } else {
                    return false
                }
            }
            else -> error("")
        }
    }

    part2{
        val grid = input.replace("#", "##").replace(".", "..").replace("@", "@.").replace("O", "[]").toGrid().toMutableMap()
        var position = grid.entries.first { it.value == '@' }.key
        for(line in lines){
            for(op in line){
                val dir = when(op){
                    '>' -> EAST
                    '<' -> WEST
                    '^' -> NORTH
                    'v' -> SOUTH
                    else -> error("")
                }
                if(grid.tryMove(position, dir)){
                    position += dir
                }
            }
        }
        grid.entries.filter { it.value == '[' }.sumOf { it.key.y * 100 + it.key.x }
    }
})