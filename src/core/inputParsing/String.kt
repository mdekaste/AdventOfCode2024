package core.inputParsing

fun String.extractInts(): List<Int> = Regex("""-?\d+""").findAll(this).map { it.value.toInt() }.toList()

operator fun MatchResult.component1() = groupValues[1]
operator fun MatchResult.component2() = groupValues[2]
operator fun MatchResult.component3() = groupValues[3]
operator fun MatchResult.component4() = groupValues[4]
operator fun MatchResult.component5() = groupValues[5]
operator fun MatchResult.component6() = groupValues[6]
operator fun MatchResult.component7() = groupValues[7]
operator fun MatchResult.component8() = groupValues[8]
operator fun MatchResult.component9() = groupValues[9]
operator fun MatchResult.component10() = groupValues[10]
