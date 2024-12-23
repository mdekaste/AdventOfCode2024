package year2024.day23

import core.AdventOfCode
import core.Challenge

fun main(){
    val day23 = Day23()
    day23.solve().let(::println)
}

class Day23 : Challenge(){
    val parsed: Map<String, Set<String>> = input.lines()
        .flatMap { it.split("-").let { (a,b) -> listOf(a to b, b to a) } }
        .groupBy ({it.first}, {it.second})
        .mapValues { it.value.toSet() }

    val regionsOfThree = buildSet<MutableSet<String>> {
        for((from, to) in parsed){
            for(target in to){
                for(third in parsed.getValue(target)){
                    if(third != from && third in to){
                        add(mutableSetOf(from, target, third))
                    }
                }
            }
        }
    }

    override fun part1() = regionsOfThree.count { it.any { it.startsWith('t') } }

    val regions = buildSet<MutableSet<String>> {
        for((from, to) in parsed){
            for(region in this){
                if(to.containsAll(region) && region.all { from in parsed.getValue(it) }){
                    region.add(from)
                }
            }
            for(node in to){
                add(mutableSetOf(from, node))
            }
        }
    }

    override fun part2() = regions.maxBy { it.size }.sorted().joinToString(",")
}