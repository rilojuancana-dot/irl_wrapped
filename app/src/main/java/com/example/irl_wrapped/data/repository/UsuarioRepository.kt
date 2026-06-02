package com.example.irl_wrapped.data.repository

import com.example.irl_wrapped.data.services.ApiService
import com.example.irl_wrapped.model.Recopilatorio

class UsuarioRepository(
    private val apiService: ApiService
) {
    suspend fun getRecopilatorios(id: Long): List<Recopilatorio>{
        return apiService.getRecopilatorios(id)
    }
}