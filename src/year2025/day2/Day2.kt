package year2025.day2

import core.AdventOfCode

fun main(){
    Day2.part1()
    Day2.part2()
}

object Day2 : AdventOfCode({
    val parsed = input.split(",").map { it.split("-").let { (x, y) -> x.toLong()..y.toLong()} }

    fun String.isRepeated2(): Boolean {
        for(i in length / 2 downTo 1){
            if(chunked(i).toSet().size == 1){
                return true
            }
        }
        return false
    }

    fun String.isRepeated(): Boolean {
        if(length % 2 == 0){
            return substring(0, length / 2) == substring(length / 2)
        }
        return false
    }

    fun invalidId(range: LongRange): List<Long> {
        return buildList {
            for(i in range){
                if(i.toString().isRepeated()){
                    add(i)
                }
            }
        }
    }

    part1{
        parsed.flatMap { invalidId(it) }.sum()
    }

    fun invalidId2(range: LongRange): List<Long> {
        return buildList {
            for(i in range){
                if(i.toString().isRepeated2()){
                    add(i)
                }
            }
        }
    }
    part2{
        parsed.flatMap { invalidId2(it) }.sum()
    }
})