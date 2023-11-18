package com.github.bkmbigo.solitaire.data.ktor.utils

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ApiError(
    @SerialName("status_code")
    val statusCode: Int?,

    @SerialName("status_message")
    val statusMessage: String?,

    val success: Boolean?
)
