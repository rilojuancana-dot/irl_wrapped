package com.example.irl_wrapped.model.data

import com.example.irl_wrapped.model.*

object DataSource {
    val personas = listOf(
        Persona("Ana"),
        Persona( "Carlos"),
        Persona("Elena"),
        Persona("David")
    )

    val temas = listOf(
        Tema( "Viajes"),
        Tema("Comida"),
        Tema( "Deporte"),
        Tema( "Música")
    )

    val emojis = listOf(
        Emoji( "\uD83C\uDF0D"), // 🌍
        Emoji( "\uD83C\uDF55"), // 🍕
        Emoji( "\uD83C\uDFC0"), // 🏀
        Emoji("\uD83C\uDFB8")  // 🎸
    )
    val lugares = listOf(
        Lugar( "Madrid"),
        Lugar( "Paris"),
        Lugar( "Londres")
    )

    val recuerdos = listOf(
        Recuerdo(
            id = 1,
            name = "Viaje a Roma",
            descripcion = "Visitamos el Coliseo y comimos mucha pasta.",
            tema = temas[0],
            emoji = emojis[0],
            personas = listOf(personas[0], personas[1]),
            imageUrl = "https://picsum.photos/1080/920",
            lugar = lugares[0]
        ),
        Recuerdo(
            id = 2,
            name = "Concierto Rock",
            descripcion = "Increíble noche con música en directo.",
            tema = temas[3],
            emoji = emojis[3],
            personas = listOf(personas[2], personas[3]),
            lugar = lugares[1],

        ),
        Recuerdo(
            id = 3,
            name = "Cena de Cumpleaños",
            descripcion = "Pizza deliciosa con amigos.",
            tema = temas[1],
            emoji = emojis[1],
            personas = personas,
            lugar = lugares[2]

        )
    )

    val recopilatorios = listOf(
        Recopilatorio(
            id = 1,
            name = "Lo mejor del 2023",
            recuerdos = recuerdos
        ),
        Recopilatorio(
            id = 2,
            name = "Verano 2024",
            recuerdos = listOf(recuerdos[0])
        )
    )

    val usuarios = listOf(
        Usuario(1, "Diego", "diego@example.com"),
        Usuario(2, "Laura", "laura@example.com")
    )
}
