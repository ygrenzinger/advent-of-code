
fun main() {

    fun buildMap(input: List<String>) : Map<Pair<Int, Int>, Int> {
        return input.mapIndexed { i, line -> line.mapIndexed { j, column -> (i to j) to column.toString().toInt() }  }.flatten().toMap()
    }

    val adjacents = listOf(
        (-1 to 0),
        (0 to -1),(0 to 1),
        (1 to 0)
    )

    fun part1(input: List<String>): Int {
        val map = buildMap(input)
        val t: List<Int> = map.keys.map { coord ->
            val currentHeight = map[coord]!!
            val coordsToCheck : List<Pair<Int, Int>> = adjacents.map { adj -> (coord.first + adj.first) to (coord.second + adj.second) }
            if (coordsToCheck.all {  currentHeight < map.getOrDefault(it, 9) }) {
                currentHeight + 1
            } else {
                0
            }
        }
        return t.sum()
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    check(part1(listOf(
        "0"
    )) == 1)

    check(part1(listOf(
        "01",
        "10"
    )) == 2)


    check(part1(listOf(
        "020",
        "212",
        "020",
    )) == 6)

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    println(part1(testInput))
    check(part1(testInput) == 15)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
