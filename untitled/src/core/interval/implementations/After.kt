package core.interval.implementations

import core.interval.*

class After<T : Comparable<T>> internal constructor(
    override val after: T,
    incrementable: Incrementable<T>,
) : ClosedOpen<T>, Incrementable<T> by incrementable {
    override val from: T by lazy { after.increment() }
    override val until: T? = null
    override val untilIncluding: T? = null
}

context(Incrementable<T>)
fun <T : Comparable<T>> after(after: T): After<T> =
    After(
        after = after,
        incrementable = given(),
    )

context(Incrementable<T>)
fun <T : Comparable<T>> after(after: T?): Interval.Right.Open<T> =
    when (after) {
        null -> openInterval()
        else -> after(after = after)
    }

