package com.example.irl_wrapped.model

import kotlinx.serialization.Serializable

@Serializable
data class Recuerdo(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val tema: Tema,
    val emoji: Emoji,
    val lugar: Lugar,
    val personas: List<Persona>,
    val imagen: String? = null

)
