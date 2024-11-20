package core

import kotlin.time.measureTimedValue

abstract class Challenge(
    val name: String? = null,
    inputFile: String = "input.txt"
) {
    val input = javaClass.getResource("input.txt").readText()

    abstract fun part1(): Any?
    abstract fun part2(): Any?

    fun solve(preheat: Int = 0): String {
        repeat(preheat) {
            try { part1() } catch(_: NotImplementedError){ }
            try { part2() } catch(_: NotImplementedError){ }
        }
        val (part1, time1) = measureTimedValue { part1() }
        val (part2, time2) = measureTimedValue { part2() }
        return buildString {
            appendLine("Solution for ${this@Challenge::class.simpleName}: ${name ?: ""}")
            appendLine("---------Part 1-----------")
            appendLine("time: $time1")
            appendLine("answer: $part1")
            appendLine("---------Part 2-----------")
            appendLine("time: $time2")
            appendLine("answer: $part2")
            appendLine("---------Total-----------")
            appendLine("time: ${time1 + time2}")
            appendLine("---------End-----------")
        }
    }
}