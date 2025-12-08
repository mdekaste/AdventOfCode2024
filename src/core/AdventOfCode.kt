package core

import kotlin.time.measureTime
import kotlin.time.measureTimedValue

abstract class AdventOfCode(
    private val init: AdventOfCodeBuilder.() -> Unit
) {
    open val name: String? = null
    private val input = javaClass.getResource("input.txt")?.readText() ?: error("Input file not found")
    var builder = AdventOfCodeBuilder(input)

    init {
        builder.apply(init)
    }

    fun part1() = println("Part 1: ${builder.part1.invoke()}")
    fun part2() = println("Part 2: ${builder.part2.invoke()}")

    fun solve(preheat: Int = 0) {
        repeat(preheat) {
            builder.apply(init)
            builder.part1.invoke()
            builder.part2.invoke()
        }
        val (init, initTime) = measureTimedValue { builder.apply(init) }
        val (part1, time1) = measureTimedValue { builder.part1.invoke() }
        val (part2, time2) = measureTimedValue { builder.part2.invoke() }
        println(
            buildString {
                appendLine("Solution for ${this@AdventOfCode::class.simpleName}: ${name ?: ""}")
                appendLine("---------Initialization-----------")
                appendLine("time: $initTime")
                appendLine("---------Part 1-----------")
                appendLine("time: $time1")
                appendLine("answer: $part1")
                appendLine("---------Part 2-----------")
                appendLine("time: $time2")
                appendLine("answer: $part2")
                appendLine("---------Total-----------")
                appendLine("time: ${initTime + time1 + time2}")
                appendLine("---------End-----------")
            }
        )
    }
}

class AdventOfCodeBuilder(val input: String) {
    var part1: () -> Any? = { "not implemented yet" }
    var part2: () -> Any? = { "not implemented yet" }
    var parse: () -> Any? = { "not implemented yet" }
    fun part1(init: () -> Any?){
        part1 = init
    }
    fun part2(init: () -> Any?){
        part2 = init
    }
    fun parse(init: () -> Any?){
        parse = init
    }
}