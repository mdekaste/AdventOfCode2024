package core.twoDimensional

import kotlin.math.absoluteValue

typealias Point = Pair<Int, Int>
val Point.y get() = first
val Point.x get() = second

val NORTH: Point = -1 to 0
val EAST: Point = 0 to 1
val SOUTH: Point = 1 to 0
val WEST: Point = 0 to -1
val NORTH_EAST: Point = -1 to 1
val SOUTH_EAST: Point = 1 to 1
val SOUTH_WEST: Point = 1 to -1
val NORTH_WEST: Point = -1 to -1
val ORIGIN: Point = 0 to 0

operator fun Point.plus(other: Point) = y + other.y to x + other.x
operator fun Point.minus(other: Point) = y - other.y to x - other.x

operator fun Point.times(amount: Int) = y * amount to x * amount
operator fun Point.rem(other: Point) = Math.floorMod(y, other.y) to Math.floorMod(x, other.x)

val CARDINALS = arrayOf(NORTH, EAST, SOUTH, WEST)
val INTERCARDINALS = arrayOf(NORTH_EAST, SOUTH_EAST, SOUTH_WEST, NORTH_WEST)
val WINDS = arrayOf(NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST)
fun Point.cardinals() = CARDINALS.map { it + this }
fun Point.intercardinals() = INTERCARDINALS.map { it + this }
fun Point.winds() = WINDS.map { it + this }

fun Point.east() = this + EAST
fun Point.west() = this + WEST
fun Point.north() = this + NORTH
fun Point.south() = this + SOUTH

fun Point.northEast() = this + NORTH_EAST
fun Point.southEast() = this + SOUTH_EAST
fun Point.southWest() = this + SOUTH_WEST
fun Point.northWest() = this + NORTH_WEST

operator fun Point.unaryMinus() = -y to -x

fun Point.right() = x to -y
fun Point.left() = -x to y

operator fun Point.dec() = left()
operator fun Point.inc() = right()

fun Point.perpendicular() = listOf(left(), right())
operator fun Point.not() = listOf(left(), -this, right())

fun Point.manhattenDistance(other: Point) = (y - other.y).absoluteValue + (x - other.x).absoluteValue

/**
 * Returns a list of points from this point to the other point
 */
fun Point.extend(direction: Point, amount: Int) = List(amount){ this + direction * it }

fun Point.walkTo(other: Point, inclusive: Boolean = false): List<Point> = when {
    other.x == x && other.y == y -> listOf(this)
    other.x == x && other.y > y -> extend(SOUTH, other.y - y + if(inclusive) 1 else 0)
    other.x == x && other.y < y -> extend(NORTH, y - other.y + if(inclusive) 1 else 0)
    other.y == y && other.x > x -> extend(EAST, other.x - x + if(inclusive) 1 else 0)
    other.y == y && other.x < x -> extend(WEST, x - other.x + if(inclusive) 1 else 0)
    other.x - x == other.y - y && other.x > x -> extend(SOUTH_EAST, other.x - x + if(inclusive) 1 else 0)
    other.x - x == other.y - y && other.x < x -> extend(NORTH_WEST, x - other.x + if(inclusive) 1 else 0)
    other.x - x == y - other.y && other.x > x -> extend(SOUTH_WEST, other.x - x + if(inclusive) 1 else 0)
    other.x - x == y - other.y && other.x < x -> extend(NORTH_EAST, x - other.x + if(inclusive) 1 else 0)
    else -> error("unsupported walk")
}

/**
 * Works under the assumption that the final point of this list connects back to the start via a 2D polygon
 * returns the area WITHIN the polygon e.g. not the area of the polygon itself
 *
 */
fun List<Point>.area() = (this + get(0))
    .zipWithNext(Point::walkTo)
    .flatten()
    .zipWithNext { (y1, x1), (_, x2) -> (x2 - x1) * y1 }
    .sum().absoluteValue - size / 2 + 1