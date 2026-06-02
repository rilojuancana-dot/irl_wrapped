package com.example.irl_wrapped.data.repository

import com.example.irl_wrapped.data.services.ApiService
import com.example.irl_wrapped.model.Lugar

class LugarRepository (
    private val apiService: ApiService
) {
    suspend fun eliminarLugar(id: Int): Boolean {
        return apiService.eliminarLugar(id)
    }

    suspend fun getLugarByNombre(nombre: String): List<Lugar> {
        return apiService.getLugarByNombre(nombre)
    }
}