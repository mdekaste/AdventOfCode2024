package core

import core.twoDimensional.cardinals
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MapKtTest{
    @Test
    fun bfs(){
        val input = """
            s123
            #3#1
            456e
        """.trimIndent()

        val graph = input.lines().toGraph()

        val cost = graph.bfs(
            start = graph.keyOf("s"),
            end = graph.keyOf("e"),
            neighbourSelector = { point, _ -> point.cardinals() },
            costCalculator = { _, _, _, v2 ->
                when(v2){
                    "e" -> 0
                    else -> v2.toIntOrNull()
                }
            }
        )

        assertEquals(7, cost)
    }
    @Test
    fun bfs2(){
        val input = """
            s:2a,3b
            a:1s,3b
            b:3d,2s,3a
            d:1b,9e
        """.trimIndent()

        val graph = input.lines().map { line ->
            line.split(":").let { (key, values) ->
                key to values.split(",").map { it[0].digitToInt() to it[1].toString() }
            }
        }.plus("e" to emptyList()).toMap()

        val cost = graph.bfs(
            start = "s",
            end = "e",
            neighbourSelector = { _, v -> v.map { it.second } },
            costCalculator = { _, v1, key, _  ->
                v1.firstOrNull { it.second == key }?.first
            }
        )

        assertEquals(15, cost)
    }
}