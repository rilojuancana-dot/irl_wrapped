package com.example.irl_wrapped.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.Surface
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.irl_wrapped.data.repository.RecopilatorioRepository
import com.example.irl_wrapped.data.repository.RecuerdoRepository
import com.example.irl_wrapped.data.repository.UsuarioRepository
import com.example.irl_wrapped.data.services.RetrofitImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors

data class CameraUiState(
    val isCapturing: Boolean = false,
    val capturedImage: Bitmap? = null,
    val errorMessage: String? = null
){}

class CameraViewModel : ViewModel() {
    
    private val _uiState = MutableStateFlow(CameraUiState())
    val uiState: StateFlow<CameraUiState> = _uiState.asStateFlow()

    private val _surfaceRequest = MutableStateFlow<SurfaceRequest?>(null)

    val surfaceRequest: StateFlow<SurfaceRequest?> = _surfaceRequest.asStateFlow()
    
    private var imageCapture: ImageCapture? = null
    private var camera: Camera? = null
    private lateinit var context: Context
    
    fun initializeCamera(context: Context, lifecycleOwner: LifecycleOwner) {
        this.context = context.applicationContext

        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also { preview ->
                    preview.setSurfaceProvider { request ->
                        _surfaceRequest.value = request
                        request.addRequestCancellationListener(
                            Executors.newSingleThreadExecutor()
                        ) {
                            _surfaceRequest.value = null
                        }
                    }
                }

            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .setTargetRotation(Surface.ROTATION_0)
                .build()

            try {
                cameraProvider?.unbindAll()
                cameraProvider?.bindToLifecycle(
                    lifecycleOwner,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview,
                    imageCapture!!
                )
            } catch (e: Exception) {
                Log.e("CameraViewModel", "Error al iniciar cámara", e)
                _uiState.update { it.copy(errorMessage = "Error: ${e.message}") }
            }

        }, ContextCompat.getMainExecutor(context))
    }
    
    fun capturePhoto() {
        val imageCapture = imageCapture
        if (imageCapture == null) {
            _uiState.value = uiState.value.copy(
                errorMessage = "La cámara no está inicializada"
            )
            return
        }
        
        _uiState.value = uiState.value.copy(isCapturing = true, errorMessage = null)
        imageCapture.takePicture(
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {

                    viewModelScope.launch {
                        val bitmap = imageProxyToBitmap(image)
                        
                        _uiState.value = uiState.value.copy(
                            isCapturing = false,
                            capturedImage = bitmap
                        )
                        
                    }
                    image.close()

                }
                
                override fun onError(exception: ImageCaptureException) {
                    _uiState.value = uiState.value.copy(
                        isCapturing = false,
                        errorMessage = "Error al capturar: ${exception.message}"
                    )
                }
            }
        )
    }
    
    fun clearCapturedImage() {
        _uiState.value = uiState.value.copy(capturedImage = null)
    }
    
    private fun imageProxyToBitmap(image: ImageProxy): Bitmap {
        val buffer = image.planes[0].buffer
        val bytes = ByteArray(buffer.remaining())
        buffer.get(bytes)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }
    
    override fun onCleared() {
        super.onCleared()
        camera = null
        imageCapture = null
    }
}