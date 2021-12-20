import kotlin.math.abs

fun main() {

    fun median(positions: List<Int>) : Int {
        val sorted = positions.sorted()
        val middle = sorted.size / 2
        val smaller = sorted.take(middle)
        val higher = sorted.drop(middle)
        return (smaller.sum() + higher.sum()) / 2
    }

    fun part1(input: List<String>): Int {
        val positions = input.first().split(",").map { it.toInt() }
        val min = positions.minOf { it }
        val max = positions.maxOf { it }
        val minFuelCost = (min..max).toList().parallelStream().map { position ->
            val fuelCost = positions.sumOf { abs(it - position) }
            fuelCost
        }.min(Integer::compare).orElse(0)
        return minFuelCost
    }

    fun part2(input: List<String>): Int {
        val positions = input.first().split(",").map { it.toInt() }
        val min = positions.minOf { it }
        val max = positions.maxOf { it }
        val minFuelCost = (min..max).toList().parallelStream().map { position ->
            val fuelCost = positions.sumOf {
                val steps = abs(it - position)
                (1..steps).sum()
            }
            fuelCost
        }.min(Integer::compare).orElse(0)
        return minFuelCost
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    println(part1(testInput))
    check(part2(testInput) == 168)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

