package core.twoDimensional

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SegmentKtTest {
    @Test
    fun test(){
        val segment: Segment = (0 to -10) to (0 to 10)
        val segment2: Segment = (-10 to 0) to (10 to 0)
        val segment3: Segment = (-5 to -10) to (-5 to 10)
        val segment4: Segment = (5 to -10) to (5 to 10)
        val segment5: Segment = (-10 to -5) to (10 to -5)
        val segment6: Segment = (-10 to 5) to (10 to 5)

        val segments = listOf(segment, segment2, segment3, segment4, segment5, segment6)
        println(segments.getOverlappingPoints())
    }
}