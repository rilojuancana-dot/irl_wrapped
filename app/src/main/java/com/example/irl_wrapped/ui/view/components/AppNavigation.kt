package com.example.irl_wrapped.ui.view.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.irl_wrapped.model.Emoji
import com.example.irl_wrapped.model.Lugar
import com.example.irl_wrapped.model.Recuerdo
import com.example.irl_wrapped.model.Tema
import com.example.irl_wrapped.ui.view.CameraPage
import com.example.irl_wrapped.ui.view.CameraScreen
import com.example.irl_wrapped.ui.view.MainPage
import com.example.irl_wrapped.ui.view.RecopilatorioPage
import com.example.irl_wrapped.ui.view.RecuerdoList
import com.example.irl_wrapped.ui.view.RecuerdoPage
import com.example.irl_wrapped.viewmodel.CameraUiState
import com.example.irl_wrapped.viewmodel.CameraViewModel
import com.example.irl_wrapped.viewmodel.RecopilatoriosViewModel
import androidx.core.graphics.createBitmap
import com.example.irl_wrapped.model.Recopilatorio
import com.example.irl_wrapped.ui.view.EstadisticasPage

@Composable
fun AppNavigation(modifier: Modifier = Modifier){
    val context = LocalContext.current
    val navController = rememberNavController()
    val lifecycleOwner = LocalLifecycleOwner.current

    val recopilatoriosViewModeliewModel : RecopilatoriosViewModel = viewModel(factory = RecopilatoriosViewModel.Factory)
    val recopilatoriosUiState by recopilatoriosViewModeliewModel.uiState.collectAsState()

    val cameraViewModel : CameraViewModel = viewModel()
    val cameraUiState by cameraViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        recopilatoriosViewModeliewModel.cargarDatosIniciales(1)
        cameraViewModel.initializeCamera(context, lifecycleOwner)
    }

    Scaffold(modifier = modifier) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "main_page"
        ){
            composable(route = "main_page"){
                MainPage(
                    recopilatoriosUiState,
                    Modifier.padding(paddingValues),
                    onNavigateToRecopilatorio = { id ->
                        navController.navigate("recopilatorio_page/$id")
                    }
                )
            }

            composable(
                route = "recopilatorio_page/{id}",
                arguments = listOf(navArgument("id") { type = NavType.LongType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getLong("id") ?: 0L
                val recopilatorio = recopilatoriosViewModeliewModel.getRecopilatorioPorId(id)

                if (recopilatorio != null) {
                    RecopilatorioPage(
                        recopilatorio = recopilatorio,
                        onNavigateToRecuerdoList = { navController.navigate("recuerdo_list/$id") },
                        onNavigateToDeleteList = { navController.navigate("recuerdo_list/$id/eliminar") },
                        onNavigateToCameraScreen = {navController.navigate("camera_screen/${id}")},
                        onNavigateToStats = {navController.navigate("stats/${id}")}
                    )
                } else {
                    Text("Error: Recopilatorio no encontrado")
                }
            }

            composable(
                route = "recuerdo_list/{recopilatorioId}",
                arguments = listOf(navArgument("recopilatorioId") { type = NavType.LongType })
            ) { backStackEntry ->
                val recopilatorioId = backStackEntry.arguments?.getLong("recopilatorioId") ?: 0L
                val recopilatorio = recopilatoriosViewModeliewModel.getRecopilatorioPorId(recopilatorioId)

                if (recopilatorio != null) {
                    RecuerdoList(
                        recuerdos = recopilatorio.recuerdos,
                        recopilatorioNombre = recopilatorio.name,
                        onRecuerdoClick = { recuerdoId ->
                            navController.navigate("recuerdo_page/$recopilatorioId/$recuerdoId")
                        },
                        onEliminar = {recuerdoId ->
                          recopilatoriosViewModeliewModel.eliminarRecuerdo(recuerdoId, recopilatorioId)
                        },
                        onBack = {navController.popBackStack()}
                    )
                }
            }
            composable(
                route = "recuerdo_list/{recopilatorioId}/eliminar",
                arguments = listOf(navArgument("recopilatorioId") { type = NavType.LongType })
            ) { backStackEntry ->
                val recopilatorioId = backStackEntry.arguments?.getLong("recopilatorioId") ?: 0L
                val recopilatorio = recopilatoriosViewModeliewModel.getRecopilatorioPorId(recopilatorioId)

                if (recopilatorio != null) {
                    RecuerdoList(
                        recuerdos = recopilatorio.recuerdos,
                        recopilatorioNombre = recopilatorio.name,
                        onRecuerdoClick = { recuerdoId ->
                            navController.navigate("recuerdo_page/$recopilatorioId/$recuerdoId")
                        },
                        onEliminar = {recuerdoId ->
                            recopilatoriosViewModeliewModel.eliminarRecuerdo(recuerdoId, recopilatorioId)
                        },
                        modoEliminar = true,
                        onBack = {navController.navigate("main_page")}

                    )
                }
            }

            composable(
                route = "recuerdo_page/{recopilatorioId}/{recuerdoId}",
                arguments = listOf(
                    navArgument("recopilatorioId") { type = NavType.LongType },
                    navArgument("recuerdoId") { type = NavType.LongType }
                )
            ) { backStackEntry ->
                val recopilatorioId = backStackEntry.arguments?.getLong("recopilatorioId") ?: 0L
                val recuerdoId = backStackEntry.arguments?.getLong("recuerdoId")

                val recuerdo = if (recuerdoId != 0L) {
                    recopilatoriosViewModeliewModel.getRecuerdosDeRecopilatorio(recopilatorioId).find { it.id == recuerdoId }
                } else {
                    Recuerdo(
                        id = 0,
                        name = "",
                        descripcion = "",
                        lugar = Lugar(""),
                        emoji = Emoji(""),
                        tema = Tema(""),
                        personas = emptyList(),
                        imageUrl = null
                    )
                }

                RecuerdoPage(
                    recuerdo = recuerdo,
                    onSave = { recuerdoActualizado : Recuerdo ->
                        if (recuerdoId == 0L) {
                            recopilatoriosViewModeliewModel.crearRecuerdo(recuerdoActualizado, recopilatorioId)
                            cameraViewModel.clearCapturedImage()
                        } else {
                            recopilatoriosViewModeliewModel.actualizarRecuerdo(recuerdoActualizado.id,recuerdoActualizado, recopilatorioId)
                            cameraViewModel.clearCapturedImage()
                        }
                    },
                    onNavigateBack = { navController.navigate("main_page") },
                    imagen = cameraViewModel.uiState.value.capturedImage ?: createBitmap(1, 1)
                )

            }

            composable(
                route = "camera_screen/{id}",
                arguments = listOf(navArgument("id") { type = NavType.LongType })
            ){ backStackEntry ->

                CameraScreen(cameraUiState, onNavigateToCameraPage = {navController.navigate("camera/${backStackEntry.arguments?.getLong("id")?:0L}")} )
            }

            composable (
                route = "camera/{id}",
                arguments = listOf(navArgument("id") { type = NavType.LongType })

            ){ backStackEntry ->

                CameraPage(
                    cameraUiState,
                    surfaceRequestState = cameraViewModel.surfaceRequest,
                    onTakePhoto = { cameraViewModel.capturePhoto() },
                    onClear = {cameraViewModel.clearCapturedImage()},
                    onSavePhoto = { navController.navigate("recuerdo_page/${backStackEntry.arguments?.getLong("id")?:0L}/${0}")},
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(
                route = "stats/{id}",
                arguments = listOf(navArgument("id"){type = NavType.LongType})
            ) { backStackEntry ->
                val recopilatorioId = backStackEntry.arguments?.getLong("id") ?: 0L
                val recopilatorio = recopilatoriosViewModeliewModel.getRecopilatorioPorId(recopilatorioId)?: Recopilatorio(
                    0,
                    "",
                    emptyList()
                )
                recopilatoriosViewModeliewModel.getRecopilatorioTemaFrecuencia(recopilatorioId)
                recopilatoriosViewModeliewModel.getRecopilatorioLugarFrecuencia(recopilatorioId)
                recopilatoriosViewModeliewModel.getRecopilatorioEmojiFrecuencia(recopilatorioId)
                recopilatoriosViewModeliewModel.getRecopilatorioPersonaFrecuencia(recopilatorioId)
                EstadisticasPage(
                    recopilatorio,
                    recopilatoriosViewModeliewModel.temaFrecuencia.value?:emptyMap(),
                    recopilatoriosViewModeliewModel.lugarFrecuencia.value?:emptyMap(),
                    recopilatoriosViewModeliewModel.emojiFrecuencia.value?:emptyMap(),
                    recopilatoriosViewModeliewModel.personaFrecuencia.value?:emptyMap(),
                    onBack = { navController.popBackStack() }

                )
            }
        }
    }
}