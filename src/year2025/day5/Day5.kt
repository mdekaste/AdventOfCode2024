package year2025.day5

import core.AdventOfCode
import core.inputParsing.splitOnEmptyLine
import java.util.TreeMap
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.set

fun main(){
    Day5.part1()
    Day5.part2()
}

object Day5 : AdventOfCode({
    val map = TreeMap<Long, Boolean>()
    val ingredients = input
        .splitOnEmptyLine()
        .let { (a, b) ->
            a.lines().forEach {
                val (left, right) = it.split("-").map(String::toLong)
                val fromBoundValue = map.ceilingEntry(left)?.value ?: false
                val untilBoundValue = map.higherEntry(right + 1)?.value ?: false
                map.subMap(left, true, right + 1, true).clear()
                if (!fromBoundValue) map[left] = false
                if (!untilBoundValue) map[right + 1] = true
            }
            b.lines().map(String::toLong)
        }
    part1{
        ingredients.count{ map.ceilingEntry(it)?.value ?: false }
    }
    part2{
        map.entries.zipWithNext { (l1, _), (l2, b2) -> if(b2) l2 - l1 else 0 }.sum()
    }
})