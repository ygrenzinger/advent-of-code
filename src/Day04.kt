class Board(private val cells: Array<IntArray>, private val markedCells: Array<BooleanArray>, private var won: Boolean = false) {

    private fun isWinningLine(index: Int): Boolean {
        return markedCells[index].all { it }
    }

    private fun isWinningColumn(index: Int): Boolean {
        return markedCells.map { line -> line[index] }.all { it }
    }

    fun won() = won

    fun sumOfUnmarkedCells() : Int {
        var total = 0
        markedCells.mapIndexed { i, line ->
            line.mapIndexed { j, cell ->
                if (!cell) {
                    total += cells[i][j]
                }
            }
        }
        return total
    }

    fun markWith(number: Int): Int? {
        cells.mapIndexed { i, line ->
            line.mapIndexed { j, cell ->
                if (cell == number) {
                    markedCells[i][j] = true
                    if (isWinningLine(i) || isWinningColumn(j)) {
                        won = true
                        return cell
                    }
                }
            }
        }
        return null
    }

    companion object {
        fun createFrom(input: List<String>): Board {
            val cells: Array<IntArray> = input.map { line ->
                line.split(" ")
                    .filter { it.isNotBlank() }
                    .map { it.toInt() }.toIntArray()
            }
                .toTypedArray()
            val markedCells = (0..4)
                .map { arrayOf(false, false, false, false, false).toBooleanArray() }
                .toTypedArray()
            return Board(cells, markedCells)
        }
    }
}

class Bingo(private val numbersToDraw: List<Int>, private val boards: List<Board>) {
    fun play1(): Int? {
        for (number in numbersToDraw) {
            for (board in boards) {
                val winning = board.markWith(number)
                if (winning != null) {
                    return winning * board.sumOfUnmarkedCells()
                }
            }
        }
        return null
    }
    fun play2(): Int? {
        for (number in numbersToDraw) {
            for (board in boards) {
                if (!board.won()) {
                    val winning = board.markWith(number)
                    if (winning != null && boards.all { it.won() }) {
                        return winning * board.sumOfUnmarkedCells()
                    }
                }
            }
        }
        return null
    }

    companion object {
        private fun fetchBoards(input: List<String>, boards: List<Board>): List<Board> {
            return if (input.isEmpty()) {
                boards
            } else {
                fetchBoards(input.drop(6), boards + Board.createFrom(input.take(5)))
            }
        }

        fun createFrom(input: List<String>): Bingo {
            val numbersToDraw = input.first().split(",").map { it.toInt() }
            val boards = fetchBoards(input.drop(2), emptyList())
            return Bingo(numbersToDraw, boards)
        }
    }
}

fun main() {

    fun part1(input: List<String>): Int {
        return Bingo.createFrom(input).play1()?:-1
    }

    fun part2(input: List<String>): Int {
        return Bingo.createFrom(input).play2()?:-1
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    println(part2(testInput))
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}

