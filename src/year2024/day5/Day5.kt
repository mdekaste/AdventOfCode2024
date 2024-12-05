package year2024.day5

import core.AdventOfCode
import core.inputParsing.extractInts
import core.inputParsing.splitOnEmptyLine

fun main(){
    val day5 = Day5()
    day5.part1()
    day5.part2()
}


class Day5 : AdventOfCode({
    val (map, pages) = input.splitOnEmptyLine().let { (a,b) ->
        a.lines() to b.lines()
    }.let{ (a,b) -> a.map { it.extractInts().let { (c,d) -> c to d  } }.groupBy ({ it.first }, { it.second}) to b }

    fun List<Int>.isSortedCorrectly(map: Map<Int, List<Int>> ): Boolean{
        return zipWithNext { a, b -> b in map.getOrElse(a){ emptyList() } }.all { it }
    }

    part1 {
        pages.map { it.extractInts() }.filter { it.isSortedCorrectly(map) }.map { it[it.size / 2] }.sum()
    }

    fun List<Int>.sort(map: Map<Int, List<Int>>): List<Int> {
        return IntArray(size).apply {
            for(element in this@sort){
                set(map.getValue(element).let { this@sort.intersect(it) }.size, element)
            }
        }.reversed().toList()
    }

    part2{
        pages.map { it.extractInts() }.filter { !it.isSortedCorrectly(map) }.map { it.sort(map) }.map { it[it.size / 2] }.sum()
    }
})
