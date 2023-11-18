package com.github.bkmbigo.solitaire.data.ktor.dto

import kotlinx.serialization.Serializable

@Serializable
internal data class FirestoreOrderByDto(
    val field: Map<String, String>,
    val direction: FirestoreFieldDirection
)
