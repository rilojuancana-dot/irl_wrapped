package com.example.irl_wrapped.model

import kotlinx.serialization.Serializable

@Serializable
data class Recopilatorio(
    val id: Long,
    val name: String,
    val recuerdos: List<Recuerdo>
)
