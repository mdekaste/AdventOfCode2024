package core

import core.twoDimensional.toPrettyString

fun <K, V> Map<K, List<V>>.toGraph(valueMapper: (V) -> Any ) {
    digraph {
        this@toGraph.forEach { (key, value) ->
            value.forEach {
                "$key" - "$it"
            }
        }
    }.let { println(it.dot()) }
}

fun <K, V> Map<K, V>.bfs(
    start: K,
    end: K,
    neighbourSelector: (key: K, value: V) -> List<K>,
    costCalculator: (key1: K, value1: V, key2: K, value2: V) -> Int?
): Int? {
    val visited = mutableSetOf<K>()
    val queue = mutableListOf(start to 0)
    while (queue.isNotEmpty()) {
        val (current, cost) = queue.removeFirst()
        val value = getValue(current)
        if (current == end) return cost
        visited.add(current)
        neighbourSelector(current, value).forEach { neighbour ->
            if(neighbour in keys && neighbour !in visited){
                val addedCost = costCalculator(current, value, neighbour, getValue(neighbour))
                if(addedCost != null){
                    queue.add(neighbour to cost + addedCost)
                }
            }
        }
    }
    return null
}

fun <K, V> Map<K, V>.bfs2(
    start: K,
    end: K,
    neighbourSelector: (key: K, value: V) -> List<K>,
    costCalculator: (key1: K, value1: V, key2: K, value2: V) -> Int?
): Int? {
    val visited = mutableMapOf<K, Int>()
    val queue = mutableListOf(start to 0)
    while (queue.isNotEmpty()) {
        val (current, cost) = queue.removeFirst()
        val value = getValue(current)
        visited.merge(current, 1, Int::plus)
        neighbourSelector(current, value).forEach { neighbour ->
            if (neighbour in keys && neighbour !in visited) {
                val addedCost = costCalculator(current, value, neighbour, getValue(neighbour))
                if (addedCost != null) {
                    queue.add(neighbour to cost + addedCost)
                }
            }
        }
    }
    return visited[end]
}

fun <K, V> Map<K,V>.keyOf(value: V): K = entries.first { it.value == value }.key

@JvmName("mergeMap")
fun <K, V> Map<K,V>.merge(other: Map<K,V>, mergeFunction: (V, V) -> V): Map<K,V> = toMutableMap().merge(other, mergeFunction)

@JvmName("mergeMutableMap")
fun <K, V> MutableMap<K,V>.merge(other: Map<K,V>, mergeFunction: (V, V) -> V): MutableMap<K, V> = apply {
    other.forEach { (key, value) ->
        this[key] = mergeFunction(this[key] ?: value, value)
    }
}