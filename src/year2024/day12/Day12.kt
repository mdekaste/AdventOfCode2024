package year2024.day12

import core.AdventOfCode
import core.groupingBy
import core.inputParsing.toGrid
import core.twoDimensional.Point
import core.twoDimensional.cardinals
import core.twoDimensional.minus

fun main(){
    val day12 = Day12()
    day12.solve(1000)
}

class Day12 : AdventOfCode({
    fun Set<Point>.toGroups(): Set<Set<Point>> = buildSet {
        fun grow(candidate: Point) = buildSet<Point> {
            add(candidate)
            val candidates = LinkedHashSet(this)
            while (candidates.isNotEmpty())
                for(neighbour in candidates.removeFirst().cardinals())
                    if(neighbour in this@toGroups && add(neighbour))
                        candidates.add(neighbour)
        }
        val candidates = this@toGroups.toCollection(LinkedHashSet())
        while(candidates.isNotEmpty()){
            val group = grow(candidates.removeFirst())
            add(group)
            candidates.removeAll(group)
        }
    }
    fun <T> List<Pair<Point, T>>.findGroups() = groupBy ({it.second}, {it.first})
        .values
        .flatMap { it.toSet().toGroups() }

    val groups = input.toGrid()
        .entries
        .map { it.key to it.value }
        .findGroups()

    fun Set<Point>.perimeter() = sumOf { point -> point.cardinals().count { it !in this } }
    part1{
        groups.sumOf { it.size * it.perimeter() }
    }

    fun Set<Point>.sides() = flatMap { o -> o.cardinals().filter { it !in this }.map { it to it - o } }.findGroups().size
    part2{
        groups.sumOf { it.size * it.sides() }
    }
})