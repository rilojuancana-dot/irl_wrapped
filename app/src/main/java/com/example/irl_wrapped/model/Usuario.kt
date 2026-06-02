package com.example.irl_wrapped.model

import kotlinx.serialization.Serializable

@Serializable
data class Usuario(
    val id: Long,
    val name: String,
    val email: String
)
