package core.interval.intervalmap

import core.interval.Closed
import core.interval.Interval

interface IntervalMap<K : Comparable<K>, V> : Map<Interval<K>, V> {
    fun firstEntry(): Map.Entry<Interval.Left.Open<K>, V>

    fun lastEntry(): Map.Entry<Interval.Right.Open<K>, V>

    fun middleEntries(): Set<Map.Entry<Closed<K>, V>>

    operator fun get(key: K): V

    fun getEntry(key: K): Map.Entry<Interval<K>, V>

    override fun isEmpty(): Boolean = false
}
