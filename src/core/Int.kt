package core

infix fun Int.choose(other: Int): Int {
    var result = 1
    for (i in 1..other) {
        result *= this - i + 1
        result /= i
    }
    return result
}

infix fun Int.lcm(other: Int): Int = this * other / this gcd other
infix fun Int.gcd(other: Int): Int = if (other == 0) this else other gcd (this % other)

