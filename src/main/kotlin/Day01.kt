fun main() {
    fun part1(input: List<String>): Int {
        var antennasToLocations = mutableMapOf<Char, List<Location>>()
        input.mapIndexed { y, string ->
            string.toCharArray()
                .filterNot { it.isWhitespace() }
                .mapIndexed { x, char ->
                    char.takeIf { char != '.' }
                        ?.let {
                            antennasToLocations
                                .getOrPut(char) { emptyList() }
                                .let { antennasToLocations.put(char, it.plus(Location(x+1, y+1))) }
                        }
                }
        }

        //calculate the distance between each pair of antennas
        antennasToLocations.map { (antenna, locations) ->
            var locationToWork = locations.toMutableList()
            while (locationToWork.size >= 2) {
                val workingLocation = locationToWork.removeFirst()
                locations.map { location2 ->
                    val distance = Math.abs(workingLocation.x - location2.x) + Math.abs(workingLocation.y - location2.y)
//                    println("Distance between $antenna and $antenna2 is $distance")
                }
            }
        }
        return antennasToLocations.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day08_test_02")
    check(part1(testInput) == 2)

    // Read the input from the `src/Day01.txt` file.
//    val input = readInput("Day01")
//    part1(input).println()
//    part2(input).println()
}

data class Location(val x: Int, val y: Int)