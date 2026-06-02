package com.example.irl_wrapped.ui.view

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.irl_wrapped.model.Emoji
import com.example.irl_wrapped.model.Lugar
import com.example.irl_wrapped.model.Persona
import com.example.irl_wrapped.model.Recopilatorio
import com.example.irl_wrapped.model.Tema
import com.example.irl_wrapped.ui.view.components.*
import kotlinx.coroutines.delay
import kotlin.random.Random
import kotlin.random.nextLong

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstadisticasPage(
    recopilatorio: Recopilatorio,
    temaFrecuencia: Map<String, Int>,
    lugarFrecuencia: Map<String, Int>,
    emojiFrecuencia: Map<String, Int>,
    personaFrecuencia: Map<String, Int>,
    onBack: () -> Unit
){
    var counter by remember { mutableStateOf(0) }
    val random = Random.Default
    val randomList = mutableListOf(
        random.nextLong(0, recopilatorio.recuerdos.maxBy { it.id }.id),
    )
    while (randomList.size < 4){
        val randomNumber = random.nextLong(0, recopilatorio.recuerdos.maxBy { it.id }.id)
        if (!randomList.contains(randomNumber)) randomList.add(randomNumber)
    }
    val temaMasFrecuente = temaFrecuencia.maxByOrNull { it.value }?.key
    val lugarMasFrecuente = lugarFrecuencia.maxByOrNull { it.value }?.key
    val emojiMasFrecuente = emojiFrecuencia.maxByOrNull { it.value }?.key
    val personaMasFrecuente = personaFrecuencia.maxByOrNull { it.value }?.key

    LaunchedEffect(Unit) {
        while (true){
            delay(3000)
            counter++
        }
    }

    Scaffold (
        topBar = {
            TopAppBar(title = {
                Text(recopilatorio.name)
            })
        }
    ){contentPadding ->

            Box(modifier = Modifier.padding(contentPadding)) {
                Crossfade(
                    targetState = counter,
                    animationSpec = tween(3000)
                ) { index ->
                    when (index) {
                        0 -> {
                            InitStats(recopilatorio.recuerdos.size)
                        }
                        1 -> LugarTotal(lugarMasFrecuente ?: "Error")
                        2 -> Recap(recopilatorio.recuerdos.map { if (it.lugar.name == lugarMasFrecuente) it.imageUrl?:"" else ""})
                        3 -> EmojiTotal(emojiMasFrecuente ?: "Error")
                        4 -> TemaTotal(temaMasFrecuente ?: "Error")
                        5 -> Recap(recopilatorio.recuerdos.map { if (randomList.contains(it.id)) it.imageUrl?:"" else "" })
                        6 -> Stats(lugarFrecuencia)
                        7 -> Stats(emojiFrecuencia)
                        8 -> Stats(personaFrecuencia)
                        9 -> Stats(temaFrecuencia)
                        10 -> FinStats(
                            lugarMasFrecuente ?: "Error",
                            emojiMasFrecuente ?: "Error",
                            personaMasFrecuente ?: "Error"
                        )

                        11 -> {
                            onBack()
                        }
                    }
                }
            }
    }
}