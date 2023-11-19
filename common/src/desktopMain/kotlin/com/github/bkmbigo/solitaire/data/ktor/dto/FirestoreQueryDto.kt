package com.github.bkmbigo.solitaire.data.ktor.dto

import kotlinx.serialization.Serializable

@Serializable
internal data class FirestoreQueryDto(
    val structuredQuery: FirestoreStructuredQueryDto = FirestoreStructuredQueryDto()
)
