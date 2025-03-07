package com.stephanie.kotlinapp.dao

import com.stephanie.kotlinapp.model.ContactPerson

interface ContactPersonDao {
    fun createTable() // Only relevant for SQL databases

    fun insert(name: String?, email: String?, phone: String?): String

    fun findById(id: String): ContactPerson?

    fun findAll(): List<ContactPerson>

    fun update(id: String, name: String?, email: String?, phone: String?)

    fun delete(id: String)
}