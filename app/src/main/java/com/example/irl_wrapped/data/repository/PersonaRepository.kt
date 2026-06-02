package com.example.irl_wrapped.data.repository

import com.example.irl_wrapped.data.services.ApiService
import com.example.irl_wrapped.model.Persona

class PersonaRepository(
    private val apiService: ApiService
) {
    suspend fun eliminarPersona(id: Int): Boolean {
        return apiService.eliminarPersona(id)
    }

    suspend fun getPersonaByNombre(nombre: String): List<Persona> {
        return apiService.getPersonaByNombre(nombre)
    }
}