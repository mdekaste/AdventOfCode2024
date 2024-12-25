package year2024.day24

import core.*
import core.inputParsing.extractInts
import core.inputParsing.splitOnEmptyLine
import java.lang.classfile.components.ClassPrinter.LeafNode
import java.util.*

fun main(){

    val day24 = Day24()
    day24.solve()
}

data class Instruction(
    val dir1: String,
    val op: String,
    val dir2: String,
    val output: String
)
class Day24 : Challenge(){

    override fun part1(): Any? {
        return "no clean solution yet"
    }

    override fun part2(): Any? {
        return "no clean solution yet"
    }

}