package com.stephanie.kotlinapp.controller

import at.favre.lib.crypto.bcrypt.BCrypt
import com.stephanie.kotlinapp.dao.ContactPersonDao
import com.stephanie.kotlinapp.model.LoginRequest
import com.stephanie.kotlinapp.model.LoginResponse
import com.stephanie.kotlinapp.model.RegisterRequest
import org.bson.types.ObjectId
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class AuthResource (private val contactPersonDao: ContactPersonDao) {

    @POST
    @Path("/login")
    fun login(request: LoginRequest): Response {
        val contact = contactPersonDao.findByEmailAndPassword(request.email, request.password)

        return if (contact != null) {
            Response.ok(LoginResponse(true, "Login successful", contact.name, "dummy-token"))
                .build()
        } else {
            Response.status(Response.Status.UNAUTHORIZED)
                .entity(LoginResponse(false, "Invalid credentials", null,null))
                .build()
        }
    }


    @POST
    @Path("/register")
    fun register(request: RegisterRequest): Response {
        val existingUser = contactPersonDao.findByEmail(request.email)
        if (existingUser != null) {
            return Response.status(Response.Status.CONFLICT)
                .entity(mapOf("message" to "Email is already registered"))
                .build()
        }

        try {

            val hashedPassword = BCrypt.withDefaults().hashToString(12, request.password.toCharArray())

            val newUserId: ObjectId = contactPersonDao.insert(request.name, request.email, request.phone, hashedPassword)

            return Response.status(Response.Status.CREATED)
                .entity(mapOf("message" to "User registered successfully", "id" to newUserId.toHexString()))
                .build()
        } catch (e: Exception) {
            println("Error inserting user: ${e.message}")
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(mapOf("message" to "Failed to register user"))
                .build()
        }
    }
}