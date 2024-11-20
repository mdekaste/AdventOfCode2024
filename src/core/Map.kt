package core

fun <K, V> Map<K, List<V>>.toGraph(valueMapper: (V) -> Any ) {
    digraph {
        this@toGraph.forEach { (key, value) ->
            value.forEach {
                "$key" - "$it"
            }
        }
    }.let { println(it.dot()) }
}