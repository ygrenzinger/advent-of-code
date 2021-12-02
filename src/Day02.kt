data class Position(val horizontal: Int = 0, val depth: Int = 0) {
    private fun forward(value: Int) = Position(horizontal + value, depth)
    private fun down(value: Int) = Position(horizontal, depth + value)
    private fun up(value: Int) = Position(horizontal, depth - value)

    fun applyCommand(command: String) : Position {
        val input = command.split(" ")
        if (input.size != 2) return this
        return try {
            val value = input[1].toInt()
            val newPosition =  when (input[0]) {
                "forward" -> this.forward(value)
                "down" -> this.down(value)
                "up" -> this.up(value)
                else -> throw RuntimeException("wrong command")
            }
            return newPosition
        } catch (_: Throwable) {
            this
        }
    }
}

data class ImprovedPosition(val horizontal: Int = 0, val aim: Int = 0, val depth: Int = 0) {
    private fun forward(value: Int) = ImprovedPosition(horizontal + value, aim, depth + (aim * value))
    private fun down(value: Int) = ImprovedPosition(horizontal, aim + value, depth)
    private fun up(value: Int) = ImprovedPosition(horizontal, aim - value, depth)

    fun applyCommand(command: String) : ImprovedPosition {
        val input = command.split(" ")
        if (input.size != 2) return this
        return try {
            val value = input[1].toInt()
            val newPosition =  when (input[0]) {
                "forward" -> this.forward(value)
                "down" -> this.down(value)
                "up" -> this.up(value)
                else -> throw RuntimeException("wrong command")
            }
            return newPosition
        } catch (_: Throwable) {
            this
        }
    }
}


fun main() {



    fun part1(input: List<String>): Int {
        val finalPosition = input.fold(Position(), Position::applyCommand)
        return finalPosition.horizontal * finalPosition.depth
    }

    fun part2(input: List<String>): Int {
        val finalPosition = input.fold(ImprovedPosition(), ImprovedPosition::applyCommand)
        return finalPosition.horizontal * finalPosition.depth
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    println(part2(testInput))
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

