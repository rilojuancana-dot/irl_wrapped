package com.example.irl_wrapped.model

import kotlinx.serialization.Serializable

@Serializable
data class Recopilatorio(
    val id: Int,
    val nombre: String,
    val recuerdos: List<Recuerdo>
)
