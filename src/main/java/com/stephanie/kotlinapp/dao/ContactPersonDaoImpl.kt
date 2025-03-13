package com.stephanie.kotlinapp.dao

import at.favre.lib.crypto.bcrypt.BCrypt
import com.mongodb.client.model.Filters
import com.mongodb.client.model.UpdateOptions
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoCollection
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.stephanie.kotlinapp.model.ContactPerson
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.bson.types.ObjectId

class ContactPersonDaoImpl(database: MongoDatabase) : ContactPersonDao {
    private val collection: MongoCollection<ContactPerson> = database.getCollection("contacts")

    override fun createTable() {
        // MongoDB does not require explicit table (collection) creation
    }

    override fun insert(name: String?, email: String?, phone: String?, password: String): ObjectId = runBlocking {
        val contact = ContactPerson(ObjectId(), name ?: "", email ?: "", phone ?: "", password)
        collection.insertOne(contact)
        contact.id
    }

    override fun findById(id: ObjectId): ContactPerson? = runBlocking {
        collection.find(Filters.eq("_id", id)).firstOrNull()
    }

    override fun findAll(): List<Map<String, Any>> = runBlocking {
        collection.find().map { contact ->
            mapOf(
                "id" to contact.id.toHexString(),
                "name" to contact.name,
                "email" to contact.email,
                "phone" to contact.phone
            )
        }.toList()
    }

    override fun update(id: ObjectId, name: String?, email: String?, phone: String?): Boolean = runBlocking {
        val updates = mutableListOf<org.bson.conversions.Bson>()

        if (!name.isNullOrEmpty()) updates.add(Updates.set("name", name))
        if (!email.isNullOrEmpty()) updates.add(Updates.set("email", email))
        if (!phone.isNullOrEmpty()) updates.add(Updates.set("phone", phone))

        if (updates.isEmpty()) return@runBlocking false // No updates provided

        val result = collection.updateOne(
            Filters.eq("_id", id),
            Updates.combine(updates),
            UpdateOptions().upsert(false)
        )

        return@runBlocking result.modifiedCount > 0 // ✅ Returns `true` if the document was updated
    }

    override fun delete(id: ObjectId) = runBlocking {
        collection.deleteOne(Filters.eq("_id", id))
    }.let { }

    override fun findByEmailAndPassword(email: String, password: String): ContactPerson? = runBlocking {
        val contact = collection.find(Filters.eq("email", email)).firstOrNull()

        if (contact == null) {
            println("User not found for email: $email")
            return@runBlocking null
        }

        if (contact.password.isNullOrBlank()) {
            println("Error: Retrieved password is null or blank for user: ${contact.email}")
            return@runBlocking null
        }


        val passwordVerified = BCrypt.verifyer().verify(password.toCharArray(), contact.password).verified
        if (passwordVerified) {
            println("Password verified for user: ${contact.email}")
            return@runBlocking contact
        } else {
            println("Incorrect password for user: ${contact.email}")
            return@runBlocking null
        }
    }

    override fun findByEmail(email: String): ContactPerson? = runBlocking {
        val contact = collection.find(Filters.eq("email", email)).firstOrNull()

        if (contact == null) {
            println("User not found for email: $email")
            return@runBlocking null
        } else {
            return@runBlocking contact
        }
    }


}
