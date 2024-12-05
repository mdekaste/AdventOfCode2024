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
    input.splitOnEmptyLine()
        .map { it.lines().map(String::extractInts) }
        .also { (rawOrder, rawBooks) ->
            val order = rawOrder.groupBy(List<*>::first, List<*>::last)
                .mapValues { it.value.toSet() }
                .withDefault { emptySet() }
            val booksWithSorted = rawBooks.associateWith { set -> set.sortedByDescending { set.intersect(order.getValue(it)).size } }

            part1 {
                booksWithSorted.filter { it.key == it.value }.values.sumOf { it[it.size/2] }
            }

            part2{
                booksWithSorted.filter { it.key != it.value }.values.sumOf { it[it.size/2] }
            }
        }
})
