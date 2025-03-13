package com.stephanie.kotlinapp.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId


data class ContactPerson(
    @BsonId val id: ObjectId = ObjectId(),
    val name: String,
    val email: String,
    val phone: String,
    val password: String

) {

    fun getIdAsString(): String = id.toHexString()
}



