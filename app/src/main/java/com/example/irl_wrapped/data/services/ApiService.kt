package com.example.irl_wrapped.data.services
import com.example.irl_wrapped.model.Emoji
import com.example.irl_wrapped.model.Lugar
import com.example.irl_wrapped.model.Persona
import com.example.irl_wrapped.model.Recopilatorio
import com.example.irl_wrapped.model.Recuerdo
import com.example.irl_wrapped.model.Tema
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("api/emojis/unicode/{unicode}")
    suspend fun getEmojiByUnicode(@Path("unicode") unicode:String): List<Emoji>

    @DELETE("api/emojis/{id}")
    suspend fun eliminarEmoji(@Path("id") id: Int) : Boolean

    @DELETE("api/lugares/{id}")
    suspend fun eliminarLugar(@Path("id") id: Int): Boolean

    @GET("api/lugares/nombre/{nombre}")
    suspend fun getLugarByNombre(@Path("nombre") nombre:String): List<Lugar>

    @DELETE("api/personas/{id}")
    suspend fun eliminarPersona(@Path("id") id: Int): Boolean

    @GET("api/personas/nombre/{nombre}")
    suspend fun getPersonaByNombre(@Path("nombre") nombre:String): List<Persona>

    @GET("api/temas/nombre/{nombre}")
    suspend fun getTemaByNombre(@Path("nombre") nombre:String): List<Tema>

    @DELETE("api/temas/{id}")
    suspend fun eliminarTema(@Path("id") id: Int): Boolean

    @POST("add")
    suspend fun crearRecopilatorio(
        @Query("nombre") nombre: String,
        @Query("usuarioId") usuarioId: Long
    ): Recopilatorio

    @PUT("api/recopilatorios/{id}")
    suspend fun actualizarRecopilatorio(@Path("id") id: Int, @Query("nuevoNombre") nuevoNombre: String): Recopilatorio

    @DELETE("api/recopilatorios/{id}")
    suspend fun eliminarRecopilatorio(@Path("id") id: Long): Boolean

    @GET("api/recopilatorios/{id}/recuerdos")
    suspend fun getRecopilatorioRecuerdos(@Path("id") id: Long): List<Recuerdo>

    @GET("api/recopilatorios/{id}/estadisticas/temas")
    suspend fun getTemaFrecuencia(@Path("id") id: Long): Map<String, Int>

    @GET("api/recopilatorios/{id}/estadisticas/emojis")
    suspend fun getEmojiFrecuencia(@Path("id") id: Long): Map<String, Int>

    @GET("api/recopilatorios/{id}/estadisticas/personas")
    suspend fun getPersonaFrecuencia(@Path("id") id: Long): Map<String, Int>

    @GET("api/recopilatorios/{id}/estadisticas/lugares")
    suspend fun getLugarFrecuencia(@Path("id") id: Long): Map<String, Int>

    @POST("api/recuerdos")
    suspend fun crearRecuerdo(
        @Body recuerdo: Recuerdo,
        @Query("recopilatorioId") recopilatorioId: Long
    ): Recuerdo

    @PUT("api/recuerdos/{id}")
    suspend fun actualizarRecuerdo(
        @Path("id") id: Long,
        @Body recuerdo: Recuerdo
    ): Recuerdo

    @DELETE("api/recuerdos/{id}")
    suspend fun eliminarRecuerdo(@Path("id") id: Long): Boolean

    @GET("api/recuerdos/{id}/tema")
    suspend fun getRecuerdoTema(@Path("id") id: Long): Tema

    @GET("api/recuerdos/{id}/emoji")
    suspend fun getRecuerdoEmoji(@Path("id") id: Long): Emoji

    @GET("api/recuerdos/{id}/personas")
    suspend fun getRecuerdoPersonas(@Path("id") id: Long): List<Persona>

    @GET("api/recuerdos/{id}/lugar")
    suspend fun getRecuerdoLugar(@Path("id") id: Long): Lugar

    @GET("api/user/{id}/recopilatorios")
    suspend fun getRecopilatorios(@Path("id") id:Long): List<Recopilatorio>
}