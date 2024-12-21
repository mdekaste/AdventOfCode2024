package year2024.day21

import core.AdventOfCode
import core.inputParsing.extractInts
import core.inputParsing.toGrid
import core.twoDimensional.Point
import core.twoDimensional.cardinals
import core.twoDimensional.x
import core.twoDimensional.y
import java.util.PriorityQueue
import kotlin.math.absoluteValue

fun main(){
    val day21 = Day21()
    day21.solve()
}

class Day21 : AdventOfCode({
    val parsed = input.lines()

    val keypad = """
        789
        456
        123
        -0A
    """.trimIndent().toGrid()

    val directionpad = """
        -^A
        <v>
    """.trimIndent().toGrid()

    operator fun Point.minus(other: Point): Point = y - other.y to x - other.x

    fun routesFromTo(from: Char, to: Char, pad: Map<Point, Char>): Point {
        val fromLoc = pad.entries.first { it.value == from }.key
        val toLoc = pad.entries.first { it.value == to }.key
        return toLoc - fromLoc
    }

    fun Point.toDirectionalKeypadRoute(): String = buildString {
        if(x < 0){
            repeat(x.absoluteValue){ append('<') }
        } else if(x > 0){
            repeat(x){ append('>') }
        }
        if(y < 0){
            repeat(y.absoluteValue){ append('^') }
        } else if(y > 0){
            repeat(y){ append('v') }
        }
    }

//    fun CharArray.applyMove(targetChar: Char, targetIndex: Int = MAX): String {
//        val from = this[targetIndex]
//        val route = routesFromTo(from, targetChar, if (targetIndex == MAX) keypad else directionpad)
//        this[targetIndex] = targetChar
//        val keypadRoute = route.toDirectionalKeypadRoute()
//        if(targetIndex == 0){
//            return keypadRoute + 'A'
//        } else if(targetIndex == MAX){
//            val totalRoute = (keypadRoute + "A").map {
//                applyMove(it, targetIndex - 1)
//            }.joinToString("")
//            return totalRoute
//        } else {
//            return keypadRoute.map { applyMove(it, targetIndex - 1) }.joinToString("")
//        }
//    }

    val MAX = 3
    fun CharArray.applyMove2(targetChar: Char, targetIndex: Int): String {
        val from = this[targetIndex]
        val route = routesFromTo(from, targetChar, if (targetIndex == MAX) keypad else directionpad)
        this[targetIndex] = targetChar
        return route.toDirectionalKeypadRoute() + 'A'
    }

    fun solve(state: CharArray, goal: String): Int {
        val route = buildString {
            for(c in goal){
                append(state.applyMove2(c, 3))
            }
        }
        val route2 = buildString {
            for(c in route){
                append(state.applyMove2(c, 2))
            }
        }
        val route3 = buildString {
            for (c in route2) {
                append(state.applyMove2(c, 1))
            }
        }
        println(route)
        println(route2)
        println(route3)
        val int = goal.extractInts()[0]
        return route3.length * int
    }

    part1 {
        val state = CharArray(4) { 'A' }
        parsed.map { solve(state, goal = it) }.onEach { println(it) }.sum()
    }

    part2 {
        parsed
    }
})