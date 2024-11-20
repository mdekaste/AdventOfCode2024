package core

operator fun IntRange.plus(amount: Int): IntRange = first + amount..last + amount
operator fun IntRange.minus(amount: Int): IntRange = first - amount..last - amount
operator fun IntRange.times(amount: Int): IntRange = first * amount..last * amount
operator fun IntRange.div(amount: Int): IntRange = first / amount..last / amount
operator fun IntRange.rem(amount: Int): IntRange = first % amount..last % amount
operator fun IntRange.inc() = first + 1..last + 1
operator fun IntRange.dec() = first - 1..last - 1
operator fun IntRange.contains(other: IntRange) = other.first in this && other.last in this

infix fun IntRange.intersect(other: IntRange) = maxOf(first, other.first)..minOf(last, other.last)
infix fun IntRange.union(other: IntRange) = minOf(first, other.first)..maxOf(last, other.last)
infix fun IntRange.overlaps(other: IntRange) = first <= other.last && other.first <= last