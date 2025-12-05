package year2025.day5

import core.AdventOfCode
import core.inputParsing.splitOnEmptyLine
import java.util.TreeMap
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.set
import kotlin.text.split

fun main(){
    Day5.solve()
}

object Day5 : AdventOfCode({
    val map = TreeMap<Long, Boolean>(mapOf(Long.MAX_VALUE to false))
    val ingredients = input
        .split(System.lineSeparator() + System.lineSeparator())
        .let { (freshRange, ingredients) ->
            freshRange.lines().forEach { freshLine ->
                val (left, right) = freshLine.split("-").map(String::toLong)
                val fromBoundValue = map.ceilingEntry(left).value
                val toBoundValue = map.higherEntry(right + 1).value
                map.subMap(left, true, right + 1, true).clear()
                if(!fromBoundValue) map[left] = false
                if(!toBoundValue) map[right + 1] = true
            }
            ingredients.lines().map(String::toLong)
        }

    part1{
        ingredients.count{ map.ceilingEntry(it).value }
    }
    part2{
        map.entries.zipWithNext { (l1, _), (l2, b2) -> if(b2) l2 - l1 else 0 }.sum()
    }
})