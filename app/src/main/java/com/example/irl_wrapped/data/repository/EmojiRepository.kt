package com.example.irl_wrapped.data.repository

import com.example.irl_wrapped.data.services.ApiService
import com.example.irl_wrapped.model.Emoji

class EmojiRepository(
    private val apiService: ApiService
) {
    suspend fun getEmojiByUnicode(unicode: String): List<Emoji> {
        return apiService.getEmojiByUnicode(unicode)
    }

    suspend fun eliminarEmoji(id: Int): Boolean {
        return apiService.eliminarEmoji(id)
    }
}