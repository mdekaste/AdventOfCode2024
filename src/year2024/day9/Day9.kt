package year2024.day9

import core.AdventOfCode

fun main(){
    val day9 = Day9()
    day9.solve()
}

class Day9 : AdventOfCode({
    val parsed = input.map { it.digitToInt() }

    part1 {
        var counter = 0
        val numbers = parsed.fold(listOf<Int?>() to false){ (acc, last), i ->
            if(!last){
                val result = acc + List(i){ counter }
                counter++
                result to true
            } else {
                acc + List(i){ null } to false
            }
        }.first.toMutableList()
        println(numbers)
        var left = 0
        var right = numbers.lastIndex
        while(left < right){
            if(numbers[left] != null){
                left++
            } else if(numbers[right] == null){
                right--
            } else {
                numbers[left] = numbers[right]
                numbers[right] = null
                left++
                right--
            }
        }
        println(numbers)
        numbers.takeWhile { it != null }.mapIndexed { index, i -> index * i!!.toLong() }.sum()
    }

    part2 {
        var sizes = mutableMapOf<Int, IntRange>()
        var counter = 0
        var sum = 0
        val numbers = parsed.fold(listOf<Int?>() to false){ (acc, last), i ->
            sum += i
            if(!last){
                val result = acc + List(i){ counter }
                sizes.put(counter, sum - i until sum)
                counter++
                result to true
            } else {
                acc + List(i){ null } to false
            }
        }.first.toMutableList()
        for((id, range) in sizes.entries.reversed()){
            val size = range.last - range.first + 1
            val window = numbers.subList(0, range.first).windowed(size, 1, false).indexOfFirst { it.all { it == null } }.takeIf { it != -1 } ?: continue
            for(i in window until window + size){
                numbers[i] = id
            }
            for(i in range){
                numbers[i] = null
            }
        }
        numbers.mapIndexed { index, i -> (i?.toLong() ?: 0) * index }.sum()
    }
})