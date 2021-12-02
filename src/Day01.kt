fun main() {

    fun isIncreasing(values: List<Int>): Int {
        if (values.size < 2) return 0
        return if (values[0] < values[1]) { 1 } else { 0 }
    }

    fun part1(input: List<String>): Int {
        return input.windowed(2)
            .sumOf { tuple -> isIncreasing(tuple.map { it.toInt() }) }
    }

    fun part2(input: List<String>): Int {
        return input.windowed(3)
            .map { lines: List<String> -> lines.sumOf { it.toInt() } }
            .windowed(2)
            .sumOf(::isIncreasing)

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    println(part2(testInput))
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
