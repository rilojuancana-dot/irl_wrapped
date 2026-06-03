package com.example.irl_wrapped.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
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
import com.example.irl_wrapped.model.Recopilatorio
import com.example.irl_wrapped.ui.view.components.ClickableCard


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecopilatorioCreatingForm(onSave : (recopilatorio : String) -> Unit, onBack : () -> Unit){
    var nombre by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Crear Recopilatorio")
                }
            )
        }
    ) {contentPadding->
        Column(
            modifier = Modifier.padding(contentPadding).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Box(){
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre del recopilatorio:") },
                    placeholder = { Text("Ej: Enero 2025, Vacaciones en Madrid...") },
                    modifier = Modifier.fillMaxWidth(0.75f),
                    singleLine = true
                )

            }
            ClickableCard(
                text = "Finalizar",
                onClick = {
                    if (nombre!= ""){
                        onSave(nombre)
                        onBack()
                    }
                },
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp).fillMaxWidth(0.8f).height(50.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecopilatorioCreatingFormPreview(){
    //return RecopilatorioCreatingForm()
}