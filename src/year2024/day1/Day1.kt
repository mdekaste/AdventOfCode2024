package year2024.day1

import core.Challenge
import kotlin.math.abs

fun main(){
    Day1().part1().let(::println)
    Day1().part2().let(::println)
}

class Day1 : Challenge(){
    val left: List<Int>
    val right: List<Int>
    init {
        input.lines()
            .map { it.split("   ").map(String::toInt).let { (a, b) -> a to b } }
            .unzip()
            .also { (l, r) -> left = l.sorted(); right = r.sorted() }
    }

    override fun part1() = left.zip(right, Int::minus).sumOf(::abs)

    override fun part2() = with(right.groupingBy { it }.eachCount()){
       left.sumOf { it * getOrDefault(it, 0) }
    }
}