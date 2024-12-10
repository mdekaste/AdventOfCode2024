package year2024.day9

import core.AdventOfCode
import java.util.*

fun main(){
    val day9 = Day9()
    day9.solve()
}


class Day9 : AdventOfCode({
    val parsed = input.flatMapIndexed{ index, c -> List(c - '0'){ if(index % 2 == 0) index / 2 else null } }

    fun List<Int?>.toResult(): Long =
        mapIndexed { index, i -> index.toLong() * (i ?: 0) }.sum()

    part1{
        val memory = parsed.toMutableList()
        var left = 0
        var right = memory.lastIndex
        while(left <= right) {
            when {
                memory[left] != null -> left++
                memory[right] == null -> right--
                else -> memory[left++] = memory[right--]
            }
        }
        memory.take(left).toResult()
    }

    part2{
        val mem = input.map(Char::digitToInt).toIntArray()
        val chunks = Array(mem.size){ mutableListOf<Int>() }
        val removed = mutableSetOf<Int>()
        for(i in mem.lastIndex downTo 0 step 2){
            for(j in 1..<i step 2){
                if(mem[j] >= mem[i]){
                    removed += i / 2
                    mem[j] -= mem[i]
                    repeat(mem[i]){ chunks[j].add(i / 2) }
                    mem[i] = 0
                }
            }
        }
        input.flatMapIndexed { index, c -> List(c.digitToInt()){ if(index % 2 == 0) (index / 2).takeIf { it in mem } else chunks[index].getOrNull(it) } }.also { println(it) }.toResult()
    }
})