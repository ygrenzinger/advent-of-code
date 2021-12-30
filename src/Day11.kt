import java.util.*
import kotlin.collections.HashSet

class Octopuses(private val map: MutableMap<Coordinate, Int>) {

    private var step = 0

    private val flashes = LinkedList<Coordinate>()
    private val hasFlashed = HashSet<Coordinate>()

    private val adjacentCells = listOf(
        (-1 to -1),(-1 to 0),(-1 to 1),
        (0 to -1), (0 to 1),
        (1 to -1),(1 to 0),(1 to 1)
    )

    private fun buildAdjacentCells(coord: Coordinate): List<Coordinate> {
        return adjacentCells.map { adj -> (coord.first + adj.first) to (coord.second + adj.second) }
    }

    private fun flash(coord: Coordinate) {
        buildAdjacentCells(coord).forEach { increase(it) }
    }

   private fun increase(coord: Coordinate) {
       map[coord] ?.let {
            if (it < 9) {
                map[coord] = it + 1
            } else {
                if (!hasFlashed.contains(coord)) {
                    flashes.add(coord)
                    hasFlashed.add(coord)
                }
            }
       }
    }

    private fun step(): Int {
        step += 1
        map.keys.forEach { increase(it) }
        while (flashes.isNotEmpty()) {
            val coord = flashes.remove()
            flash(coord)
        }
        hasFlashed.forEach { map[it] = 0 }
        return hasFlashed.size
    }

    private fun draw() {
        println()
        println("Step $step")
        val height = map.keys.maxOf { it.first }
        val with = map.keys.maxOf { it.second }
        (0..height).forEach { i ->
            val line = (0..with).map { j ->
                map[Pair(i, j)]!!
            }.joinToString("") { it.toString() }
            println(line)
        }
    }

    fun fastForward(): Int {
        return (1..100).sumOf {
            val count = step()
            hasFlashed.clear()
            count
        }
    }

    fun syncStep(): Int {
        var sync = false
        while (!sync) {
            draw()
            step()
            sync = map.keys.all { it in hasFlashed }
            hasFlashed.clear()
        }
        return step
    }

    companion object {
        fun createFrom(input: List<String>): Octopuses {
            val map = input.mapIndexed { i, line -> line.mapIndexed { j, column -> (i to j) to column.toString().toInt() } }
                .flatten().toMap()
            return Octopuses(map.toMutableMap())
        }
    }
}

fun main() {

    fun part1(input: List<String>): Int {
        return Octopuses.createFrom(input).fastForward()
    }

    fun part2(input: List<String>): Int {
        return Octopuses.createFrom(input).syncStep()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")

    check(part1(testInput) == 1656)
    println(part2(testInput))
    check(part2(testInput) == 195)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}
