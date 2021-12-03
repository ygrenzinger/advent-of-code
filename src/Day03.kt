
fun main() {

    fun significantByte(bits: List<Char>, comparator: (Int, Int) -> Boolean) : Char {
        val onesCount = bits.count { it == '1' }
        val avg = bits.size / 2
        return if (comparator(onesCount, avg)) {
            '1'
        } else {
            '0'
        }
    }

    fun mostSignificantByte(bits: List<Char>) : Char {
        return significantByte(bits) { ones: Int, avg: Int -> ones > avg }
    }

    fun leastSignificantByte(bits: List<Char>) : Char {
        return significantByte(bits) { ones: Int, avg: Int -> ones < avg }
    }

    fun rate(input: List<String>, significantByte: (bytes: List<Char>) -> Char) : Int {
        val columnsIndices = 0 until input.first().length
        val binaryNumber: List<Char> = columnsIndices.map { columnsIndex -> significantByte(input.map { it[columnsIndex] }) }
        return Integer.parseInt(binaryNumber.joinToString(""), 2)
    }

    fun gammaRate(input: List<String>) : Int {
        return rate(input, ::mostSignificantByte)
    }

    fun epsilonRate(input: List<String>) : Int {
        return rate(input, ::leastSignificantByte)
    }

    fun mostCommon(bits: List<Char>): Char {
        val onesCount = bits.count { it == '1' }
        val zerosCount = bits.size - onesCount
        return if (onesCount >= zerosCount) {
            '1'
        } else {
            '0'
        }
    }

    fun leastCommon(bits: List<Char>): Char {
        val onesCount = bits.count { it == '1' }
        val zerosCount = bits.size - onesCount
        return if (onesCount >= zerosCount) {
            '0'
        } else {
            '1'
        }
    }

    fun rating(numbers: List<String>, index: Int, selector: (List<Char>) -> Char): Int {
        if (numbers.size == 1) {
            return Integer.parseInt(numbers.first(), 2)
        }
        val bits: List<Char> = numbers.map { it[index] }
        val bitSelection = selector(bits)
        val filtered = numbers.filter { it[index] == bitSelection }
        return rating(filtered, index + 1, selector)
    }

    fun oxygenGeneratorRating(numbers: List<String>): Int {
       return rating(numbers,0, ::mostCommon)
    }

    fun co2ScrubberRating(numbers: List<String>): Int {
        return rating(numbers,0, ::leastCommon)
    }

    fun part1(input: List<String>): Int {
        return gammaRate(input) * epsilonRate(input)
    }

    fun part2(input: List<String>): Int {
        return oxygenGeneratorRating(input)  * co2ScrubberRating(input)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    println(part1(testInput))
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

