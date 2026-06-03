package com.example.irl_wrapped.ui.view.components

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
@Composable
fun InitStats(numeroRecuerdos : Int){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxHeight().fillMaxWidth()
    ) {
        CustomCard("Has guardado $numeroRecuerdos recuerdos nuevos")
    }
}

@Composable
fun LugarTotal(lugarMas : String){
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight().fillMaxWidth()
    ) {
        CustomCard("Tu lugar más visitado ha sido $lugarMas, aquí tienes algunos ejemplos:")
    }
}

@Composable
fun Recap(imagenes : List<String> ){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxHeight().fillMaxWidth()
    ) {
        items(imagenes){
            AsyncImage(
                model = it,
                contentDescription = "Imagen del recuerdo",
                modifier = Modifier.padding(8.dp).height(150.dp).fillMaxWidth()
            )
        }
    }
}

@Composable
fun EmojiTotal(emoji: String){
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight().fillMaxWidth()
    ) {
        CustomCard("Tu emoji más utilizado es $emoji")
    }
}

@Composable
fun TemaTotal(tema: String){
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight().fillMaxWidth()
    ) {
       CustomCard("Tu tema más utilizado es $tema")
    }
}



@Composable
fun Stats(map: Map<String, Int>) {
    val sortedKeys = map.keys.sortedByDescending { map[it] }

    BarChart(
        data = map.values.toList().sortedDescending(),
        labels = sortedKeys,
    )

}

@Composable
fun FinStats(lugarMas: String, emoji: String, persona: String){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight().fillMaxWidth().padding(8.dp)
    ) {
        Text("Podríamos resumir este recopilatorio con:", textAlign = TextAlign.Center, style = MaterialTheme.typography.headlineLarge)
        Card(modifier = Modifier.weight(1f).fillMaxWidth().padding(8.dp)){
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text("Mi lugar favorito ha sido: $lugarMas", textAlign = TextAlign.Center)
            }
        }
        Card(modifier = Modifier.weight(1f).fillMaxWidth().padding(8.dp)){
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text(
                    "La persona con la que más he disfrutado ha sido: $persona",
                    textAlign = TextAlign.Center
                )
            }
        }
        Card (modifier = Modifier.weight(1f).fillMaxWidth().padding(8.dp)){
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text("Yo soy este: $emoji", textAlign = TextAlign.Center)
            }
        }
    }

}

@Composable
fun BarChart(
    data: List<Int>,
    labels: List<String> = emptyList(),
    modifier: Modifier = Modifier,
    barColor: Color = Color.Blue
) {
    val max = data.maxOrNull() ?: 1


    var actualData = data.toList()
    var actualLabels = labels.toList()
    if (data.size > 4) {
        actualLabels = labels.subList(0, 4)
        actualData = data.subList(0, 4)
    }

    Column(modifier = modifier) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {

            val barWidth = size.width / actualData.size
            val spacing = barWidth * 0.2f
            val actualBarWidth = barWidth - spacing

            actualData.forEachIndexed { index, value ->
                val barHeight = (value.toFloat() / max) * size.height

                val left = index * barWidth + spacing / 2

                val top = size.height - barHeight

                drawRect(
                    color = barColor,
                    topLeft = Offset(left, top),
                    size = Size(
                        width = actualBarWidth,
                        height = barHeight
                    )
                )
            }
        }

        if (actualLabels.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                actualLabels.forEach { label ->
                    Text(
                        text = label,
                        fontSize = 12.sp,
                        modifier = Modifier.weight(1f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun CustomCard(texto :String){
    Card(
        modifier = Modifier.padding(8.dp).fillMaxWidth().heightIn(100.dp, 300.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ){
            Column(Modifier.matchParentSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text(texto, textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
fun StatsError(onBack : () -> Unit){
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text("No hay datos para generar estadísticas")
        Button(onClick = {onBack()}) {Text( "Volver") }
    }
}