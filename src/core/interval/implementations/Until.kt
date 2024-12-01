package core.interval.implementations

import core.interval.Incrementable
import core.interval.Interval
import core.interval.OpenClosed
import core.interval.given

class Until<T : Comparable<T>>(
    override val until: T,
    incrementable: Incrementable<T>,
) : OpenClosed<T>, Incrementable<T> by incrementable {
    override val after: T? = null
    override val from: T? = null
    override val untilIncluding: T by lazy { until.decrement() }

    override fun toString() = "(-∞, $until)"
}

context(Incrementable<T>)
fun <T : Comparable<T>> until(until: T): Until<T> =
    Until(
        until = until,
        incrementable = given(),
    )

context(Incrementable<T>)
fun <T : Comparable<T>> until(until: T?): Interval.Left.Open<T> =
    when (until) {
        null -> openInterval()
        else -> until(until = until)
    }

context(Incrementable<T>)
infix fun <T : Comparable<T>> Nothing?.until(until: T): Interval.Right.Closed<T> = until(until)
