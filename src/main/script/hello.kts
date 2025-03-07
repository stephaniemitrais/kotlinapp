
 var username: String?
    do {
        print("Enter your name: ")
        username = readLine()?.trim()
    } while (username.isNullOrEmpty()) // Keep asking if input is empty

 println("Hello, $username!")


