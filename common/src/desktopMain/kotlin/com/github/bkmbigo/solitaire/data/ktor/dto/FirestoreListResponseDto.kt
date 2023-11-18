package com.github.bkmbigo.solitaire.data.ktor.dto

import kotlinx.serialization.Serializable

@Serializable
data class FirestoreListResponseDto(
    val documents: List<FirestoreDocumentDto> = emptyList(),
    val nextPageToken: String? = null
)
