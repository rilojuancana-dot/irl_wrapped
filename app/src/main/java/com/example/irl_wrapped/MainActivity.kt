package com.example.irl_wrapped

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.example.irl_wrapped.model.data.DataSource
import com.example.irl_wrapped.ui.theme.Irl_wrappedTheme
import com.example.irl_wrapped.ui.view.MainPage
import com.example.irl_wrapped.ui.view.RecuerdoPage
import com.example.irl_wrapped.ui.view.components.AppNavigation
import com.example.irl_wrapped.ui.view.components.PermissionBlockedScreen
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : ComponentActivity() {

    private val _permissionGranted = MutableStateFlow<Boolean?>(null)

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        _permissionGranted.value = granted
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (hasCameraPermission()) {
            _permissionGranted.value = true
        } else {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }

        setContent {
            Irl_wrappedTheme {
                val permissionGranted by _permissionGranted.collectAsState()

                when (permissionGranted) {
                    null -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    false -> {
                        PermissionBlockedScreen(
                            onExit = { finish() }
                        )
                    }
                    true -> {
                        AppNavigation()
                    }
                }
            }
        }
    }

    private fun hasCameraPermission() =
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Irl_wrappedTheme {
        Greeting("Android")
    }
}