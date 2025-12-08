package year2024.day3

import core.AdventOfCode

fun main(){
    val day3 = Day3()
    day3.solve(10000)
}
private val regex = """(mul\((\d+),(\d+)\)|do\(\)|don't\(\))""".toRegex()
class Day3 : AdventOfCode({
    val parsed = regex.findAll(input).map(Instruction::of)

    part1 {
        parsed.filterIsInstance<Instruction.MUL>().sumOf(Instruction.MUL::value)
    }
    part2 {
        parsed.fold(true to 0){ (doIt, acc), inst ->
            when(inst){
                Instruction.DO -> true to acc
                Instruction.DONT -> false to acc
                is Instruction.MUL -> if (doIt) true to acc + inst.value else false to acc
            }
        }.second
    }
})
sealed interface Instruction {
    data object DO : Instruction
    data object DONT : Instruction
    data class MUL(val value: Int) : Instruction
    companion object {
        fun of(matchResult: MatchResult): Instruction {
            val (inst, a, b) = matchResult.destructured
            return when(inst){
                "do()" -> DO
                "don't()" -> DONT
                else -> MUL(a.toInt() * b.toInt())
            }
        }
    }
}