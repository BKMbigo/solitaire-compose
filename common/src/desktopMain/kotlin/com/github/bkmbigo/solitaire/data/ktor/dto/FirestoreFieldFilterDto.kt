package com.github.bkmbigo.solitaire.data.ktor.dto

import kotlinx.serialization.Serializable

@Serializable
internal data class FirestoreFieldFilterDto(
    val field: Map<String, String> = emptyMap(),
    val op: String = "EQUAL",
    val value: Map<String, String> = emptyMap()
)
