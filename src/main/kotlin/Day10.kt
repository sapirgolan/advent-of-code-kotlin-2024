import java.util.*

fun main() {
    fun findAllStartingPoints(input: List<String>): List<Node> {
        return input.flatMapIndexed { y, string ->
            string.toCharArray()
                .mapIndexed { x, char ->
                    char.takeIf { char == '0' }
                        ?.let {
                            Node(Location(x + 1, y + 1), 0)
                        }
                }.filterNotNull()
        }
    }

    fun createBoard(input: List<String>): List<List<Node>> {
        return input.mapIndexed { y, string ->
            string.toCharArray()
                .mapIndexed { x, char ->
                    Node(Location(x + 1, y + 1), char.takeIf { it.isDigit() }?.digitToInt() ?: -1)
                }
        }

    }

    fun countTrailhead(input: List<String>): List<Node> {
        val board = createBoard(input)

        val peaks: List<Node> = findAllStartingPoints(input)
            .map { DFS(board).findReachablePeaks(it) }
            .flatten()
        return peaks
    }

    // Test if implementation meets criteria from the description, like:
//    check(findAllStartingPoints(readInput("Day10_test_01")).size == 1)
//    check(findAllStartingPoints(readInput("Day10_test_02")).size == 1)
//    check(findAllStartingPoints(readInput("Day10_test_03")).size == 1)
//    check(findAllStartingPoints(readInput("Day10_test_04")).size == 2)
//    check(findAllStartingPoints(readInput("Day10_test_05")).size == 9)
//    println("Passed test 1")

    check(countTrailhead(readInput("Day10_test_01")).size == 1)
    check(countTrailhead(readInput("Day10_test_02")).size == 2)
    check(countTrailhead(readInput("Day10_test_03")).size == 4)
    check(countTrailhead(readInput("Day10_test_04")).size == 3)
    check(countTrailhead(readInput("Day10_test_05")).size == 36)
    println("Passed test 2")

    println(countTrailhead(readInput("Day10_quizz")).size)

}

data class Node(val location: Location, val value: Int)

class DFS(private val board: List<List<Node>>) {

    private val boardLength = board[0].size
    private val boardHeight = board.size

    fun findReachablePeaks(start: Node, endValue: Int = 9): List<Node> {
        val visited = mutableSetOf<Node>()
        val stack = Stack<Node>()
        val reachedPeaks = mutableSetOf<Node>()
        stack.push(start)
        while (stack.isNotEmpty()) {
            val current = stack.pop()
            if (current.value == endValue) {
                reachedPeaks.add(current)
            }
            visited.add(current)
            val neighbors = current.getNeighbors()
            stack.addAll(neighbors.filter { it !in visited })
        }
        return reachedPeaks.toList()
    }

    private fun Node.getNeighbors(): List<Node> = listOfNotNull(
        getRightNeighbor(),
        getTopNeighbor(),
        getBottomNeighbor(),
        getLeftNeighbor()
    )

    private fun Node.getRightNeighbor(): Node? {
        val x = location.x
        val y = location.y
        if (x+1 <= boardLength) {
            val right = board[y.toArrayIndex()][x.toArrayIndex()+1]
            if (right.value - 1 == value) {
                return right
            }
        }
        return null
    }

    private fun Int.toArrayIndex(): Int = this - 1

    private fun Node.getLeftNeighbor(): Node? {
        val x = location.x
        val y = location.y
        if (x-1 >= 1) {
            val left = board[y.toArrayIndex()][x.toArrayIndex()-1]
            if (left.value - 1 == value) {
                return left
            }
        }
        return null
    }

    private fun Node.getTopNeighbor(): Node? {
        val x = location.x
        val y = location.y
        if (y-1 >= 1) {
            val top = board[y.toArrayIndex()-1][x.toArrayIndex()]
            if (top.value - 1 == value) {
                return top
            }
        }
        return null
    }

    private fun Node.getBottomNeighbor(): Node? {
        val x = location.x
        val y = location.y
        if (y+1 <= boardHeight) {
            val bottom = board[y.toArrayIndex()+1][x.toArrayIndex()]
            if (bottom.value - 1 == value) {
                return bottom
            }
        }
        return null
    }



}