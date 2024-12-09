package core

import core.twoDimensional.Point
import kotlin.time.measureTime
import kotlin.time.measureTimedValue

fun List<String>.toGraph(width: Int = 1) = buildMap<Point, String> {
    forEachIndexed { y, line ->
        line.chunked(width).forEachIndexed { x, s ->
            put(y to x, s)
        }
    }
}

fun <T> List<List<T>>.unzip(): List<List<T>> {
    return List(get(0).size) { i -> map { it[i] } }
}

fun <T> List<T>.product(): List<Pair<T, T>> = subList(0, lastIndex).flatMapIndexed { index, pair -> subList(index + 1, size).map { pair to it } }

fun <T> MutableList<T>.swap(i: Int, j: Int) {
    val temp = this[i]
    this[i] = this[j]
    this[j] = temp
}

fun <T> List<T>.permutations(): List<List<T>> {
    val result = mutableListOf<List<T>>()
    val cur = toMutableList()
    fun generate(k: Int): Any = when(k){
        1 -> result.add(cur.toList())
        else -> for(i in 0..<k){
            generate(k - 1)
            when(k % 2){
                0 -> cur.swap(i, k - 1)
                else -> cur.swap(0, k - 1)
            }
        }
    }
    generate(size)
    return result
}