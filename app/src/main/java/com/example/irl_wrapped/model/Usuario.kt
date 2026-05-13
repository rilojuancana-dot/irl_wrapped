package com.example.irl_wrapped.model

import kotlinx.serialization.Serializable

@Serializable
data class Usuario(
    val id: Int,
    val nombre: String,
    val email: String
)
