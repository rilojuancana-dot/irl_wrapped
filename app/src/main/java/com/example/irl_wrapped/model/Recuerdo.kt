package com.example.irl_wrapped.model

data class Recuerdo(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val tema: Tema,
    val emoji: Emoji,
    val personas: List<Persona>

)
