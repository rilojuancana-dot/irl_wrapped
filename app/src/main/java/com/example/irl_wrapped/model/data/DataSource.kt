package com.example.irl_wrapped.model.data

import com.example.irl_wrapped.model.*

object DataSource {
    val personas = listOf(
        Persona(1, "Ana"),
        Persona(2, "Carlos"),
        Persona(3, "Elena"),
        Persona(4, "David")
    )

    val temas = listOf(
        Tema(1, "Viajes"),
        Tema(2, "Comida"),
        Tema(3, "Deporte"),
        Tema(4, "Música")
    )

    val emojis = listOf(
        Emoji(1, "\uD83C\uDF0D"), // 🌍
        Emoji(2, "\uD83C\uDF55"), // 🍕
        Emoji(3, "\uD83C\uDFC0"), // 🏀
        Emoji(4, "\uD83C\uDFB8")  // 🎸
    )

    val recuerdos = listOf(
        Recuerdo(
            id = 1,
            nombre = "Viaje a Roma",
            descripcion = "Visitamos el Coliseo y comimos mucha pasta.",
            tema = temas[0],
            emoji = emojis[0],
            personas = listOf(personas[0], personas[1])
        ),
        Recuerdo(
            id = 2,
            nombre = "Concierto Rock",
            descripcion = "Increíble noche con música en directo.",
            tema = temas[3],
            emoji = emojis[3],
            personas = listOf(personas[2], personas[3])
        ),
        Recuerdo(
            id = 3,
            nombre = "Cena de Cumpleaños",
            descripcion = "Pizza deliciosa con amigos.",
            tema = temas[1],
            emoji = emojis[1],
            personas = personas
        )
    )

    val recopilatorios = listOf(
        Recopilatorio(
            id = 1,
            nombre = "Lo mejor del 2023",
            recuerdos = recuerdos
        ),
        Recopilatorio(
            id = 2,
            nombre = "Verano 2024",
            recuerdos = listOf(recuerdos[0])
        )
    )

    val usuarios = listOf(
        Usuario(1, "Diego", "diego@example.com"),
        Usuario(2, "Laura", "laura@example.com")
    )
}
