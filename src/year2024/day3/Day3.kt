package year2024.day3

import core.AdventOfCode
import core.inputParsing.component1
import core.withNoNulls

fun main(){
    val day3 = Day3()
    day3.part1()
    day3.part2()
}

class Day3 : AdventOfCode({
    val regex = """(mul\((\d+),(\d+)\)|do\(\)|don't\(\))""".toRegex()
    val parsed = regex.findAll(input)
        .map(MatchResult::destructured)
        .map { (inst, a, b) -> inst to (withNoNulls(a.toIntOrNull(), b.toIntOrNull(), Int::times) ?: 0) }

    part1 {
        parsed.sumOf { it.second }
    }
    part2 {
       parsed.fold(true to 0) { (canDo, acc), (instruction, mul) ->
           when(instruction){
               "do()" -> true to acc
               "don't()" -> false to acc
               else -> if(canDo) true to (acc + mul) else false to acc
           }
       }.second
    }
})
