package com.example.irl_wrapped.data.repository

import com.example.irl_wrapped.data.services.ApiService
import com.example.irl_wrapped.model.Emoji
import com.example.irl_wrapped.model.Lugar
import com.example.irl_wrapped.model.Persona
import com.example.irl_wrapped.model.Recopilatorio
import com.example.irl_wrapped.model.Recuerdo
import com.example.irl_wrapped.model.Tema

class RecopilatorioRepository(
    private val apiService: ApiService
) {
    
    suspend fun crearRecopilatorio(nombre: String, usuarioId: Long): Recopilatorio {
        return apiService.crearRecopilatorio(nombre, usuarioId)
    }
    
    suspend fun actualizarRecopilatorio(id: Int, nuevoNombre: String): Recopilatorio {
        return apiService.actualizarRecopilatorio(id, nuevoNombre)
    }
    
    suspend fun eliminarRecopilatorio(id: Long): Boolean {
        return apiService.eliminarRecopilatorio(id)
    }
    
    suspend fun getRecopilatorioRecuerdos(id: Long): List<Recuerdo> {
        return apiService.getRecopilatorioRecuerdos(id)
    }
    
    suspend fun getTemaFrecuencia(id: Long): Map<String, Int> {
        return apiService.getTemaFrecuencia(id)
    }
    
    suspend fun getEmojiFrecuencia(id: Long): Map<String, Int> {
        return apiService.getEmojiFrecuencia(id)
    }
    
    suspend fun getPersonaFrecuencia(id: Long): Map<String, Int> {
        return apiService.getPersonaFrecuencia(id)
    }
    
    suspend fun getLugarFrecuencia(id: Long): Map<String, Int> {
        return apiService.getLugarFrecuencia(id)
    }

}