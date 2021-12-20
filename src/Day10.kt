import kotlin.collections.ArrayDeque

fun main() {

    val isClosingSymbol: (Char) -> Boolean = { it in listOf(')', ']', '}', '>') }
    val isOpeningSymbol: (Char) -> Boolean = { it in listOf('(', '[', '{', '<') }

    val  openingSymbol: (c: Char)-> Char = {
        when (it) {
            ')' -> '('
            ']' -> '['
            '}' -> '{'
            else -> '<'
        }
    }

    val  incorrectSymbolValue: (c: Char)-> Int = {
       when (it) {
            ')' -> 3
            ']' -> 57
            '}' -> 1197
            '>' -> 25137
            else -> 0
        }
    }

    fun incorrectLineValue(line: String): Int {
        val stack = ArrayDeque<Char>()
        line.forEach {
            if (isOpeningSymbol(it)) {
                stack.add(it)
            }
            if (isClosingSymbol(it)) {
                val lastOpeningSymbol = stack.removeLast()
                if (lastOpeningSymbol != openingSymbol(it)) {
                    return incorrectSymbolValue(it)
                }
            }
        }
        return 0
    }

    val  incompleteSymbolValue: (c: Char)-> Int = {
        when (it) {
            '(' -> 1
            '[' -> 2
            '{' -> 3
            else -> 4
        }
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { incorrectLineValue(it) }
    }

    fun incompleteLineValue(line: String): Long {
        val openedStack = ArrayDeque<Char>()
        line.forEach {
            if (isOpeningSymbol(it)) {
                openedStack.add(it)
            }
            if (isClosingSymbol(it)) {
                val lastOpeningSymbol = openedStack.removeLast()
                if (lastOpeningSymbol != openingSymbol(it)) {
                    return 0
                }
            }
        }
        val sumOf = openedStack.reversed().fold(0L) {
                acc, symbol -> acc * 5 + incompleteSymbolValue(symbol)
        }
        return sumOf
    }

    fun part2(input: List<String>): Long {
        val scores = input.map { incompleteLineValue(it) }.filterNot { it == 0L }.sorted()
        return scores[scores.size/2]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    println(part2(testInput))
    check(part2(testInput) == 288957L)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}
