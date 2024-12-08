package core

import core.twoDimensional.Point

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