package com.example.irl_wrapped.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.irl_wrapped.model.data.DataSource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Welcome", style = MaterialTheme.typography.headlineLarge)
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                modifier = Modifier.padding(16.dp),
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "FAB add button", modifier = Modifier)
            }
        }
    ){contentPadding->
        LazyColumn(contentPadding = contentPadding){
            items(DataSource.recuerdos) { recuerdo ->
                Card(modifier = Modifier.fillMaxWidth()
                    .padding(8.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                            .padding(all = 8.dp)
                    ) {
                        Text(text = recuerdo.nombre, style = MaterialTheme.typography.bodyLarge)
                        Text(text = recuerdo.descripcion, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun MainPagePreview(){
    MainPage()
}