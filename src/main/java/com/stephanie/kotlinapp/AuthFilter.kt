package com.stephanie.kotlinapp

import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter
import javax.ws.rs.core.Response
import javax.ws.rs.ext.Provider

@Provider
class AuthFilter : ContainerRequestFilter {
    override fun filter(requestContext: ContainerRequestContext) {
        val authHeader = requestContext.getHeaderString("Authorization")

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build())
        }

        val token = authHeader.substring(7)

        if (!isValidToken(token)) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build())
        }
    }

    private fun isValidToken(token: String): Boolean {
        return token == "dummy-token" // TODO: Replace with real token validation logic
    }
}