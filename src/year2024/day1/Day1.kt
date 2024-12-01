package year2024.day1

import core.*
import core.inputParsing.extractInts
import kotlin.collections.reduce
import kotlin.math.abs

fun main(){
    Day1().part1().let(::println)
    Day1().part2().let(::println)
}

class Day1 : Challenge(){
    private val parsed = input.lines()
        .map(String::extractInts)
        .unzip()
        .map { it.sorted() }
    private val left = parsed.first()
    private val right = parsed.last()

    override fun part1() = left.zip(right, Int::minus).sumOf(::abs)

    override fun part2() = with(left.groupingBy().reduce(Int::plus).withDefault { 0 }){
       right.sumOf(::getValue)
    }
}