
fun retrieveNotes(input: String) : List<String> {
    return input.split(" | ")[1].split(" ")
}

fun retrieveSignals(input: String) : List<String> {
    return input.split(" | ")[0].split(" ")
}

fun easyDigit(pattern: String) : Int {
    val digit = when (pattern.length) {
        2 -> 1
        3 -> 7
        7 -> 8
        4 -> 4
        else -> 0
    }
    return digit
}

fun decodeEasyDigits(notes: List<String>): List<Int> {
    return notes.map { easyDigit(it)  }
}


fun numberOfSimilarSegments(a: String, b:String) : Int {
    val t: String = b.filter { a.toCharArray().contains(it) }
    return t.length
}

fun discoverDigit(signals: List<String>): Map<Set<Char>, Int> {
    val mutableMap: MutableMap<Int, String> = mutableMapOf()
    mutableMap[1] = signals.find { it.length == 2 }!!
    mutableMap[4] = signals.find { it.length == 4 }!!
    mutableMap[8] = signals.find { it.length == 7 }!!
    mutableMap[7] = signals.find { it.length == 3 }!!

    val signalsSizeSix = signals.filter { it.length == 6 }
    val six = signalsSizeSix.find { numberOfSimilarSegments(mutableMap[7]!!, it) == 2 }!!
    val nine = signalsSizeSix.find { numberOfSimilarSegments(mutableMap[4]!!, it) == 4 }!!
    mutableMap[6] = six
    mutableMap[9] = nine
    mutableMap[0] = signalsSizeSix.find { it != six && it != nine }!!

    val signalsSizeFive = signals.filter { it.length == 5 }
    val two = signalsSizeFive.find { numberOfSimilarSegments(mutableMap[9]!!, it) == 4 }!!
    val five = signalsSizeFive.find { numberOfSimilarSegments(mutableMap[6]!!, it) == 5 }!!
    mutableMap[2] = two
    mutableMap[5] = five
    mutableMap[3] = signalsSizeFive.find { it != five && it != two }!!

    return mutableMap.entries.associate { it.value.toSet() to it.key }
}

fun decodeNotes(input: String) : Int {
    val signals = retrieveSignals(input)
    val digitPatterns = discoverDigit(signals)
    val notes: List<String> = retrieveNotes(input)
    val number = notes.map { digitPatterns[it.toSet()] }.joinToString("") { it.toString() }.toInt()
    return number
}

fun main() {

    fun part1(input: List<String>): Int {
        return input.map { retrieveNotes(it) }
            .flatMap { decodeEasyDigits(it) }
            .filterNot { it == 0 }
            .size
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { decodeNotes(it) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    println(part2(testInput))
    check(part2(testInput) == 61229)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
