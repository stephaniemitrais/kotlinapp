package com.stephanie.kotlinapp.dao

import com.mongodb.client.model.Filters
import com.mongodb.client.model.UpdateOptions
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoCollection
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.stephanie.kotlinapp.model.ContactPerson
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.bson.types.ObjectId

class ContactPersonDaoImpl(database: MongoDatabase) : ContactPersonDao {
    private val collection: MongoCollection<ContactPerson> = database.getCollection("contacts")

    override fun createTable() {
        // MongoDB does not require explicit table (collection) creation
    }

    override fun insert(name: String?, email: String?, phone: String?): String = runBlocking {
        val contact = ContactPerson(ObjectId().toHexString(), name ?: "", email ?: "", phone ?: "")
        collection.insertOne(contact)
        contact.id // Return ObjectId as String
    }

    override fun findById(id: String): ContactPerson? = runBlocking {
        collection.find(Filters.eq("_id", id)).firstOrNull()
    }

    override fun findAll(): List<ContactPerson> = runBlocking {
        collection.find().toList()
    }

    override fun update(id: String, name: String?, email: String?, phone: String?) = runBlocking {
        collection.updateOne(
            Filters.eq("_id", id),
            Updates.combine(
                Updates.set("name", name ?: ""),
                Updates.set("email", email ?: ""),
                Updates.set("phone", phone ?: "")
            ),
            UpdateOptions().upsert(false)
        ).let { }
    }

    override fun delete(id: String) = runBlocking {
        collection.deleteOne(Filters.eq("_id", id))
    }.let { }
}
