package core

fun <T> Iterable<T>.eachCount() = groupingBy { it }.eachCount()

fun <T> Iterable<T>.groupingBy() = groupingBy { it }

inline fun <S, T : S, K> Grouping<T, K>.reduce(operation: (acc: S, element: T) -> S): Map<K, S> = reduce { _: K, accumulator: S, element: T -> operation(accumulator, element) }