package year2024.day19

import core.AdventOfCode
import core.inputParsing.splitOnEmptyLine

fun main(){
    repeat(10000){
        val day19 = Day19()
        day19.solve()
    }
    val day19 = Day19()
    day19.solve()

}

class Day19 : AdventOfCode({
    val (towels, designs) = input.splitOnEmptyLine().let { (a,b) -> a.split(", ") to b.lines() }

    class PrefixMap : MutableMap<Char, PrefixMap> by mutableMapOf(){
        var isEnd = false
        fun add(prefix: String): Unit = when(prefix){
            "" -> isEnd = true
            else -> getOrPut(prefix[0], :: PrefixMap).add(prefix.substring(1))
        }
    }

    val prefixMap = PrefixMap().apply {
        for (towel in towels) {
            add(towel)
        }
    }

    val memoization = mutableMapOf("" to 1L)
    fun countPossible(design: String): Long = memoization.getOrPut(design){
        var prefixMap: PrefixMap = prefixMap
        var count = 0L
        for((index, character) in design.withIndex()){
            prefixMap = prefixMap[character] ?: break
            if(prefixMap.isEnd){
                count += countPossible(design.substring(index + 1))
            }
        }
        count
    }

    val possibleDesigns = designs.map(::countPossible)

    part1{
        possibleDesigns.count { it > 0 }
    }

    part2{
        possibleDesigns.sum()
    }
})