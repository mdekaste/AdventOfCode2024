package core.interval.implementations

import core.interval.Open

object All : Open<Nothing> {
    override val after: Nothing? = null
    override val from: Nothing? = null
    override val untilIncluding: Nothing? = null
    override val until: Nothing? = null
}

@Suppress("UNCHECKED_CAST")
fun <T : Comparable<T>> openInterval(): Open<T> = All as Open<T>

infix fun <T : Comparable<T>> Nothing?.until(none: Nothing?): Open<T> = openInterval()
