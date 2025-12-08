package year2025.day8

import core.AdventOfCode
import core.removeFirstBy
import java.util.PriorityQueue
import kotlin.math.pow
import kotlin.math.sqrt

fun main(){
    Day8().solve()
}

class Day8 : AdventOfCode({
    val points: List<Point3D> = input.lines()
        .map { it.split(",").map(String::toInt).let{ (a,b,c) -> Triple(a,b,c) } }

    val pairsByDistance = points
        .flatMapIndexed { index, p1 -> points.subList(index + 1, points.size).map { p2 -> p1 to p2 } }
        .sortedBy { (p1, p2) -> p1.distanceTo(p2) }

    var part1: Int = 0
    var part2: Int = 0

    buildList {
        addAll(points.map { setOf(it) } )
        pairsByDistance.forEachIndexed { index, (p1, p2) ->
            if(index == 1000){
                part1 = map { it.size }.sortedDescending().take(3).reduce(Int::times)
            }
            val circuit1 = first { p1 in it }
            val circuit2 = first { p2 in it }
            if(size == 2 && circuit1 != circuit2){
                part2 = p1.x * p2.x
                return@buildList
            }
            remove(circuit1)
            remove(circuit2)
            addFirst(circuit1 + circuit2)
        }
    }

    part1{
        part1
    }
    part2{
        part2
    }
})

fun Point3D.distanceTo(other: Point3D): Double = sqrt(
    (x - other.x).toDouble().pow(2) + (y - other.y).toDouble().pow(2) + (z - other.z).toDouble().pow(2)
)

typealias Point3D = Triple<Int, Int, Int>
val Point3D.x get() = first
val Point3D.y get() = second
val Point3D.z get() = third

