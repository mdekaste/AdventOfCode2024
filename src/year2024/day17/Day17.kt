package year2024.day17

import core.AdventOfCode
import core.inputParsing.extractInts
import core.inputParsing.extractLongs
import core.inputParsing.splitOnEmptyLine
import kotlin.math.pow

fun main(){
    val day17 = Day17()
    day17.part1()
    day17.part2()
}

class Day17 : AdventOfCode({
    val (registers, program) = input.splitOnEmptyLine().let { (a,b) ->
        a.lines().flatMap { it.extractLongs() } to b.extractInts()
    }

    fun solve(aStart: Long) = buildList {
        var a = aStart
        var b = 0L
        var c = 0L
        fun combo(op: Int): Long = when(op){
            in 0..3 -> op.toLong()
            4 -> a
            5 -> b
            6 -> c
            else -> error("should not appear")
        }
        var index = 0
        while(index < program.size - 1){
            val op = program[index]
            val operand = program[index + 1]
            val combo = combo(operand)
            when(op){
                0 -> a = a shr combo.toInt()
                1 -> b = b xor operand.toLong()
                2 -> b = combo % 8
                3 -> if(a != 0L){ index = operand; continue }
                4 -> b = b xor c
                5 -> add((combo % 8).toInt())
                6 -> b = a shr combo.toInt()
                7 -> c = a shr combo.toInt()
            }
            index += 2
        }
    }

    part1{
        solve(registers[0]).joinToString(",")
    }

    fun recursion(target: List<Int>): Long {
        var a = when(target.size){
            1 -> 0
            else -> recursion(target.drop(1)) shl 3
        }
        while(solve(a) != target){
            a++
        }
        return a
     }

    part2 {
        recursion(program)
    }
})