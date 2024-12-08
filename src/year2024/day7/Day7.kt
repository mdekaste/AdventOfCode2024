package year2024.day7

import core.AdventOfCode
import core.inputParsing.extractInts
import core.inputParsing.extractLongs
import kotlin.math.pow

fun main(){
    val day7 = Day7()
    day7.solve(1000)
}

val EQUATIONS: List<(Long, Long) -> Long> = listOf(Long::plus, Long::times, { a, b -> "$a$b".toLong() })

class Day7 : AdventOfCode({
    val parsed = input.lines().map { it.extractLongs() }.map { it.first() to it.drop(1) }

    fun solve(target: Long, numbers: List<Long>, equations: List<(Long, Long) -> Long>): Boolean {
        fun solve(cur: Long, index: Int): Boolean = when{
            cur > target -> false
            numbers.size == index -> cur == target
            else -> equations.any { equation -> solve(equation(cur, numbers[index]), index + 1) }
        }
        return solve(numbers[0], 1)
    }

    part1{
        parsed.filter { (target, numbers) -> solve(target, numbers, EQUATIONS.subList(0, 2)) }.sumOf { (target, _) -> target }
    }

    part2{
        parsed.filter { (target, numbers) -> solve(target, numbers, EQUATIONS.subList(0, 3)) }.sumOf { (target, _) -> target }
    }
})