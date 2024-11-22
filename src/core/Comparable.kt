package core

infix fun <T : Comparable<T>> T.minMax(other: T): Pair<T, T> = if (this < other) this to other else other to this