package com.example.irl_wrapped.ui.view

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.irl_wrapped.viewmodel.CameraUiState

@Composable
fun CameraScreen(cameraUiState: CameraUiState, onNavigateToCameraPage: () -> Unit) {


    when {
        cameraUiState.errorMessage != null-> {
            Text(
                text = cameraUiState.errorMessage!!,
            )
        }
        else -> {
            onNavigateToCameraPage()
        }
    }
}