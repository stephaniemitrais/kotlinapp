package com.stephanie.kotlinapp.controller

import com.stephanie.kotlinapp.dao.ContactPersonDao
import com.stephanie.kotlinapp.model.ContactPerson
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response


@Path("/contacts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class ContactPersonResource(private val contactDao: ContactPersonDao) {

    @POST
    fun createContact(contact: ContactPerson): Response {
        val id = contactDao.insert(contact.name, contact.email, contact.phone)
        val createdContact = contact.copy(id = id) // Ensure ID is set correctly
        return Response.status(Response.Status.CREATED).entity(createdContact).build()
    }

    @GET
    @Path("/{id}")
    fun getContact(@PathParam("id") id: String): Response {
        val contact = contactDao.findById(id)
            ?: return Response.status(Response.Status.NOT_FOUND).build()
        return Response.ok(contact).build()
    }

    @GET
    fun getAllContacts(): Response {
        val contacts = contactDao.findAll()
        return Response.ok(contacts).build()
    }

    @PUT
    @Path("/{id}")
    fun updateContact(@PathParam("id") id: String, contact: ContactPerson): Response {
        contactDao.update(id, contact.name, contact.email, contact.phone)
        val updatedContact = contact.copy(id = id)
        return Response.ok(updatedContact).build()
    }

    @DELETE
    @Path("/{id}")
    fun deleteContact(@PathParam("id") id: String): Response {
        contactDao.delete(id)
        return Response.status(Response.Status.NO_CONTENT).build()
    }
}
