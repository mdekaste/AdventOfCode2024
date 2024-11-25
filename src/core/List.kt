package core

import core.twoDimensional.Point

fun List<String>.toGraph(width: Int = 1) = buildMap<Point, String> {
    forEachIndexed { y, line ->
        line.chunked(width).forEachIndexed { x, s ->
            put(y to x, s)
        }
    }
}