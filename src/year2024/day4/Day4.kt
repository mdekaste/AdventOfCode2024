package year2024.day4

import core.AdventOfCode
import core.toGraph
import core.twoDimensional.*

fun main(){
    val day4 = Day4()
    day4.part1()
    day4.part2()
}



class Day4 : AdventOfCode({
    val parsed = input.lines().toGraph()

    part1{
        parsed.map { (k ,v) ->
            if(v != "X") return@map 0
            val east = v == "X" && parsed.get(k.east()) == "M" && parsed.get(k.east().east()) == "A" && parsed.get(k.east().east().east()) == "S"
            val south = k.south().let { parsed.get(it) == "M" && parsed.get(it.south()) == "A" && parsed.get(it.south().south()) == "S" }
            val southEast = k.south().east().let { parsed.get(it) == "M" && parsed.get(it.southEast()) == "A" && parsed.get(it.southEast().southEast()) == "S" }
            val southWest = k.southWest().let { parsed.get(it) == "M" && parsed.get(it.southWest()) == "A" && parsed.get(it.southWest().southWest()) == "S" }
            val northEast = k.northEast().let { parsed.get(it) == "M" && parsed.get(it.northEast()) == "A" && parsed.get(it.northEast().northEast()) == "S" }
            val north = k.north().let { parsed.get(it) == "M" && parsed.get(it.north()) == "A" && parsed.get(it.north().north()) == "S" }
            val northWest = k.northWest().let { parsed.get(it) == "M" && parsed.get(it.northWest()) == "A" && parsed.get(it.northWest().northWest()) == "S" }
            val west = k.west().let { parsed.get(it) == "M" && parsed.get(it.west()) == "A" && parsed.get(it.west().west()) == "S" }
            listOf(east, south, southEast, southWest, northEast, north, northWest, west).count { it }
        }.onEach { println(it) }.sum()
    }


    part2{
        parsed.map {
            var sum = 0
            val southEast = it.key.let { parsed.get(it) == "M" && parsed.get(it.southEast()) == "A" && parsed.get(it.southEast().southEast()) == "S" }
            val northWest = it.key.southEast().southEast().let { parsed.get(it) == "M" && parsed.get(it.northWest()) == "A" && parsed.get(it.northWest().northWest()) == "S" }
            if(southEast || northWest){
                val southwest = it.key.east().east().let { parsed.get(it) == "M" && parsed.get(it.southWest()) == "A" && parsed.get(it.southWest().southWest()) == "S" }
                val northeast = it.key.south().south().let { parsed.get(it) == "M" && parsed.get(it.northEast()) == "A" && parsed.get(it.northEast().northEast()) == "S" }
                if(southwest || northeast) sum++
            }
            sum
        }.sum()
    }


})
