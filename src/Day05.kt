import java.math.BigDecimal

data class Point(val x: Int, val y: Int) {
    companion object {
        fun from(point: String) =
            Point(point.split(",")[0].toInt(), point.split(",")[1].toInt())
    }
}

class Line(p1: Point, p2: Point) {
    val p1: Point
    val p2: Point

    init {
        if (p1.x <= p2.x) {
            this.p1 = p1
            this.p2 = p2
        } else {
            this.p1 = p2
            this.p2 = p1
        }
    }

    fun points() : List<Point> {
        return if (p1.x == p2.x) {
            if (p1.y < p2.y) {
                (p1.y..p2.y).map { Point(p1.x, it) }
            } else {
                (p2.y..p1.y).map { Point(p1.x, it) }
            }
        } else {
            val m: BigDecimal = (p2.y - p1.y).toBigDecimal() / (p2.x - p1.x).toBigDecimal()
            val b: BigDecimal = p1.y.toBigDecimal() - m * p1.x.toBigDecimal()
            (p1.x..p2.x).map {
                val y = (it.toBigDecimal() * m + b).toInt()
                Point(it, y)
            }
        }
    }

    companion object {
        fun from(line: String) =
            Line(Point.from(line.split(" -> ")[0]), Point.from(line.split(" -> ")[1]))
    }
}

class Field(width: Int, height: Int) {
    private val field = (0..height).map { (0..width).map { 0 }.toIntArray()  }.toTypedArray()
    fun markLines(lines: List<Line>) = lines.fold(this) { acc: Field, line: Line ->  acc.markLine(line) }

    private fun markLine(line: Line) : Field {
        val points = line.points()
        points.forEach { field[it.y][it.x]++ }
        return this
    }

    fun countOverlap() = field.flatMap { it.toList() }.count { it > 1 }
}

fun main() {

    fun part1(input: List<String>): Int {
        val lines = input.map { Line.from(it) }
        val points = lines.flatMap { listOf(it.p1, it.p2) }
        val maxHeight = points.maxOf { it.y }
        val maxWith = points.maxOf { it.x }
        val field = Field(maxWith, maxHeight)
        field.markLines(lines)
        return field.countOverlap()
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    println(part1(testInput))
    check(part1(testInput) == 12)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

