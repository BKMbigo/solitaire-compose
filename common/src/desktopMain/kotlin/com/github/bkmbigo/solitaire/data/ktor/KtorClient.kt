package com.github.bkmbigo.solitaire.data.ktor

import io.ktor.client.*
import io.ktor.client.engine.java.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.observer.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

val ktorClient = HttpClient(Java) {
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            }, contentType = ContentType.Any
        )
    }

    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                /* Used for debugging */
//                com.github.bkmbigo.solitaire.utils.Logger.LogInfo("Ktor", "Log Event: $message")
            }
        }
    }

    install(ResponseObserver) {
        onResponse {
            /* Used for debugging only */
//            com.github.bkmbigo.solitaire.utils.Logger.LogInfo(
//                "Ktor Response",
//                "Status: ${it.status.value}: ${it.status}, Call: ${it.call},Body: ${it.call.body<String>()}"
//            )
        }
    }
}
