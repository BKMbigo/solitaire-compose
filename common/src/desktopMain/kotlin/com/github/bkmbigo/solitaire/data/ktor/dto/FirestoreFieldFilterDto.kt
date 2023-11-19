package com.github.bkmbigo.solitaire.data.ktor.dto

import kotlinx.serialization.Serializable

@Serializable
internal data class FirestoreFieldFilterDto(
    val field: Map<String, String> = emptyMap(),
    /* Workaround as the field  is emitted when there is a default value*/
    val op: String,
    val value: Map<String, String> = emptyMap()
)
