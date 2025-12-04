package year2025.day4

import core.AdventOfCode
import core.inputParsing.toGrid
import core.twoDimensional.Grid
import core.twoDimensional.intercardinals
import core.twoDimensional.winds

fun main(){
    Day4.part1()
    Day4.part2()
}

object Day4 : AdventOfCode({
    val parsed = input.toGrid()
    fun Grid<Char>.step(): Grid<Char> = mapValues { (point, char) ->
        when(char){
            '@' if(point.winds().count{ get(it) == '@' } < 4) -> '.'
            else -> char
        }
    }
    fun Grid<Char>.countRolls() = count { it.value == '@' }

    part1{
        parsed.countRolls() - parsed.step().countRolls()
    }
    part2{
        generateSequence(parsed, Grid<Char>::step)
            .zipWithNext()
            .takeWhile { it.first != it.second }
            .last().second
            .let { parsed.countRolls() - it.countRolls() }
    }
})