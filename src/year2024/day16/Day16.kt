package year2024.day16

import core.AdventOfCode
import core.bfs
import core.gif.GifSequenceWriter
import core.groupingBy
import core.inputParsing.toGrid
import core.merge
import core.twoDimensional.*
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.util.PriorityQueue
import javax.imageio.ImageIO
import kotlin.collections.Map.Entry

fun main() {
    val day16 = Day16()
    day16.solve()
}
class Day16 : AdventOfCode({
    val parsed = input.toGrid()
    val start = parsed.entries.first { it.value == 'S' }.key

    val initialState = State(start, EAST)
    val visited = mutableMapOf(initialState to Score())
    val frontier = PriorityQueue<State>(compareBy { visited.getValue(it).score })
    frontier.add(initialState)
    val ends = mutableSetOf<State>()
    while (frontier.isNotEmpty() && ends.size < 2) {
        val state = frontier.poll()
        val score = visited.getValue(state)
        for ((nextPos, nextScore) in state.nextSteps(score)) {
            when (parsed[nextPos.position]) {
                'E' -> ends += nextPos
                '#' -> continue
            }
            if (visited.merge(nextPos, nextScore, Score::merge) == nextScore) {
                frontier.add(nextPos)
            }
        }
    }
    val solution = ends.map(visited::getValue).groupBy { it.score }.minBy { it.key }.value.reduce(Score::merge)

    part1 {
        solution.score
    }

    part2 {
        solution.visited.distinctBy { it.position }.size + 1
    }
})

private data class Score(val score: Int = 0, val visited: Set<State> = emptySet()) {
    fun merge(other: Score) = when{
        other.score < score -> other
        other.score > score -> this
        else -> copy(visited = visited + other.visited)
    }
}

private data class State(val position: Point, val direction: Point) {
    fun nextSteps(score: Score) = listOf(
        copy(position = position + direction) to score.copy(score = score.score + 1, visited = score.visited + this),
        copy(direction = direction.left()) to score.copy(score = score.score + 1000, visited = score.visited + this),
        copy(direction = direction.right()) to score.copy(score = score.score + 1000, visited = score.visited + this)
    )
}