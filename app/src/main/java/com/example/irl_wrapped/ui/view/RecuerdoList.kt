package com.example.irl_wrapped.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.irl_wrapped.model.Recuerdo
import com.example.irl_wrapped.model.data.DataSource
import com.example.irl_wrapped.ui.view.components.ClickableCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecuerdoList(recuerdos: List<Recuerdo>, recopilatorioNombre: String,  onRecuerdoClick: (Long) -> Unit) {
    val cardHeight = LocalConfiguration.current.screenHeightDp.dp / 6

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row (
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth().fillMaxHeight()
                    ){
                        Text(text = recopilatorioNombre, style = MaterialTheme.typography.headlineLarge,)
                    }
                },
            )
        }
    ) { contentPadding ->
        LazyColumn (contentPadding = contentPadding, modifier = Modifier.fillMaxHeight(1f)){
            items(recuerdos){recuerdo->
                ClickableCard(
                    text = recuerdo.name + "\n \n" + recuerdo.descripcion,
                    onClick = {onRecuerdoClick(recuerdo.id)},
                    modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp)
                        .wrapContentHeight()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecuerdoListPreview(){
    //RecuerdoList(DataSource.recopilatorios[0].recuerdos, DataSource.recopilatorios[0].name)
}
