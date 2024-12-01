package core.interval

sealed interface Interval<out T> {
    val from: T?
    val until: T?

    val after: T?
    val untilIncluding: T?

    sealed interface Left<T> {
        sealed interface Open<T> : Left<T>, Interval<T>
        sealed interface Closed<T> : Left<T>, Interval<T> {
            override val after: T
            override val from: T
        }
    }

    sealed interface Right<T> {
        sealed interface Open<T> : Right<T>, Interval<T>
        sealed interface Closed<T> : Right<T>, Interval<T> {
            override val until: T
            override val untilIncluding: T
        }
    }
}

interface Open<T> : Interval.Left.Open<T>, Interval.Right.Open<T>
interface OpenClosed<T> : Interval.Left.Open<T>, Interval.Right.Closed<T>
interface ClosedOpen<T> : Interval.Left.Closed<T>, Interval.Right.Open<T>
interface Closed<T> : Interval.Left.Closed<T>, Interval.Right.Closed<T>