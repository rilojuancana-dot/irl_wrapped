package com.example.irl_wrapped.data.repository

import com.example.irl_wrapped.data.services.ApiService
import com.example.irl_wrapped.model.Tema

class TemaRepository(
    private val apiService: ApiService
) {
    suspend fun eliminarTema(id: Int): Boolean {
        return apiService.eliminarTema(id)
    }

    suspend fun getTemaByNombre(nombre: String): List<Tema> {
        return apiService.getTemaByNombre(nombre)
    }
}