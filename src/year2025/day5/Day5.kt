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
    val (fresh, ingredients) = input
        .splitOnEmptyLine()
        .let { (a,b) ->
            a.lines().map { it.split("-").map(String::toLong).let { (x, y) -> x..y } } to
            b.lines().map(String::toLong)
        }
    val map = TreeMap<Long, Boolean>()
    fresh.forEach { l ->
        val fromBoundValue = map.ceilingEntry(l.first)?.value ?: false
        val untilBoundValue = map.higherEntry(l.last + 1)?.value ?: false
        map.subMap(l.first, true, l.last + 1, true).clear()
        if (!fromBoundValue) map[l.first] = false
        if (!untilBoundValue) map[l.last + 1] = true
    }
    part1{
        ingredients.count{ map.ceilingEntry(it)?.value ?: false }
    }
    part2{
        map.entries.zipWithNext { (l1, _), (l2, b2) -> if(b2) l2 - l1 else 0 }.sum()
    }
})