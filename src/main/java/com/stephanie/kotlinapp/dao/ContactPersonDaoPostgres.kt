package com.stephanie.kotlinapp.dao

import com.stephanie.kotlinapp.model.ContactPerson
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

@RegisterBeanMapper(ContactPerson::class)
interface ContactPersonDaoPostgres : ContactPersonDao {

    @SqlUpdate(
        "CREATE TABLE IF NOT EXISTS contacts (" +
                "id SERIAL PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "email VARCHAR(255) UNIQUE NOT NULL, " +
                "phone VARCHAR(20) NOT NULL)"
    )
    override fun createTable()

    @SqlUpdate("INSERT INTO contacts (name, email, phone) VALUES (?, ?, ?)")
    @GetGeneratedKeys
    override fun insert(name: String?, email: String?, phone: String?): String

    @SqlQuery("SELECT * FROM contacts WHERE id = ?")
    override fun findById(id: String): ContactPerson?

    @SqlQuery("SELECT * FROM contacts")
    override fun findAll(): List<ContactPerson>

    @SqlUpdate("UPDATE contacts SET name = ?, email = ?, phone = ? WHERE id = ?")
    override fun update(id: String, name: String?, email: String?, phone: String?)

    @SqlUpdate("DELETE FROM contacts WHERE id = ?")
    override fun delete(id: String)
}