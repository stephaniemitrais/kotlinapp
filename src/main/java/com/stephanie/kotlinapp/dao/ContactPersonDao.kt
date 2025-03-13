package com.stephanie.kotlinapp.dao

import com.stephanie.kotlinapp.model.ContactPerson
import org.bson.types.ObjectId

interface ContactPersonDao {
    fun createTable() // Only relevant for SQL databases

    fun insert(name: String?, email: String?, phone: String?, password: String): ObjectId

    fun findById(id: ObjectId): ContactPerson?

    fun findAll(): List<Map<String, Any>>

    fun update(id: ObjectId, name: String?, email: String?, phone: String?): Boolean

    fun delete(id: ObjectId)

    fun findByEmailAndPassword(email: String, password: String): ContactPerson?

    fun findByEmail(email: String): ContactPerson?
}