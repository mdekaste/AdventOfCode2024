package year2024.day22

import core.AdventOfCode
import core.merge

fun main(){
    val day22 = Day22()
    day22.solve()
}

const val MOD = 16777216L
class Day22 : AdventOfCode({
    val parsed = input.lines().map(String::toLong)

    fun Long.mix(secret: Long) = this xor secret
    fun Long.prune() = this % MOD
    fun Long.step(calc: (Long) -> Long) = calc(this).mix(this).prune()
    fun Long.nextSecret() = step { it * 64 }.step { it / 32 }.step { it * 2048 }
    fun Long.generateSecrets() = generateSequence(this, Long::nextSecret).take(2001)

    fun Sequence<Long>.to4SequenceMap(): Map<List<Long>, Long> = map { it % 10 }
        .zipWithNext { a, b -> b to b - a }
        .windowed(4){ (a,b,c,d) -> listOf(a.second, b.second, c.second, d.second) to d.first }
        .groupingBy { (key, value) -> key }
        .fold({ _, value -> value.second },{ _, a, _ -> a })

    fun MutableMap<List<Long>, Long>.combine(oMap: Map<List<Long>, Long>) = apply {
        oMap.forEach { (key, value) -> merge(key, value, Long::plus) }
    }

    part1 {
        parsed.map(Long::generateSecrets).sumOf(Sequence<Long>::last)
    }

    part2 {
        parsed.map(Long::generateSecrets)
            .map(Sequence<Long>::to4SequenceMap)
            .fold(mutableMapOf<List<Long>, Long>(), MutableMap<List<Long>, Long>::combine)
            .values
            .max()
    }
})