package core

class Cycle<T> private constructor(
    val initial: T,
    val stepFunction: (T) -> T
) {
    var repeatedElement: IndexedValue<T> private set
    val result: Set<T> = buildSet {
        add(initial)
        var next = stepFunction(initial)
        while(add(next)){
            next = stepFunction(next)
        }
        repeatedElement = IndexedValue(indexOf(next), next)
    }
    val lengthUntilCycle = repeatedElement.index
    val cycle = result.drop(repeatedElement.index)


    companion object{
        fun<T> of(
            initial: T,
            stepFunction: (T) -> T,
        ) : Cycle<T> = Cycle(initial, stepFunction)
    }
}