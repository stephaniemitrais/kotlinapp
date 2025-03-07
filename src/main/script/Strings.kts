println("======= 1. Declare a String =======")
val message: String = "Hello, Kotlin!"
println(message)

println("======= 2. Accessing Characters in a String =======")
val text = "Kotlin"
println(text[0])
println(text.last())

println("======= 3. String Length =======")
val name = "Stephanie"
println(name.length)

println("======= 4. Iterating Over a String =======")
val word = "Hello"
for (ch in word) {
    println(ch)
}

println("======= 5. String Concatenation =======")
val firstName = "John"
val lastName = "Doe"
println(firstName + " " + lastName)
println("$firstName $lastName")

println("======= 6. String Interpolation =======")
val age = 25
println("I am $age years old")
println("Next year, I will be ${age + 1}")

println("======= 7. Multi-line String =======")
val paragraph = """
    Kotlin is fun!
    It's concise and powerful.
""".trimIndent()
println(paragraph)

println("======= 8. String Methods =======")
val phrase = "Kotlin Programming"
println(phrase.toUpperCase())
println(phrase.toLowerCase())
println(phrase.contains("Kotlin"))
println(phrase.startsWith("Kot"))
println(phrase.endsWith("ing"))
println(phrase.replace("Kotlin", "Java"))

println("======= 9. Substring Extraction =======")
val text2 = "Kotlin Programming"
println(text2.substring(0, 6))

println("======= 10. String vs. Char =======")
val letter: Char = 'A'
val word2: String = "Apple"
println(letter)
println(word2)
