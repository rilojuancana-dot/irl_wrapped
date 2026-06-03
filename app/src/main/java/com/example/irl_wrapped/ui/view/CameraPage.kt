package com.example.irl_wrapped.ui.view

import android.graphics.Bitmap
import android.view.Surface
import androidx.camera.compose.CameraXViewfinder
import androidx.camera.core.SurfaceRequest
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.irl_wrapped.viewmodel.CameraUiState
import kotlinx.coroutines.flow.StateFlow

@Composable
fun CameraPage(cameraUiState: CameraUiState, surfaceRequestState: StateFlow<SurfaceRequest?>, onTakePhoto: () -> Unit, onSavePhoto: () -> Unit, onClear: () -> Unit, onNavigateBack: () -> Unit){
    val surfaceRequest by surfaceRequestState.collectAsState()

    val currentSurfaceRequest = surfaceRequest
    Box(modifier = Modifier.fillMaxSize()) {
        if (currentSurfaceRequest != null) {
            CameraXViewfinder(
                surfaceRequest = currentSurfaceRequest,
                modifier = Modifier.fillMaxSize()
            )
        }else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {onNavigateBack()}) {
                    Text("Cancelar")
                }

                Button(
                    onClick = { onTakePhoto() },
                    enabled = !cameraUiState.isCapturing
                ) {
                    if (cameraUiState.isCapturing) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp))

                    } else {
                        Text("Tomar Foto")
                    }
                }
            }

            if (cameraUiState.errorMessage != null) {
                Text(
                    text = cameraUiState.errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        cameraUiState.capturedImage?.let { bitmap ->
            CapturedImageOverlay(
                bitmap = bitmap,
                onDismiss = onClear ,
                onSavePhoto = onSavePhoto
            )
        }
    }
}

@Composable
fun CapturedImageOverlay(bitmap: Bitmap, onSavePhoto: () -> Unit, onDismiss: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentScale = ContentScale.Fit,
                contentDescription = "Foto capturada",
                modifier = Modifier
                    .size(300.dp)
                    .padding(16.dp)
                    .weight(1f)
            )

            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(onClick = onDismiss) {
                    Text("Descartar")
                }
                Button(onClick = { onSavePhoto() }) {
                    Text("Guardar")
                }
            }
        }
    }
}