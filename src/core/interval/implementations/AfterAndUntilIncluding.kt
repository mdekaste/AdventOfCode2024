package core.interval.implementations

import core.interval.Closed
import core.interval.Incrementable
import core.interval.Interval
import core.interval.given

class AfterAndUntilIncluding<T : Comparable<T>>(
    override val after: T,
    override val untilIncluding: T,
    incrementable: Incrementable<T>,
) : Closed<T>, Incrementable<T> by incrementable {
    override val from: T by lazy { after.increment() }
    override val until: T by lazy { untilIncluding.increment() }
}

context(Incrementable<T>)
fun <T : Comparable<T>> afterAndUntilIncluding(
    after: T,
    untilIncluding: T,
): AfterAndUntilIncluding<T> =
    AfterAndUntilIncluding(
        after = after,
        untilIncluding = untilIncluding,
        incrementable = given(),
    )

context(Incrementable<T>)
fun <T : Comparable<T>> afterAndUntilIncluding(
    after: T?,
    untilIncluding: T,
): Interval.Right.Closed<T> =
    when (after) {
        null -> untilIncluding(untilIncluding = untilIncluding)
        else ->
            afterAndUntilIncluding(
                after = after,
                untilIncluding = untilIncluding,
            )
    }

context(Incrementable<T>)
fun <T : Comparable<T>> afterAndUntilIncluding(
    after: T,
    untilIncluding: T?,
): Interval.Left.Closed<T> =
    when (untilIncluding) {
        null -> after(after = after)
        else ->
            afterAndUntilIncluding(
                after = after,
                untilIncluding = untilIncluding,
            )
    }

context(Incrementable<T>)
fun <T : Comparable<T>> afterAndUntilIncluding(
    after: T?,
    untilIncluding: T?,
): Interval<T> =
    when {
        after == null -> untilIncluding(untilIncluding = untilIncluding)
        untilIncluding == null -> after(after = after)

        else ->
            afterAndUntilIncluding(
                after = after,
                untilIncluding = untilIncluding,
            )
    }
