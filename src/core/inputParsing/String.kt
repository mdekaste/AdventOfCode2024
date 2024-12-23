package core.inputParsing

import core.twoDimensional.Grid
import core.twoDimensional.MutableGrid

fun String.extractInts(): List<Int> = Regex("""-?\d+""").findAll(this).map { it.value.toInt() }.toList()
fun String.extractBytes(): List<Byte> = Regex("""-?\d+""").findAll(this).map { it.value.toByte() }.toList()
fun String.intsToSet(): Set<Int> = Regex("""-?\d+""").findAll(this).mapTo(mutableSetOf()) { it.value.toInt() }
fun String.extractLongs(): List<Long> = Regex("""-?\d+""").findAll(this).map { it.value.toLong() }.toList()
fun String.extractDoubles(): List<Double> = Regex("""-?\d+(\.\d+)?""").findAll(this).map { it.value.toDouble() }.toList()
fun String.extractWords(): List<String> = Regex("""\w+""").findAll(this).map { it.value }.toList()


fun String.splitOnEmptyLine(): List<String> = this.split(System.lineSeparator() + System.lineSeparator())

operator fun MatchResult.component1() = groupValues.getOrNull(1)
operator fun MatchResult.component2() = groupValues.getOrNull(2)
operator fun MatchResult.component3() = groupValues.getOrNull(3)
operator fun MatchResult.component4() = groupValues.getOrNull(4)
operator fun MatchResult.component5() = groupValues.getOrNull(5)
operator fun MatchResult.component6() = groupValues.getOrNull(6)
operator fun MatchResult.component7() = groupValues.getOrNull(7)
operator fun MatchResult.component8() = groupValues.getOrNull(8)
operator fun MatchResult.component9() = groupValues.getOrNull(9)
operator fun MatchResult.component10() = groupValues.getOrNull(10)

fun String.toGrid(): Grid<Char> = lines().flatMapIndexed { y, line -> line.mapIndexed { x, c -> y to x to c } }.toMap()
fun String.toGrid(ignore: Char): Grid<Char> = lines().flatMapIndexed { y, line -> line.mapIndexedNotNull { x, c -> c.takeIf { it != ignore }?.let { y to x to it } } }.toMap()
fun String.toMutableGrid(ignore: Char): MutableGrid<Char> = lines().flatMapIndexed { y, line -> line.mapIndexedNotNull { x, c -> c.takeIf { it != ignore }?.let { y to x to it } } }.toMap(mutableMapOf())