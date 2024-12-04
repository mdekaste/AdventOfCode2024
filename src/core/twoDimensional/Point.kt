package core.twoDimensional

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

fun Point.rotateRight() = -x to y
fun Point.rotateLeft() = x to -y

operator fun Point.dec() = rotateLeft()
operator fun Point.inc() = rotateRight()

fun Point.perpendicular() = listOf(rotateLeft(), rotateRight())
operator fun Point.not() = listOf(rotateLeft(), -this, rotateRight())