package core.twoDimensional

import core.minMax

typealias Segment = Pair<Point, Point>

fun Iterable<Segment>.getOverlappingPoints(): Set<Point> {
    val events = buildList<IntArray> {
        for((point1, point2) in this@getOverlappingPoints){
            when{
                point1.x == point2.x -> {
                    val (y1, y2) = point1.y minMax point2.y
                    add(intArrayOf(point1.x, 0, y1, y2))
                }
                point1.y == point2.y -> {
                    val (x1, x2) = point1.x minMax point2.x
                    add(intArrayOf(x1, -1, point1.y, point1.y))
                    add(intArrayOf(x2, 1, point2.y, point2.y))
                }
                else -> error("Segments must be horizontal or vertical")
            }
        }
        sortWith(compareBy({ it[0] }, { it[1] }))
    }
    val active = sortedSetOf<Int>()
    return buildSet {
        for((x, type, y1, y2) in events){
            when(type){
                -1 -> active.add(y1)
                0 -> active.subSet(y1, y2 + 1).mapTo(this) { Point(it, x) }
                1 -> active.remove(y1)
            }
        }
    }
}

operator fun Segment.component3() = first.first
operator fun Segment.component4() = first.second
operator fun Segment.component5() = second.first
operator fun Segment.component6() = second.second