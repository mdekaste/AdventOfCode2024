package core.interval

import core.interval.implementations.*
import core.interval.intervalmap.IntervalMap
import core.interval.intervalmap.MutableIntervalMap
import core.interval.intervalmap.associateByInterval as associateByIntervalImpl
import core.interval.intervalmap.toIntervalMap as toIntervalMapImpl
import core.interval.intervalmap.groupByInterval as groupByIntervalImpl

interface Incrementable<T> {
    val minValue: T
    val maxValue: T
    fun T.increment(): T
    fun T.decrement(): T
}

abstract class IncrementableBase<T : Comparable<T>>(
    override val minValue: T,
    override val maxValue: T,
) : Incrementable<T> {
    fun after(value : T): After<T> = core.interval.implementations.after(value)

    fun after(value: T?): Interval.Right.Open<T> = core.interval.implementations.after(value)

    // AfterUntil
    fun afterUntil(
        after: T,
        until: T,
    ): AfterAndUntil<T> = core.interval.implementations.afterUntil(after, until)

    fun afterUntil(
        after: T?,
        until: T,
    ): Interval.Right.Closed<T> = core.interval.implementations.afterUntil(after, until)

    fun afterUntil(
        after: T,
        until: T?,
    ): Interval.Left.Closed<T> = core.interval.implementations.afterUntil(after, until)

    fun afterUntil(
        after: T?,
        until: T?,
    ): Interval<T> = core.interval.implementations.afterUntil(after, until)

    // AfterAndUntilIncluding
    fun afterAndUntilIncluding(
        after: T,
        untilIncluding: T,
    ): AfterAndUntilIncluding<T> =
        core.interval.implementations.afterAndUntilIncluding(
            after,
            untilIncluding,
        )

    fun afterAndUntilIncluding(
        after: T?,
        untilIncluding: T,
    ): Interval.Right.Closed<T> =
        core.interval.implementations.afterAndUntilIncluding(
            after,
            untilIncluding,
        )

    fun afterAndUntilIncluding(
        after: T,
        untilIncluding: T?,
    ): Interval.Left.Closed<T> =
        core.interval.implementations.afterAndUntilIncluding(
            after,
            untilIncluding,
        )

    fun afterAndUntilIncluding(
        after: T?,
        untilIncluding: T?,
    ): Interval<T> =
        core.interval.implementations.afterAndUntilIncluding(
            after,
            untilIncluding,
        )

    // From
    fun from(from: T): From<T> = core.interval.implementations.from(from)

    fun from(from: T?): Interval.Right.Open<T> = core.interval.implementations.from(from)

    // FromAndUntil
    fun fromAndUntil(
        from: T,
        until: T,
    ): FromAndUntil<T> = core.interval.implementations.fromAndUntil(from, until)

    fun fromAndUntil(
        from: T?,
        until: T,
    ): Interval.Right.Closed<T> = core.interval.implementations.fromAndUntil(from, until)

    fun fromAndUntil(
        from: T,
        until: T?,
    ): Interval.Left.Closed<T> = core.interval.implementations.fromAndUntil(from, until)

    fun fromAndUntil(
        from: T?,
        until: T?,
    ): Interval<T> = core.interval.implementations.fromAndUntil(from, until)

    infix fun T.until(until: T): FromAndUntil<T> = core.interval.implementations.fromAndUntil(this, until)

    infix fun T?.until(until: T): Interval.Right.Closed<T> = core.interval.implementations.fromAndUntil(this, until)

    infix fun T.until(until: T?): Interval.Left.Closed<T> = core.interval.implementations.fromAndUntil(this, until)

    infix fun T?.until(until: T?): Interval<T> = core.interval.implementations.fromAndUntil(this, until)

    // FromAndUntilIncluding
    fun fromAndUntilIncluding(
        from: T,
        untilIncluding: T,
    ): FromAndUntilIncluding<T> =
        core.interval.implementations.fromAndUntilIncluding(
            from,
            untilIncluding,
        )

    fun fromAndUntilIncluding(
        from: T?,
        untilIncluding: T,
    ): Interval.Right.Closed<T> =
        core.interval.implementations.fromAndUntilIncluding(
            from,
            untilIncluding,
        )

    fun fromAndUntilIncluding(
        from: T,
        untilIncluding: T?,
    ): Interval.Left.Closed<T> =
        core.interval.implementations.fromAndUntilIncluding(
            from,
            untilIncluding,
        )

    fun fromAndUntilIncluding(
        from: T?,
        untilIncluding: T?,
    ): Interval<T> =
        core.interval.implementations.fromAndUntilIncluding(
            from,
            untilIncluding,
        )

    infix fun T.untilIncluding(untilIncluding: T): FromAndUntilIncluding<T> = core.interval.implementations.fromAndUntilIncluding(this, untilIncluding)

    infix fun T?.untilIncluding(untilIncluding: T): Interval.Right.Closed<T> = core.interval.implementations.fromAndUntilIncluding(this, untilIncluding)

    infix fun T.untilIncluding(untilIncluding: T?): Interval.Left.Closed<T> = core.interval.implementations.fromAndUntilIncluding(this, untilIncluding)

    infix fun T?.untilIncluding(untilIncluding: T?): Interval<T> = core.interval.implementations.fromAndUntilIncluding(this, untilIncluding)

    // Until
    fun until(until: T): Until<T> = core.interval.implementations.until(until)

    fun until(until: T?): Interval.Left.Open<T> = core.interval.implementations.until(until)

    // UntilIncluding
    fun untilIncluding(untilIncluding: T): UntilIncluding<T> = core.interval.implementations.untilIncluding(untilIncluding)

    fun untilIncluding(untilIncluding: T?): Interval.Left.Open<T> = core.interval.implementations.untilIncluding(untilIncluding)

    fun <V> mutableIntervalMapOf(): MutableIntervalMap<T, V?> = core.interval.intervalmap.mutableIntervalMapOf()

    fun <V> mutableIntervalMapOf(initialValue: V): MutableIntervalMap<T, V> = core.interval.intervalmap.mutableIntervalMapOf(initialValue)

    fun <V> Iterable<V>.associateByInterval(keySelector: (V) -> Interval<T>): IntervalMap<T, V?> = associateByIntervalImpl(keySelector)

    fun <V, M : MutableIntervalMap<T, V>> Sequence<Pair<Interval<T>, V>>.toIntervalMap(destination: M): M = toIntervalMapImpl(destination)

    fun <V, M : MutableIntervalMap<T, V>> Iterable<Pair<Interval<T>, V>>.toIntervalMap(destination: M): M = toIntervalMapImpl(destination)

    fun <V, M : MutableIntervalMap<T, V>> Array<out Pair<Interval<T>, V>>.toIntervalMap(destination: M): M = toIntervalMapImpl(destination)

    fun <V, E> Iterable<E>.groupByInterval(
        keySelector: (E) -> Interval<T>,
        valueTransform: (E) -> V,
        defaultValue: V,
        onConflict: (V, V) -> V,
    ): IntervalMap<T, V> = groupByIntervalImpl(keySelector, valueTransform, defaultValue, onConflict)


}

data object IntIncrementable : IncrementableBase<Int>(
    minValue = Int.MIN_VALUE,
    maxValue = Int.MAX_VALUE,
) {
    override fun Int.increment() = plus(1)
    override fun Int.decrement() = minus(1)
}

data object LongIncrementable : IncrementableBase<Long>(
    minValue = Long.MIN_VALUE,
    maxValue = Long.MAX_VALUE,
){
    override fun Long.decrement() = minus(1)
    override fun Long.increment() = plus(1)
}