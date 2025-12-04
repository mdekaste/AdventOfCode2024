package core

tailrec fun <T> T.stepUntilDifference(stepFunction: (T) -> T): T {
    val next = stepFunction(this)
    if(this == next) return this
    return next.stepUntilDifference(stepFunction)
}