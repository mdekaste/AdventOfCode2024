package year2024.day22

import core.AdventOfCode
import core.merge

fun main(){
    val day22 = Day22()
    day22.solve()
}

class Day22 : AdventOfCode({
    fun Long.mix(secret: Long) = this xor secret
    fun Long.prune() = this % 16777216L
    fun Long.step(calc: (Long) -> Long) = calc(this).mix(this).prune()
    fun Long.nextSecret() = step { it * 64 }.step { it / 32 }.step { it * 2048 }
    fun Long.generateSecrets() = generateSequence(this, Long::nextSecret).take(2001)

    val secrets = input.lines().map(String::toLong).map(Long::generateSecrets)

    part1 {
        secrets.sumOf(Sequence<Long>::last)
    }

    fun Sequence<Long>.to4SequenceMap() = map { it % 10 }
        .zipWithNext { a, b -> b to b - a }
        .windowed(4){ (a,b,c,d) -> listOf(a.second, b.second, c.second, d.second) to d.first }
        .groupingBy { (key, value) -> key }
        .fold({ _, value -> value.second },{ _, a, _ -> a })

    fun MutableMap<List<Long>, Long>.combine(oMap: Map<List<Long>, Long>) = apply {
        oMap.forEach { (key, value) -> merge(key, value, Long::plus) }
    }

    part2 {
        secrets.map(Sequence<Long>::to4SequenceMap)
            .fold(mutableMapOf<List<Long>, Long>(), MutableMap<List<Long>, Long>::combine)
            .values
            .max()
    }
})