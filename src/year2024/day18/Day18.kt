package year2024.day18

import core.AdventOfCode
import core.bfs
import core.inputParsing.extractInts
import core.twoDimensional.*
import java.util.PriorityQueue
import kotlin.math.absoluteValue

fun main(){
    val day18 = Day18()
    day18.solve()
}

class Day18 : AdventOfCode({
    val parsed = input.lines().map { it.extractInts() }.map { (x, y) -> x to y }

    fun floodFill(points: Set<Point>): Int? {
        var frontier = setOf(ORIGIN)
        val visited = mutableSetOf<Point>()
        var iteration = 0
        while(frontier.isNotEmpty()){
            frontier = buildSet<Point> {
                for(point in frontier){
                    for(neighbour in point.cardinals()){
                        if(neighbour.x in 0..70 && neighbour.y in 0..70 && neighbour !in points && visited.add(neighbour)){
                            if(neighbour == 70 to 70){
                                return iteration + 1
                            }
                            add(neighbour)
                        }
                    }
                }
            }
            iteration++
        }
        return null
    }

    part1{
        floodFill(parsed.take(1024).toSet())
    }

    part2 {
        (1..parsed.size)
            .map(parsed::take)
            .binarySearch { if(floodFill(it.toSet()) == null) 1 else -1 }
            .let { parsed[it.absoluteValue - 1] }
    }
})