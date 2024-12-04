package core

typealias Point3D = Pair<Pair<Int, Int>, Int>
val Point3D.z: Int get() = first.first
val Point3D.y: Int get() = first.second
val Point3D.x: Int get() = second

val UP_3D: Point3D = -1 to 0 to 0
val DOWN_3D: Point3D = 1 to 0 to 0
val NORTH_3D: Point3D = 0 to -1 to 0
val SOUTH_3D: Point3D = 0 to 1 to 0
val EAST_3D: Point3D = 0 to 0 to 1
val WEST_3D: Point3D = 0 to 0 to -1

val CARDINALS_3D = arrayOf(NORTH_3D, EAST_3D, SOUTH_3D, WEST_3D, UP_3D, DOWN_3D)


operator fun Point3D.plus(other: Point3D) = z + other.z to y + other.y to x + other.x
operator fun Point3D.minus(other: Point3D) = z - other.z to y - other.y to x - other.x

operator fun Point3D.times(amount: Int) = z * amount to y * amount to x * amount


