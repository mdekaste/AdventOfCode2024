package core.interval.implementations

import core.interval.Closed
import core.interval.Incrementable
import core.interval.Interval
import core.interval.given

class FromAndUntil<T : Comparable<T>>(
    override val from: T,
    override val until: T,
    incrementable: Incrementable<T>,
) : Closed<T>, Incrementable<T> by incrementable {
    override val after: T by lazy { from.decrement() }
    override val untilIncluding: T by lazy { until.decrement() }

    override fun toString() = "[$from, $until)"
}

context(Incrementable<T>)
fun <T : Comparable<T>> fromAndUntil(
    from: T,
    until: T,
): FromAndUntil<T> =
    FromAndUntil(
        from = from,
        until = until,
        incrementable = given(),
    )

context(Incrementable<T>)
fun <T : Comparable<T>> fromAndUntil(
    from: T?,
    until: T,
): Interval.Right.Closed<T> =
    when (from) {
        null -> until(until = until)

        else ->
            fromAndUntil(
                from = from,
                until = until,
            )
    }

context(Incrementable<T>)
fun <T : Comparable<T>> fromAndUntil(
    from: T,
    until: T?,
): Interval.Left.Closed<T> =
    when (until) {
        null -> from(from = from)
        else ->
            fromAndUntil(
                from = from,
                until = until,
            )
    }

context(Incrementable<T>)
fun <T : Comparable<T>> fromAndUntil(
    from: T?,
    until: T?,
): Interval<T> =
    when {
        from == null -> until(until = until)
        until == null -> from(from = from)
        else ->
            fromAndUntil(
                from = from,
                until = until,
            )
    }

context(Incrementable<T>)
infix fun <T : Comparable<T>> T.until(until: T): FromAndUntil<T> = fromAndUntil(this, until)

context(Incrementable<T>)
infix fun <T : Comparable<T>> T?.until(until: T): Interval.Right.Closed<T> = fromAndUntil(this, until)

context(Incrementable<T>)
infix fun <T : Comparable<T>> T.until(until: T?): Interval.Left.Closed<T> = fromAndUntil(this, until)

context(Incrementable<T>)
infix fun <T : Comparable<T>> T?.until(until: T?): Interval<T> = fromAndUntil(this, until)

