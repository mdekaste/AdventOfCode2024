package core

fun <T> Sequence<T>.lastByDifference(): T? {
    val iterator = iterator()
    if(!iterator.hasNext()) return null
    var element = iterator.next()
    while(iterator().hasNext()){
        val candidate = iterator.next()
        if(candidate == element){
            return element
        }
        element = candidate
    }
    return element
}