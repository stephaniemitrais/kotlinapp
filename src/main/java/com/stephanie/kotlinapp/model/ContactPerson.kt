package com.stephanie.kotlinapp.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId


data class ContactPerson(
    @BsonId val id: String = ObjectId().toHexString(),  // Store ObjectId as String
    val name: String,
    val email: String,
    val phone: String
)

