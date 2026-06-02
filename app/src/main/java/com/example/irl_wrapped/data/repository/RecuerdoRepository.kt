package com.example.irl_wrapped.data.repository

import com.example.irl_wrapped.data.services.ApiService
import com.example.irl_wrapped.model.Emoji
import com.example.irl_wrapped.model.Lugar
import com.example.irl_wrapped.model.Persona
import com.example.irl_wrapped.model.Recuerdo
import com.example.irl_wrapped.model.Tema

class RecuerdoRepository(
    private val apiService: ApiService
) {
    suspend fun crearRecuerdo(recuerdo: Recuerdo, recopilatorioId: Long): Recuerdo {
        return apiService.crearRecuerdo(recuerdo, recopilatorioId)
    }

    suspend fun actualizarRecuerdo(id: Long, recuerdo: Recuerdo): Recuerdo {
        return apiService.actualizarRecuerdo(id, recuerdo)
    }

    suspend fun eliminarRecuerdo(id: Long): Boolean {
        return apiService.eliminarRecuerdo(id)
    }
    suspend fun getRecuerdoTema(id: Long): Tema {
        return apiService.getRecuerdoTema(id)
    }

    suspend fun getRecuerdoEmoji(id: Long): Emoji {
        return apiService.getRecuerdoEmoji(id)
    }

    suspend fun getRecuerdoPersonas(id: Long): List<Persona> {
        return apiService.getRecuerdoPersonas(id)
    }

    suspend fun getRecuerdoLugar(id: Long): Lugar {
        return apiService.getRecuerdoLugar(id)
    }
}