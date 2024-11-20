package core


operator fun LongRange.plus(amount: Long): LongRange = first + amount..last + amount
operator fun LongRange.minus(amount: Long): LongRange = first - amount..last - amount
operator fun LongRange.times(amount: Long): LongRange = first * amount..last * amount
operator fun LongRange.div(amount: Long): LongRange = first / amount..last / amount
operator fun LongRange.rem(amount: Long): LongRange = first % amount..last % amount
operator fun LongRange.inc(): LongRange = first + 1..last + 1
operator fun LongRange.dec(): LongRange = first - 1..last - 1
operator fun LongRange.contains(other: LongRange) = other.first in this && other.last in this

infix fun LongRange.intersect(other: LongRange) = maxOf(first, other.first)..minOf(last, other.last)
infix fun LongRange.union(other: LongRange) = minOf(first, other.first)..maxOf(last, other.last)
infix fun LongRange.overlaps(other: LongRange) = first <= other.last && other.first <= last