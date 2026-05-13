package com.example.irl_wrapped.ui.view

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.irl_wrapped.model.Recuerdo
import com.example.irl_wrapped.model.data.DataSource
import com.example.irl_wrapped.ui.view.components.ClickableCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecuerdoPage(recuerdo: Recuerdo){
    var expanded by remember { mutableStateOf(false) }
    var temas = DataSource.temas
    Log.d("RecuerdoPage", "Temas: ${temas.joinToString { it.nombre }}")
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row (
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth().fillMaxHeight()
                    ){
                        Text(text = recuerdo.nombre, style = MaterialTheme.typography.headlineLarge,)
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
                    model = recuerdo.imagen,
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
                    ClickableCard(
                        text = "Modificar descripcion",
                        onClick = {},
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp).fillMaxWidth(1f).height(50.dp)
                    )
                    ClickableCard(
                        text = recuerdo.lugar.nombre,
                        onClick = {},
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp).fillMaxWidth(1f).height(50.dp)
                    )
                    ClickableCard(
                        text = recuerdo.emoji.unicode,
                        onClick = {},
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp).fillMaxWidth(1f).height(50.dp)
                    )
                    Box(){
                        ClickableCard(
                            text = recuerdo.tema.nombre,
                            onClick = {expanded = !expanded},
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp).fillMaxWidth(1f).height(50.dp)
                        )
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },

                        ) {
                            temas.forEach { tema ->
                                DropdownMenuItem(
                                    text = { Text(text = tema.nombre) },
                                    onClick = {
                                        Log.d("DropdownMenu", "Tema seleccionado: ${tema.nombre}")
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                    ClickableCard(
                        text = recuerdo.personas.joinToString { it.nombre},
                        onClick = {},
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp).fillMaxWidth(1f).height(50.dp)
                    )
                    ClickableCard(
                        text = "Finalizar",
                        onClick = {},
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
    RecuerdoPage(recuerdo)
}

