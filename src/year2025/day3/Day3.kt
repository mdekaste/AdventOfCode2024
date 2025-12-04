package year2025.day3

import core.AdventOfCode
import core.inputParsing.toGrid

fun main(){
    Day3.part1()
    Day3.part2()
}


object Day3 : AdventOfCode({
    val parsed = input
        .lines()
        .map { line -> line.map(Char::digitToInt) }

    fun solve(list: List<Int>, limit: Int): Long {
        val indices = IntArray(limit){ it + list.size - limit }
        var pointer = 0
        loop@while(pointer >= 0){
            val rightWall = indices[pointer]
            val leftWall = indices.getOrNull(pointer - 1) ?: -1
            for(i in rightWall - 1 downTo leftWall + 1){
                if(list[i] >= list[rightWall]){
                    indices[pointer] = i
                    if(pointer < limit - 1){
                        pointer++
                    }
                    continue@loop
                }
            }
            pointer--
        }
        return indices.fold(0L){ acc, value -> acc * 10 + list[value] }
    }

    part1{
        parsed.sumOf { solve(it, 2) }
    }

    part2{
        parsed.sumOf {solve(it, 12) }
    }
})