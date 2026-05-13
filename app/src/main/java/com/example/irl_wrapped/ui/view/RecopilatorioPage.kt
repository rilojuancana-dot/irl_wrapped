package com.example.irl_wrapped.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.irl_wrapped.model.Recopilatorio
import com.example.irl_wrapped.model.data.DataSource
import com.example.irl_wrapped.ui.view.components.ClickableCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecopilatorioPage(recopilatorio: Recopilatorio){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row (
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth().fillMaxHeight()
                    ){
                        Text(text = recopilatorio.nombre, style = MaterialTheme.typography.headlineLarge,)
                    }
                },
            )
        }
    ){contentPadding->
        Column(modifier = Modifier.padding(contentPadding).fillMaxHeight(1f)) {
            ClickableCard(
                text = "Añadir nuevo recuerdo",
                onClick = {},
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp).fillMaxWidth(1f)
                    .weight(1f)
            )
            ClickableCard(
                text = "Modificar recuerdo antiguo",
                onClick = {},
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp).fillMaxWidth(1f)
                    .weight(1f)
            )
            ClickableCard(
                text = "Borrar recuerdo antiguo",
                onClick = {},
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp).fillMaxWidth(1f)
                    .weight(1f)
            )
            ClickableCard(
                text = "Generar estadísticas",
                onClick = {},
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp).fillMaxWidth(1f)
                    .weight(1f)
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun RecopilatorioPagePreview(){
    RecopilatorioPage(DataSource.recopilatorios[0])
}




