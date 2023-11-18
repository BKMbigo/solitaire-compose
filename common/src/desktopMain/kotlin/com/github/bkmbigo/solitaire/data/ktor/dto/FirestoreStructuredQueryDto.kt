package com.github.bkmbigo.solitaire.data.ktor.dto

import kotlinx.serialization.Serializable

@Serializable
internal data class FirestoreStructuredQueryDto(
    val where: FirestoreWhereDto = FirestoreWhereDto(),
    val orderBy: List<FirestoreOrderByDto> = emptyList(),
    val from: List<Map<String, String>> = emptyList()
)
