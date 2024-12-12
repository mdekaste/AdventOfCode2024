package year2024.day12

import core.AdventOfCode
import core.groupingBy
import core.inputParsing.toGrid
import core.twoDimensional.Point
import core.twoDimensional.cardinals
import core.twoDimensional.minus

fun main(){
    val day12 = Day12()
    day12.solve()
}

class Day12 : AdventOfCode({
    val parsed = input.toGrid()
    val byType = parsed.entries.groupBy({it.value}, {it.key}).mapValues { it.value.toSet() }

    fun Set<Point>.toGroups() = buildSet {
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

    val groups = byType.values.flatMap { it.toGroups() }

    fun Set<Point>.perimeter() = sumOf { point -> point.cardinals().count { it !in this } }

    part1{
        groups.sumOf { it.size * it.perimeter() }
    }

    fun Set<Point>.sides(): List<Set<Point>>{
        val wallsByType = flatMap { point -> point.cardinals().filter { it !in this }.map { it - point to it } }.groupBy({it.first}, { it.second}).mapValues { it.value.toSet() }
        return wallsByType.values.flatMap { it.toGroups() }
    }

    part2{
        groups.sumOf { it.size * it.sides().size }
    }
})