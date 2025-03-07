println("========Basic if-else=========")
val num = 10

if (num > 0) {
    println("Positive number")
} else if (num < 0) {
    println("Negative number")
} else {
    println("Zero")
}

println("========if expression=========")
val a = 10
val b = 20

val max = if (a > b) a else b  // Returns the larger number
println("Max value: $max")

println("======== while loop=========")

var i = 1
while (i <= 5) {
    println(i)
    i++  // Increment counter
}

println("======== do while=========")
var xx = 6

do {
    println("Number: $xx")
    xx++
} while (xx <= 5)  // Condition is false, but executes once!


println("======== loop through a range=========")
for (wee in 1..5) { // Range from 1 to 5
    println(wee)
}

println("======== loop through an array=========")
val fruits = arrayOf("Apple", "Banana", "Cherry")

for (fruit in fruits) {
    println(fruit)
}

println("======== down to and step =========")
for (yeye in 10 downTo 1 step 2) { // Counts backward with step 2
    println(yeye)
}

println("======== when =========")
val day = 3

when (day) {
    1 -> println("Monday")
    2 -> println("Tuesday")
    3 -> println("Wednesday")
    else -> println("Invalid day")
}

println("======== when as an expression=========")
val numb = 10
val result = when {
    numb > 0 -> "Positive"
    numb < 0 -> "Negative"
    else -> "Zero"
}
println(result)


println("======== unlabeled break=========")
for (aa in 1..5) {
    if (aa == 3) break // Exits loop when aa = 3
    println(aa)
}

println("======== labeled break=========")
outer@ for (bb in 1..3) {
    for (cc in 1..3) {
        if (bb == 2 && cc == 2) break@outer  // Breaks outer loop
        println("bb=$bb, cc=$cc")
    }
}


println("======== unlabeled continue =========")
for (xx in 1..5) {
    if (xx == 3) continue // Skips when xx = 3
    println(xx)
}


println("======== labeled continue =========")
outer@ for (xx in 1..3) {
    for (yy in 1..3) {
        if (yy == 2 && yy == 2) continue@outer // Skips only this case
        println("xx=$xx, yy=$yy")
    }
}