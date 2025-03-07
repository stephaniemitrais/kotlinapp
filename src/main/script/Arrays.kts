println("======= 1. Declare and Initialize Arrays =======")
val numbers = arrayOf(1, 2, 3, 4, 5)
val fruits = arrayOf("Apple", "Banana", "Cherry")

println(numbers[0])
println(fruits[2])

println("======= 2. Modify Array Elements =======")
val colors = arrayOf("Red", "Green", "Blue")
colors[1] = "Yellow"
println(colors.joinToString())

println("======= 3. Iterate Over Arrays =======")
val cities = arrayOf("New York", "Paris", "Tokyo")
for (city in cities) {
    println(city)
}
cities.forEach { println(it) }

println("======= 4. Array with Specific Size and Default Values =======")
val zeros = Array(5) { 0 }
val squares = Array(5) { it * it }
println(zeros.joinToString())
println(squares.joinToString())

println("======= 5. Kotlin IntArray, DoubleArray, etc. =======")
val nums = intArrayOf(1, 2, 3, 4, 5)
println(nums.joinToString())

println("======= 6. Array Operations =======")
val arr = arrayOf(10, 20, 30, 40, 50)
println(arr.size)
println(arr.first())
println(arr.last())
println(arr.contains(20))
println(arr.indexOf(30))

println("======= 7. Sorting an Array =======")
val unsortedNums = arrayOf(5, 2, 8, 1, 9)
unsortedNums.sort()
println(unsortedNums.joinToString())
