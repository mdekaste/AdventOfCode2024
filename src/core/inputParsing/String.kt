package core.inputParsing

fun String.extractInts(): List<Int> = Regex("""-?\d+""").findAll(this).map { it.value.toInt() }.toList()

operator fun MatchResult.component1() = groupValues.getOrNull(1)
operator fun MatchResult.component2() = groupValues.getOrNull(2)
operator fun MatchResult.component3() = groupValues.getOrNull(3)
operator fun MatchResult.component4() = groupValues.getOrNull(4)
operator fun MatchResult.component5() = groupValues.getOrNull(5)
operator fun MatchResult.component6() = groupValues.getOrNull(6)
operator fun MatchResult.component7() = groupValues.getOrNull(7)
operator fun MatchResult.component8() = groupValues.getOrNull(8)
operator fun MatchResult.component9() = groupValues.getOrNull(9)
operator fun MatchResult.component10() = groupValues.getOrNull(10)
