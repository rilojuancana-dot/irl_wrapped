package com.example.irl_wrapped.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.irl_wrapped.model.data.DataSource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(){
    val cardHeight = LocalConfiguration.current.screenHeightDp.dp / 5
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
        LazyColumn(contentPadding = contentPadding, modifier = Modifier.fillMaxHeight(1f)){
            items(DataSource.recopilatorios) { recuerdo ->
                Card(modifier = Modifier.fillMaxWidth(1f)
                    .padding(8.dp)
                    .height(cardHeight)

                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                            .padding(all = 8.dp)
                            .fillMaxHeight()
                    ) {
                        Text(text = recuerdo.nombre, style = MaterialTheme.typography.titleLarge)
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