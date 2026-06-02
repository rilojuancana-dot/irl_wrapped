package com.example.irl_wrapped.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.irl_wrapped.model.data.DataSource
import com.example.irl_wrapped.viewmodel.RecopilatoriosUiState
import com.example.irl_wrapped.viewmodel.RecopilatoriosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(uiState: RecopilatoriosUiState, modifier: Modifier = Modifier, onNavigateToRecopilatorio: (Long) -> Unit = {}, ){



    val cardHeight = LocalConfiguration.current.screenHeightDp.dp / 5

    when(val state = uiState) {
        is RecopilatoriosUiState.Success -> {
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
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "FAB add button",
                            modifier = Modifier
                        )
                    }
                }
            ) { contentPadding ->
                LazyColumn(contentPadding = contentPadding, modifier = Modifier.fillMaxHeight(1f)) {
                    items(state.recopilatorios) { recuerdo ->
                        Card(
                            modifier = Modifier.fillMaxWidth(1f)
                                .padding(8.dp)
                                .height(cardHeight)
                                .clickable(onClick = {
                                    onNavigateToRecopilatorio(recuerdo.id)
                                })
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                                    .padding(all = 8.dp)
                                    .fillMaxHeight()
                            ) {
                                Text(
                                    text = recuerdo.name,
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                        }
                    }
                }
            }
        }
        is RecopilatoriosUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is RecopilatoriosUiState.Error -> {
            Text("Error: ${state.message}")
        }
    }
}

@Composable
@Preview(showBackground = true)
fun MainPagePreview(){
}