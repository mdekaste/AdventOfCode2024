package core


infix fun Long.choose(other: Long): Long {
    var result = 1L
    for (i in 1..other) {
        result *= this - i + 1
        result /= i
    }
    return result
}

infix fun Long.lcm(other: Long): Long = this * other / this gcd other
infix fun Long.gcd(other: Long): Long = if (other == 0L) this else other gcd (this % other)

fun Long.digits() = toString().map { it - '0' }