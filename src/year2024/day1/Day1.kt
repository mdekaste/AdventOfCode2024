package year2024.day1

import core.Challenge
import kotlin.math.abs

fun main(){
    Day1().part1().let(::println)
    Day1().part2().let(::println)
}

class Day1 : Challenge(){
    val parsed = input.lines().map { it.split("   ").map(String::toInt).let { (a, b) -> a to b } }.unzip()

    override fun part1() = parsed.first.sorted().zip(parsed.second.sorted()).sumOf { abs(it.first - it.second) }

    override fun part2() = with(parsed.second.groupingBy { it }.eachCount()){
        parsed.first.sumOf { it * getOrDefault(it, 0) }
    }
}