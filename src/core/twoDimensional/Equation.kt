package core.twoDimensional

typealias Equation = Triple<Int, Int, Int>
val Equation.a get() = first
val Equation.b get() = second
val Equation.c get() = third

fun Equation.intersection(other: Equation): Point {
    val determinant = a * other.b - other.a * b
    val x = (other.b * c - b * other.c) / determinant
    val y = (a * other.c - other.a * c) / determinant
    return y to x
}