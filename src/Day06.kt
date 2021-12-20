import java.util.stream.Stream
import kotlin.streams.toList

data class Lanternfish(val internalTimer: Int, val count: Long) {
    fun dayPassing() : Stream<Lanternfish> {
        val current = this.decreaseTimer()
        return if (current.internalTimer >= 0) {
            Stream.of(current)
        } else {
            Stream.of(Lanternfish(6,count), Lanternfish(8,count))
        }
    }

    private fun decreaseTimer() = Lanternfish(internalTimer - 1, count)
}

fun List<Lanternfish>.dayPassing() : List<Lanternfish> {

    val fishes: List<Lanternfish> = this.parallelStream()
        .flatMap { it.dayPassing() }
        .toList()
    val newerCount = fishes.filter { it.internalTimer == 8 }.sumOf { it.count }
    val dayPassed = if (newerCount > 0) {
        fishes.filterNot { it.internalTimer == 8 } + Lanternfish(8, newerCount)
    } else {
        fishes
    }
    return dayPassed
}

fun main() {

    fun part1(input: List<String>): Long {
        val fishes = input.first().split(",")
            .groupBy { it.toInt() }
            .map { Lanternfish(it.key, it.value.size.toLong()) }
        return (1..80).fold(fishes) { acc, _ -> acc.dayPassing() }.sumOf { it.count }
    }

    fun part2(input: List<String>): Long {
        val fishes = input.first().split(",")
            .groupBy { it.toInt() }
            .map { Lanternfish(it.key, it.value.size.toLong()) }
        return (1..256).fold(fishes) { acc, _ -> acc.dayPassing() }.sumOf { it.count }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    println(part1(testInput))
    check(part2(testInput) == 26984457539L)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}

