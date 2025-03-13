package com.stephanie.kotlinapp

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.stephanie.kotlinapp.controller.AuthResource
import com.stephanie.kotlinapp.controller.ContactPersonResource
import com.stephanie.kotlinapp.dao.ContactPersonDaoImpl
import io.dropwizard.Application
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment
import kotlinx.coroutines.*
import org.eclipse.jetty.servlets.CrossOriginFilter
import java.util.*
import javax.servlet.DispatcherType


class ContactApp : Application<ContactAppConfiguration>() {
    override fun initialize(bootstrap: Bootstrap<ContactAppConfiguration>) {}

    override fun run(configuration: ContactAppConfiguration, environment: Environment) {

        val filter = environment.servlets().addFilter("CORS", CrossOriginFilter::class.java)
        filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*")
        filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,POST,PUT,DELETE,OPTIONS")
        filter.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "Content-Type, Authorization")
        filter.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true")

        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType::class.java), true, "/*")


        //val databaseConfig = configuration.database
        //val jdbi = Jdbi.create(databaseConfig!!.build(environment.metrics(), "postgresql"))

        //jdbi.installPlugin(SqlObjectPlugin())
        //val contactDAO = jdbi.onDemand(ContactPersonDao::class.java)
        //contactDAO.createTable()

        val mongoClient = MongoDBClient(configuration.mongodb.uri, configuration.mongodb.database)

        mongoClient.start()
        val database = mongoClient.getDatabase()

        environment.lifecycle().manage(mongoClient)

        val contactDAO = ContactPersonDaoImpl(database)
        environment.jersey().register(CorsFilter())
        environment.jersey().register(ContactPersonResource(contactDAO))
        environment.jersey().register(AuthResource(contactDAO))

        environment.objectMapper.apply {
            registerModule(KotlinModule.Builder().build())
            disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        }

    }

    companion object {
        @JvmStatic
        infix fun Int.times(str: String) = str.repeat(this)

        @Throws(Exception::class)
        @JvmStatic
        fun main(args: Array<String>) {

            ContactApp().run(*args)

            /*
            MyClass.greet()

            val result = MyClass.sum(5, 10)
            println("Sum: $result") // Sum: 15


            MyClass.greetUser() // Hello, Guest!
            MyClass.greetUser("John") // Hello, John!

            println(MyClass.factorial(5))


            val add: (Int, Int) -> Int = { a, b -> a + b }
            println(add(5, 10)) // 15

            val numbers = listOf(1, 2, 3, 4, 5)
            numbers.forEach { println(it * it) } // Prints squares


            MyClass.measureTime {
                println("Running task...")
            }

            println(3 times "Hello ")

            val multiply = fun(a: Int, b: Int): Int {
                return a * b
            }

            println(multiply(3, 4))

            val sum = operateOnNumbers(10, 20) { x, y -> x + y }
            println(sum) // 30


            MyClass.checkType("Hello")
            MyClass.checkType(10)

            MyClass.printLength("Hello Kotlin")

            */

        }

        fun operateOnNumbers(a: Int, b: Int, operation: (Int, Int) -> Int): Int {
            return operation(a, b)
        }

        fun runBlock() = runBlocking {
            launch {
                delay(1000L)
                println("Hello from Coroutine!")
            }
            println("Hello from main thread")
        }
    }



}

class MyClass {
    companion object {
        fun greet() {
            println("Hello, Kotlin!")
        }

        fun sum(a: Int, b: Int): Int {
            return a + b
        }

        fun greetUser(name: String = "Guest") {
            println("Hello, $name!")
        }

        fun factorial(n: Int): Int {
            return if (n == 1) 1 else n * factorial(n - 1)
        }

        inline fun measureTime(task: () -> Unit) {
            val start = System.currentTimeMillis()
            task()
            val end = System.currentTimeMillis()
            println("Time taken: ${end - start} ms")
        }


        fun checkType(x: Any) {
            if (x is String) {
                println("The variable is a String with length ${x.length}")
            } else if (x is Int) {
                println("The variable is an Int with value $x")
            } else {
                println("Unknown type")
            }
        }


        fun printLength(obj: Any) {
            if (obj is String) {
                // No explicit cast needed
                println("String length: ${obj.length}")
            }
        }
    }
}