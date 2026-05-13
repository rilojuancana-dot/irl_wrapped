package com.example.irl_wrapped.model

import kotlinx.serialization.Serializable

@Serializable
data class Emoji(
    val id: Int,
    val unicode: String
)
