package year2024.day18

import core.AdventOfCode
import core.bfs
import core.inputParsing.extractInts
import core.twoDimensional.*
import java.util.PriorityQueue
import kotlin.math.absoluteValue

fun main(){
    val day18 = Day18()
    day18.solve(10000)
}

class Day18 : AdventOfCode({
    val parsed = input.lines().map { it.extractInts() }.map { (x, y) -> x to y }

    fun floodFill(points: Set<Point>): Int {
        var frontier = listOf(ORIGIN)
        val visited = mutableSetOf<Point>()
        var iteration = 0
        while(frontier.isNotEmpty()){
            val nextFrontier = ArrayDeque<Point>()
            for(point in frontier){
                for(neighbour in point.cardinals()){
                    if(neighbour == 70 to 70){
                        return iteration + 1
                    }
                    if(neighbour !in points && neighbour.x in 0..70 && neighbour.y in 0..70 && visited.add(neighbour)){
                        nextFrontier.add(neighbour)
                    }
                }
            }
            frontier = nextFrontier
            iteration++
        }
        return -iteration
    }

    part1{
        floodFill(parsed.subList(0, 1024).toSet())
    }

    part2 {
        (1..parsed.size)
            .toList()
            .binarySearch(1024) { -floodFill(parsed.subList(0, it).toSet()) }
            .let { parsed[it.absoluteValue - 1] }
    }
})