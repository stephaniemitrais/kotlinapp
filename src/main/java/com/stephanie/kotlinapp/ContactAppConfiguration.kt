package com.stephanie.kotlinapp

import com.fasterxml.jackson.annotation.JsonProperty
import io.dropwizard.Configuration
import io.dropwizard.db.DataSourceFactory
import javax.validation.Valid
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class ContactAppConfiguration : Configuration() {
    @JsonProperty("database")
    var database: @Valid @NotNull DataSourceFactory? = DataSourceFactory()

    @JsonProperty("mongodb")
    lateinit var mongodb: MongoDBConfig

    class MongoDBConfig {
        @NotEmpty
        lateinit var uri: String

        @NotEmpty
        lateinit var database: String
    }
}