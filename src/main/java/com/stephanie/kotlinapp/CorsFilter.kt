package com.stephanie.kotlinapp

import javax.ws.rs.container.ContainerResponseContext
import javax.ws.rs.container.ContainerResponseFilter
import javax.ws.rs.ext.Provider

@Provider
class CorsFilter : ContainerResponseFilter {
    override fun filter(requestContext: javax.ws.rs.container.ContainerRequestContext, responseContext: ContainerResponseContext) {
        responseContext.headers.add("Access-Control-Allow-Origin", "http://localhost:5173") // ✅ Set frontend URL
        responseContext.headers.add("Access-Control-Allow-Credentials", "true") // ✅ Allow credentials
        responseContext.headers.add("Access-Control-Allow-Methods", "OPTIONS, GET, POST, PUT, DELETE") // ✅ Allowed methods
        responseContext.headers.add("Access-Control-Allow-Headers", "Content-Type, Authorization") // ✅ Allowed headers
    }
}