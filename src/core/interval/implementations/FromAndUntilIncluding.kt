package core.interval.implementations

import core.interval.Closed
import core.interval.Incrementable
import core.interval.Interval
import core.interval.given

class FromAndUntilIncluding<T : Comparable<T>>(
    override val from: T,
    override val untilIncluding: T,
    incrementable: Incrementable<T>,
) : Closed<T>, Incrementable<T> by incrementable {
    override val after: T by lazy { from.decrement() }
    override val until: T by lazy { untilIncluding.increment() }
}

context(Incrementable<T>)
fun <T : Comparable<T>> fromAndUntilIncluding(
    from: T,
    untilIncluding: T,
): FromAndUntilIncluding<T> =
    FromAndUntilIncluding(
        from = from,
        untilIncluding = untilIncluding,
        incrementable = given(),
    )

context(Incrementable<T>)
fun <T : Comparable<T>> fromAndUntilIncluding(
    from: T?,
    untilIncluding: T,
): Interval.Right.Closed<T> =
    when (from) {
        null -> untilIncluding(untilIncluding = untilIncluding)
        else ->
            fromAndUntilIncluding(
                from = from,
                untilIncluding = untilIncluding,
            )
    }

context(Incrementable<T>)
fun <T : Comparable<T>> fromAndUntilIncluding(
    from: T,
    untilIncluding: T?,
): Interval.Left.Closed<T> =
    when (untilIncluding) {
        null -> from(from = from)
        else ->
            fromAndUntilIncluding(
                from = from,
                untilIncluding = untilIncluding,
            )
    }

context(Incrementable<T>)
fun <T : Comparable<T>> fromAndUntilIncluding(
    from: T?,
    untilIncluding: T?,
): Interval<T> =
    when {
        from == null -> untilIncluding(untilIncluding = untilIncluding)
        untilIncluding == null -> from(from = from)
        else ->
            fromAndUntilIncluding(
                from = from,
                untilIncluding = untilIncluding,
            )
    }

context(Incrementable<T>)
infix fun <T : Comparable<T>> T.untilIncluding(untilIncluding: T): FromAndUntilIncluding<T> = fromAndUntilIncluding(this, untilIncluding)

context(Incrementable<T>)
infix fun <T : Comparable<T>> T?.untilIncluding(untilIncluding: T): Interval.Right.Closed<T> = fromAndUntilIncluding(this, untilIncluding)

context(Incrementable<T>)
infix fun <T : Comparable<T>> T.untilIncluding(untilIncluding: T?): Interval.Left.Closed<T> = fromAndUntilIncluding(this, untilIncluding)

context(Incrementable<T>)
infix fun <T : Comparable<T>> T?.untilIncluding(untilIncluding: T?): Interval<T> = fromAndUntilIncluding(this, untilIncluding)
