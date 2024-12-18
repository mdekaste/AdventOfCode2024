package year2024.day18

import core.AdventOfCode
import core.bfs
import core.inputParsing.extractInts
import core.twoDimensional.*
import java.util.PriorityQueue

fun main(){
    val day18 = Day18()
    day18.solve()
}

class Day18 : AdventOfCode({
    val parsed = input.lines().map { it.extractInts() }.map { (x,y) -> y to x}
    val end = 70 to 70

    part1{
        val grid = parsed.take(1024).associateBy({ it}, { '#' }).toMutableMap()
        for(y in 0..70){
            for(x in 0..70){
                val point = y to x
                if(point in grid) continue
                grid.put(point, '.')
            }
        }
        var frontier: Set<Point> = setOf(0 to 0)
        var iteration = 0
        while(frontier.isNotEmpty()){
            val nextFrontier = mutableSetOf<Point>()
            for(point in frontier){
                if(grid[point] == '.'){
                    grid[point] = 'X'
                    if(point == end){
                        return@part1 iteration
                    }
                    nextFrontier.addAll(point.cardinals())
                }
            }
            frontier = nextFrontier
            iteration++
        }
    }

    fun solve(take: Int): MutableMap<Point, Char>? {
        val grid = parsed.take(take).associateBy({ it}, { '#' }).toMutableMap()
        for(y in 0..70){
            for(x in 0..70){
                val point = y to x
                if(point in grid) continue
                grid.put(point, '.')
            }
        }
        var frontier: Set<Point> = setOf(0 to 0)
        var iteration = 0
        loop@while(frontier.isNotEmpty()){
            val nextFrontier = mutableSetOf<Point>()
            for(point in frontier){
                if(grid[point] == '.'){
                    grid[point] = 'X'
                    if(point == end)
                        return grid
                    nextFrontier.addAll(point.cardinals())
                }
            }
            frontier = nextFrontier
            iteration++
        }
        return null
    }

    fun floodFill(lines: List<Point>): MutableMap<Point, Char>{
        val grid = lines.associateByTo(mutableMapOf(), { it }, { '#' })
        for(y in 0..70){
            for(x in 0..70){
                val point = y to x
                if(point in grid) continue
                grid.put(point, '.')
            }
        }
        var frontier = mutableSetOf<Point>()
        frontier.add(0 to 0)
        while(frontier.isNotEmpty() && grid[end] != 'X'){
            val nextFrontier = mutableSetOf<Point>()
            for(point in frontier){
                if(grid[point] == '.'){
                    grid[point] = 'X'
                    nextFrontier.addAll(point.cardinals())
                }
            }
            frontier = nextFrontier
        }
        if(grid[end] != 'X') return mutableMapOf()
        return grid
    }

    fun addOn(grid: MutableMap<Point, Char>, points: Set<Point>): Boolean {
        var frontier = points.take(points.size - 1).toSet()
        val end = points.last()
        val visited = mutableSetOf<Point>()
        while(frontier.isNotEmpty()){
            val nextFrontier = mutableSetOf<Point>()
            for(point in frontier){
                if(grid[point] == 'X' && visited.add(point)){
                    if(point == end){
                        return true
                    }
                    nextFrontier.addAll(point.cardinals())
                }
            }
            frontier = nextFrontier
        }
        return false
    }

    part2 {
        var reachablePoint = floodFill(parsed.take(1024))
        reachablePoint.toPrettyString().let(::println)
        for(y in 1025 until parsed.size){
            reachablePoint.toPrettyString().let(::println)
            println("====================================")
            val pointsToTest = parsed[y].cardinals().associateBy({it}, {reachablePoint[it]}).filterValues { it == 'X' }.keys
            when{
                pointsToTest.size <= 1 -> reachablePoint[parsed[y]] = '#'
                else -> {
                    reachablePoint[parsed[y]] = '#'
                   if(!addOn(reachablePoint, pointsToTest)) {
                       reachablePoint = floodFill(parsed.take(y + 1))
                       if(reachablePoint.isEmpty()) return@part2 parsed[y].let { it.x to it.y }
                   }
                }
            }
        }
        reachablePoint.toPrettyString().let(::println)
    }
})