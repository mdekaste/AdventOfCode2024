package year2024.day10

import core.AdventOfCode
import core.inputParsing.toGrid
import core.twoDimensional.Point
import core.twoDimensional.cardinals

fun main(){
    val day10 = Day10()
    day10.solve(1000000)
}

class Day10: AdventOfCode({
    class Memory(val digit: Int){
        val visited = mutableMapOf<Point, Int>()
    }

    val parsed = input
        .toGrid()
        .mapValues { Memory(it.value.digitToInt()) }

    for((start, _) in parsed.filter { it.value.digit == 0 }){
        val frontier = ArrayDeque<Point>().apply { add(start) }
        while(frontier.isNotEmpty()){
            val cur = frontier.removeFirst()
            for(neighbour in cur.cardinals()){
                if(neighbour in parsed && parsed.getValue(neighbour).digit - parsed.getValue(cur).digit == 1){
                    parsed.getValue(neighbour).visited.merge(start, 1, Int::plus)
                    frontier.add(neighbour)
                }
            }
        }
    }

    val ends = parsed.values.filter { it.digit == 9 }

    part1 {
        ends.sumOf { it.visited.size }
    }

    part2 {
        ends.sumOf { it.visited.values.sum() }
    }
})