package com.stephanie.kotlinapp

import com.mongodb.MongoClientSettings

import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase

import io.dropwizard.lifecycle.Managed
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.configuration.CodecRegistry
import org.bson.codecs.pojo.PojoCodecProvider


class MongoDBClient(private val uri: String, private val dbName: String) : Managed {
    private lateinit var client: MongoClient
    private var database: MongoDatabase? = null

    override fun start() {
        val codecRegistry: CodecRegistry = CodecRegistries.fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())
        )

        // Create MongoDB client with extended CodecRegistry
        client = MongoClient.create(uri)
        database = client.getDatabase(dbName).withCodecRegistry(codecRegistry)
        println("Connected to MongoDB: $dbName")
    }

    override fun stop() {
        client.close()
        println("MongoDB connection closed.")
    }

    fun getDatabase(): MongoDatabase {
        return database ?: throw IllegalStateException("MongoDB has not been initialized. Call start() first.")
    }
}