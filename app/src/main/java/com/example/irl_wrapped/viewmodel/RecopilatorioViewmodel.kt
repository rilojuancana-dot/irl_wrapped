package com.example.irl_wrapped.viewmodel

import com.example.irl_wrapped.data.repository.EmojiRepository
import com.example.irl_wrapped.data.repository.LugarRepository
import com.example.irl_wrapped.data.repository.PersonaRepository
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.irl_wrapped.data.repository.RecopilatorioRepository
import com.example.irl_wrapped.data.repository.RecuerdoRepository
import com.example.irl_wrapped.data.repository.UsuarioRepository
import com.example.irl_wrapped.data.services.RetrofitImpl
import com.example.irl_wrapped.model.Recopilatorio
import com.example.irl_wrapped.model.Recuerdo
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


sealed interface RecopilatoriosUiState {
    object Loading : RecopilatoriosUiState
    data class Success(
        val recopilatorios: List<Recopilatorio> = emptyList(),
        val recuerdosPorRecopilatorio: Map<Long, List<Recuerdo>> = emptyMap(),
        val recopilatorioSeleccionado: Recopilatorio? = null
    ) : RecopilatoriosUiState
    data class Error(val message: String) : RecopilatoriosUiState
}

class RecopilatoriosViewModel(
    private val recopilatorioRepository: RecopilatorioRepository,
    private val recuerdoRepository: RecuerdoRepository,
    private val usuarioRepository: UsuarioRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<RecopilatoriosUiState>(RecopilatoriosUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val recuerdosCache = mutableMapOf<Long, List<Recuerdo>>()


    fun cargarDatosIniciales(usuarioId: Long) {
        viewModelScope.launch {
            _uiState.value = RecopilatoriosUiState.Loading
            try {
                val recopilatorios = usuarioRepository.getRecopilatorios(1L)

                val recuerdosMap = mutableMapOf<Long, List<Recuerdo>>()
                recopilatorios.forEach { recopilatorio ->
                    val recuerdos = recopilatorioRepository.getRecopilatorioRecuerdos(recopilatorio.id)
                    recuerdosMap[recopilatorio.id] = recuerdos
                    recuerdosCache[recopilatorio.id] = recuerdos
                }

                _uiState.value = RecopilatoriosUiState.Success(
                    recopilatorios = recopilatorios,
                    recuerdosPorRecopilatorio = recuerdosMap
                )
            } catch (e: Exception) {
                Log.e("RecopilatoriosVM", "Error cargando datos: ${e.message}")
                _uiState.value = RecopilatoriosUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }


    fun crearRecopilatorio(nombre: String, usuarioId: Long) {
        viewModelScope.launch {
            try {
                val nuevoRecopilatorio = recopilatorioRepository.crearRecopilatorio(nombre, usuarioId)

                _uiState.update { currentState ->
                    if (currentState is RecopilatoriosUiState.Success) {
                        val nuevosRecopilatorios = currentState.recopilatorios + nuevoRecopilatorio
                        currentState.copy(
                            recopilatorios = nuevosRecopilatorios,
                            recuerdosPorRecopilatorio = currentState.recuerdosPorRecopilatorio +
                                    (nuevoRecopilatorio.id to emptyList())
                        )
                    } else {
                        currentState
                    }
                }
            } catch (e: Exception) {
                _uiState.value = RecopilatoriosUiState.Error("Error al crear: ${e.message}")
            }
        }
    }

    fun actualizarRecopilatorio(id: Int, nuevoNombre: String) {
        viewModelScope.launch {
            try {
                val actualizado = recopilatorioRepository.actualizarRecopilatorio(id, nuevoNombre)

                _uiState.update { currentState ->
                    if (currentState is RecopilatoriosUiState.Success) {
                        val nuevosRecopilatorios = currentState.recopilatorios.map {
                            if (it.id == actualizado.id) actualizado else it
                        }
                        currentState.copy(recopilatorios = nuevosRecopilatorios)
                    } else {
                        currentState
                    }
                }
            } catch (e: Exception) {
                _uiState.value = RecopilatoriosUiState.Error("Error al actualizar: ${e.message}")
            }
        }
    }

    fun eliminarRecopilatorio(id: Long) {
        viewModelScope.launch {
            try {
                val success = recopilatorioRepository.eliminarRecopilatorio(id)
                if (success) {
                    _uiState.update { currentState ->
                        if (currentState is RecopilatoriosUiState.Success) {
                            val nuevosRecopilatorios = currentState.recopilatorios.filter { it.id != id }
                            val nuevosRecuerdosMap = currentState.recuerdosPorRecopilatorio.toMutableMap()
                            nuevosRecuerdosMap.remove(id)
                            recuerdosCache.remove(id)

                            currentState.copy(
                                recopilatorios = nuevosRecopilatorios,
                                recuerdosPorRecopilatorio = nuevosRecuerdosMap,
                                recopilatorioSeleccionado = if (currentState.recopilatorioSeleccionado?.id == id) null
                                else currentState.recopilatorioSeleccionado
                            )
                        } else {
                            currentState
                        }
                    }
                }
            } catch (e: Exception) {
                _uiState.value = RecopilatoriosUiState.Error("Error al eliminar: ${e.message}")
            }
        }
    }

    fun seleccionarRecopilatorio(recopilatorio: Recopilatorio?) {
        _uiState.update { currentState ->
            if (currentState is RecopilatoriosUiState.Success) {
                currentState.copy(recopilatorioSeleccionado = recopilatorio)
            } else {
                currentState
            }
        }
    }


    fun crearRecuerdo(recuerdo: Recuerdo, recopilatorioId: Long) {
        viewModelScope.launch {
            try {
                val nuevoRecuerdoAsync = async{
                    recuerdoRepository.crearRecuerdo(recuerdo, recopilatorioId)
                }
                val nuevoRecuerdo = nuevoRecuerdoAsync.await()
                Log.d("RecopilatoriosViewModel", "========================Nuevo Recuerdo: $nuevoRecuerdo")
                val recuerdosActuales = recuerdosCache[recopilatorioId]?.toMutableList() ?: mutableListOf()
                recuerdosActuales.add(nuevoRecuerdo)
                Log.d("RecopilatoriosViewModel", "========================Recuerdos actuales: ${recuerdosActuales.joinToString { it.name }}")
                recuerdosCache[recopilatorioId] = recuerdosActuales

                _uiState.update { currentState ->
                    if (currentState is RecopilatoriosUiState.Success) {
                        val nuevosRecuerdosMap = currentState.recuerdosPorRecopilatorio.toMutableMap()
                        nuevosRecuerdosMap[recopilatorioId] = recuerdosActuales
                        val recopilatoriosActualizado = currentState.recopilatorios.map { recopilatorio ->
                            if (recopilatorio.id == recopilatorioId) {
                                recopilatorio.copy(recuerdos = recuerdosActuales)
                            } else {
                                recopilatorio
                            }
                        }
                        currentState.copy(
                            recopilatorios = recopilatoriosActualizado,
                            recuerdosPorRecopilatorio = nuevosRecuerdosMap.toMap())
                    } else {
                        currentState
                    }
                }
            } catch (e: Exception) {
                _uiState.value = RecopilatoriosUiState.Error("Error al crear recuerdo: ${e.message}")
            }
        }
    }

    fun actualizarRecuerdo(id: Long, recuerdo: Recuerdo, recopilatorioId: Long) {
        viewModelScope.launch {
            try {
                val actualizado = recuerdoRepository.actualizarRecuerdo(id, recuerdo)

                val recuerdosActuales = recuerdosCache[recopilatorioId] ?: emptyList()
                recuerdosCache[recopilatorioId] = recuerdosActuales.map {
                    if (it.id == actualizado.id) actualizado else it
                }

                _uiState.update { currentState ->
                    if (currentState is RecopilatoriosUiState.Success) {
                        val nuevosRecuerdosMap = currentState.recuerdosPorRecopilatorio.toMutableMap()
                        nuevosRecuerdosMap[recopilatorioId] = recuerdosCache[recopilatorioId] ?: emptyList()

                        currentState.copy(recuerdosPorRecopilatorio = nuevosRecuerdosMap)
                    } else {
                        currentState
                    }
                }
            } catch (e: Exception) {
                _uiState.value = RecopilatoriosUiState.Error("Error al actualizar recuerdo: ${e.message}")
            }
        }
    }

    fun eliminarRecuerdo(id: Long, recopilatorioId: Long) {
        viewModelScope.launch {
            try {
                val success = recuerdoRepository.eliminarRecuerdo(id)
                if (success) {
                    val recuerdosActuales = recuerdosCache[recopilatorioId] ?: emptyList()
                    recuerdosCache[recopilatorioId] = recuerdosActuales.filter { it.id != id }

                    _uiState.update { currentState ->
                        if (currentState is RecopilatoriosUiState.Success) {
                            val nuevosRecuerdosMap = currentState.recuerdosPorRecopilatorio.toMutableMap()
                            nuevosRecuerdosMap[recopilatorioId] = recuerdosCache[recopilatorioId] ?: emptyList()

                            currentState.copy(recuerdosPorRecopilatorio = nuevosRecuerdosMap)
                        } else {
                            currentState
                        }
                    }
                }
            } catch (e: Exception) {
                _uiState.value = RecopilatoriosUiState.Error("Error al eliminar recuerdo: ${e.message}")
            }
        }
    }

    fun recargarRecuerdosDeRecopilatorio(recopilatorioId: Long) {
        viewModelScope.launch {
            try {
                val recuerdos = recopilatorioRepository.getRecopilatorioRecuerdos(recopilatorioId)
                recuerdosCache[recopilatorioId] = recuerdos

                _uiState.update { currentState ->
                    if (currentState is RecopilatoriosUiState.Success) {
                        val nuevosRecuerdosMap = currentState.recuerdosPorRecopilatorio.toMutableMap()
                        nuevosRecuerdosMap[recopilatorioId] = recuerdos

                        currentState.copy(recuerdosPorRecopilatorio = nuevosRecuerdosMap)
                    } else {
                        currentState
                    }
                }
            } catch (e: Exception) {
                _uiState.value = RecopilatoriosUiState.Error("Error al recargar recuerdos: ${e.message}")
            }
        }
    }


    fun getRecuerdosDeRecopilatorio(recopilatorioId: Long): List<Recuerdo> {
        return recuerdosCache[recopilatorioId] ?: emptyList()
    }

    fun getRecopilatorioPorId(id: Long): Recopilatorio? {
        val state = _uiState.value
        return if (state is RecopilatoriosUiState.Success) {
            state.recopilatorios.find { it.id == id }
        } else {
            null
        }
    }

    fun resetError() {
        val currentState = _uiState.value
        if (currentState is RecopilatoriosUiState.Error) {
            _uiState.value = RecopilatoriosUiState.Loading
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                RecopilatoriosViewModel(
                    recopilatorioRepository = RecopilatorioRepository(RetrofitImpl().api),
                    recuerdoRepository = RecuerdoRepository(RetrofitImpl().api),
                    usuarioRepository = UsuarioRepository(RetrofitImpl().api)
                )
            }
        }
    }
}