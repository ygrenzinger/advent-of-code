fun main() {

    fun buildMap(input: List<String>): Map<Coordinate, Int> {
        return input.mapIndexed { i, line -> line.mapIndexed { j, column -> (i to j) to column.toString().toInt() } }
            .flatten().toMap()
    }

    val adjacentCells = listOf(
        (-1 to 0),
        (0 to -1), (0 to 1),
        (1 to 0)
    )

    fun buildAdjacentCells(coord: Coordinate): List<Coordinate> {
        return adjacentCells.map { adj -> (coord.first + adj.first) to (coord.second + adj.second) }
    }

    fun part1(input: List<String>): Int {
        val map = buildMap(input)
        return map.keys.map { coord ->
            val currentHeight = map[coord]!!
            val coordsToCheck: List<Coordinate> = buildAdjacentCells(coord)
            if (coordsToCheck.all { currentHeight < map.getOrDefault(it, 9) }) {
                currentHeight + 1
            } else {
                0
            }
        }.sum()
    }

    fun bassinSize(map: Map<Coordinate, Int>, visited: MutableMap<Coordinate, Boolean>, coord: Coordinate): Int {
        return if (visited.getOrDefault(coord, true)) {
            0
        } else {
            visited[coord] = true
            buildAdjacentCells(coord).fold(1) { acc, c -> acc + bassinSize(map, visited, c) }
        }
    }

    fun part2(input: List<String>): Int {
        val map = buildMap(input)
        val visited = map.keys.associateWith { (map.getOrDefault(it, 9) == 9) }.toMutableMap()
        val bassinSizes: List<Int> = map.keys.fold<Coordinate, List<Int>>(listOf()) { acc, coord -> acc + bassinSize(map, visited, coord) }.filterNot { it == 0 }
        return bassinSizes.sorted().reversed().take(3).reduce { a, b -> a * b }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    println(part2(testInput))
    check(part2(testInput) == 1134)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
