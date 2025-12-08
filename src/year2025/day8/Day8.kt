package year2025.day8

import core.AdventOfCode
import core.Point3D
import core.x
import core.y
import core.z
import kotlin.math.sqrt

fun main(){
    Day8.part1()
    Day8.part2()
}

fun Point3D.distanceTo(other: Point3D): Double{
    return sqrt((x - other.x.toLong()) * (x - other.x) + (y - other.y.toLong()) * (y - other.y.toLong()) + (z - other.z.toLong()) * (z - other.z).toDouble())
}

object Day8 : AdventOfCode({
    val parsed: List<Point3D> = input.lines().map { it.split(",").map { it.toInt() }.let { it[0] to it[1] to it[2] } }
    part1{
        val combinations = parsed.flatMapIndexed { i, p1 -> parsed.drop(i + 1).map { p2 -> p1 to p2 } }
        val sorted = combinations.sortedBy { (p1, p2) -> p1.distanceTo(p2) }
        val circuits: MutableList<Set<Point3D>> = parsed.map { setOf(it) }.toMutableList()
        sorted.take(10).forEachIndexed { index, (p1 ,p2) ->
            val circuit1 = circuits.first { p1 in it }
            val circuit2 = circuits.first { p2 in it }
            circuits.remove(circuit1)
            circuits.remove(circuit2)
            val newCircuit = circuit1 + circuit2
            circuits.add(newCircuit)
        }
        circuits.sortedByDescending { it.size }.forEach { println(it) }
        circuits.sortedByDescending { it.size }.take(3).forEach { println(it) }
        circuits.sortedByDescending { it.size }.take(3).let{ (a,b,c) -> a.size.toLong() * b.size * c.size}
    }
    part2{
        val combinations = parsed.flatMapIndexed { i, p1 -> parsed.drop(i + 1).map { p2 -> p1 to p2 } }
        val sorted = combinations.sortedBy { (p1, p2) -> p1.distanceTo(p2) }
        val circuits: MutableList<Set<Point3D>> = parsed.map { setOf(it) }.toMutableList()
        sorted.forEach { (p1, p2) ->
            val circuit1 = circuits.first { p1 in it }
            val circuit2 = circuits.first { p2 in it }
            if(circuits.size == 2 && circuit1 != circuit2){
                return@part2 p1.z * p2.z
            }
            circuits.remove(circuit1)
            circuits.remove(circuit2)
            val newCircuit = circuit1 + circuit2
            circuits.add(newCircuit)
        }
    }
})