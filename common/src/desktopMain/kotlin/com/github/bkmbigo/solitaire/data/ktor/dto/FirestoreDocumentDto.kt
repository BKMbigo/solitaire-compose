package com.github.bkmbigo.solitaire.data.ktor.dto

import kotlinx.serialization.Serializable

@Serializable
/** Returned during an API call to represent a single document */
data class FirestoreDocumentDto(
    val name: String = "",
    val fields: FirestoreFieldMap = emptyMap()
)
