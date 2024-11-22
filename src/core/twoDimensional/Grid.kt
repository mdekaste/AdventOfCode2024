package core.twoDimensional

typealias Grid<T> = Map<Point, T>
typealias MutableGrid<T> = MutableMap<Point, T>

fun <T> Grid<T>.toPrettyString(transform: (T) -> String, default: (Point) -> String): String {
    val maxX = keys.maxOfOrNull { it.x } ?: 0
    val maxY = keys.maxOfOrNull { it.y } ?: 0
    val minX = keys.minOfOrNull { it.x } ?: 0
    val minY = keys.minOfOrNull { it.y } ?: 0

    return (minY..maxY).joinToString("\n") { y ->
        (minX..maxX).joinToString("") { x ->
            Point(y, x).let {
                get(it)?.let{ transform(it) } ?: default(it)
            }.toString()
        }
    }
}

fun <T> Grid<T>.transpose(): Grid<T> = buildMap {
    this@transpose.forEach { (point, value) ->
        put(point.x to point.y, value)
    }
}

fun <T> Grid<T>.flipHorizontal(): Grid<T> = buildMap {
    val width = keys.maxOfOrNull { it.x } ?: 0
    this@flipHorizontal.forEach { (point, value) ->
        put(width - point.x to point.y, value)
    }
}

fun <T> Grid<T>.flipVertical(): Grid<T> = buildMap {
    val height = keys.maxOfOrNull { it.y } ?: 0
    this@flipVertical.forEach { (point, value) ->
        put(point.x to height - point.y, value)
    }
}

fun <T> Grid<T>.rotateRight(): Grid<T> = transpose().flipHorizontal()
fun <T> Grid<T>.rotateLeft(): Grid<T> = flipHorizontal().transpose()

fun <T> Grid<T>.toPrettyString(default: String): String = toPrettyString({ it.toString() }, { default })
fun <T> Grid<T>.toPrettyString(): String = toPrettyString(" ")

fun <T> MutableGrid<T>.surroundWalls(default: T) {
    val maxX = keys.maxOfOrNull { it.x } ?: 0
    val maxY = keys.maxOfOrNull { it.y } ?: 0
    val minX = keys.minOfOrNull { it.x } ?: 0
    val minY = keys.minOfOrNull { it.y } ?: 0

    for(y in minY..maxY){
        put(Point(y, minX - 1), default)
        put(Point(y, maxX + 1), default)
    }
    for(x in minX..maxX){
        put(Point(minY - 1, x), default)
        put(Point(maxY + 1, x), default)
    }
    put(Point(minY - 1, minX - 1), default)
    put(Point(minY - 1, maxX + 1), default)
    put(Point(maxY + 1, minX - 1), default)
    put(Point(maxY + 1, maxX + 1), default)
}

fun <T> Grid<T>.surroundWalls(default: T): Grid<T> = toMutableMap().apply { surroundWalls(default) }

