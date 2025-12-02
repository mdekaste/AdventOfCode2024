package year2025.day1

import core.AdventOfCode
import kotlin.math.absoluteValue
import kotlin.math.sign

fun main(){
    Day1.part1()
    Day1.part2()
}

object Day1 : AdventOfCode( {
    val parsed = input
        .lines()
        .map { it[0] to it.substring(1).toInt() }
        .map { (dir, amount) -> if(dir == 'L') -amount else amount }

    val simulated = parsed.runningFold(50 to 0){ (curValue, _), amount ->
        val value = curValue + amount
        val clicks = when {
            amount > 0 -> value / 100
            curValue == 0 && amount < 0 -> value.div(100).absoluteValue
            else -> value.minus(100).div(100).absoluteValue
        }
        value.mod(100) to clicks
    }

    part1{
        simulated.count { (cur, _) -> cur == 0 }
    }
    part2{
        simulated.sumOf { (_, clicks) -> clicks }
    }
})