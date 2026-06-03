package com.example.irl_wrapped.ui.view

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import coil.compose.AsyncImage
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.irl_wrapped.model.Emoji
import com.example.irl_wrapped.model.Lugar
import com.example.irl_wrapped.model.Persona
import com.example.irl_wrapped.model.Recuerdo
import com.example.irl_wrapped.model.Tema
import com.example.irl_wrapped.model.data.DataSource
import com.example.irl_wrapped.ui.view.components.ClickableCard
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecuerdoPage(recuerdo: Recuerdo?, onSave: (Recuerdo) -> Unit,  onNavigateBack: () -> Unit, imagen : Bitmap){
    var expanded by remember { mutableStateOf(false) }
    var temas = DataSource.temas

    var name by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var lugar by remember { mutableStateOf("") }
    var temaVar by remember { mutableStateOf("Tema") }
    var emoji by remember { mutableStateOf("") }
    var personas by remember { mutableStateOf("") }
    if(recuerdo != null){
        name = recuerdo.name
        descripcion = recuerdo.descripcion
        lugar = recuerdo.lugar.name?:""
        temaVar = recuerdo.tema.name?:""
        emoji = recuerdo.emoji.unicode?:""
        personas = recuerdo.personas.joinToString{"${it.name} "}
    }

    Log.d("RecuerdoPage", "Temas: ${temas.joinToString { it.name?:"" }}")
    Log.d("Imagen en RecuerdoPage", "Imagen: ${imagen.height}")
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row (
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth().fillMaxHeight()
                    ){
                        Text(text = if (recuerdo != null) recuerdo.name else "Añadir", style = MaterialTheme.typography.headlineLarge,)
                    }
                },
            )
        }
    ) { contentPadding ->
        Column (modifier = Modifier.padding(contentPadding).fillMaxWidth().fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Box(
                modifier = Modifier.weight(1.25f)
            ){
                AsyncImage(
                    model = if (imagen != null) imagen else recuerdo?:imagen,
                    contentDescription = "Imagen del recuerdo",
                    modifier = Modifier.fillMaxHeight().fillMaxWidth(),
                )
            }
            Box(
                modifier = Modifier.weight(1f)
            ){
                Column(
                    modifier = Modifier.padding(8.dp).verticalScroll(rememberScrollState(0)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text(text = "${if (recuerdo != null) "Modificar" else "Añadir"} nombre") },
                        placeholder = { Text("Escribe un nombre") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = descripcion,
                        onValueChange = { descripcion = it },
                        label = { Text(text = "${if (recuerdo != null) "Modificar" else "Añadir"} descripción") },
                        placeholder = { Text("Escribe una descripción...") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = false,
                        maxLines = 3
                    )

                    OutlinedTextField(
                        value = lugar,
                        onValueChange = { lugar = it },
                        label = { Text("Lugar") },
                        placeholder = { Text("Madrid, Biblioteca...") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = emoji,
                        onValueChange = { emoji = it },
                        label = { Text("Emoji") },
                        placeholder = { Text("😊, 🎉, ❤️") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Box(){
                        ClickableCard(
                            text = temaVar,
                            onClick = {expanded = !expanded},
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp).fillMaxWidth(1f).height(50.dp)
                        )
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },

                        ) {
                            temas.forEach { tema ->
                                DropdownMenuItem(
                                    text = { Text(text = tema.name ?: "") },
                                    onClick = {
                                        temaVar = tema.name ?:""
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                    OutlinedTextField(
                        value = personas,
                        onValueChange = { personas = it },
                        label = { Text("Personas") },
                        placeholder = { Text("Nombres separados por espacios") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = false,
                        maxLines = 2
                    )

                    ClickableCard(
                        text = "Finalizar",
                        onClick = {
                            onSave(Recuerdo(
                                if (recuerdo == null) 0L else recuerdo.id,
                                name,
                                descripcion,
                                Tema(temaVar),
                                Emoji(emoji),
                                Lugar(lugar),
                                personas.split(",").map { Persona(it) },
                                recuerdo?.imageUrl
                            ))
                            onNavigateBack()
                        },
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp).fillMaxWidth(1f).height(50.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecuerdoPagePreview(){
    var recuerdo = DataSource.recuerdos[0]
    //RecuerdoPage(recuerdo)
}

