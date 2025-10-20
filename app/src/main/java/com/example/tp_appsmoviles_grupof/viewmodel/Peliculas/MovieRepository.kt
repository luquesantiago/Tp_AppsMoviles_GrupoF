
package com.example.tp_appsmoviles_grupof.data

import com.example.tp_appsmoviles_grupof.database.local.RoomApp
import com.example.tp_appsmoviles_grupof.database.local.entities.comprasEntity
import com.example.tp_appsmoviles_grupof.data.remote.TmdbApi
import com.example.tp_appsmoviles_grupof.data.remote.VideoDto

class MovieRepository(private val apiKey: String) {
    private val comprasDao = RoomApp.database.comprasDao()
    private val api = TmdbApi.service

    // Llamala cuando tocan Comprar
    suspend fun buyMovieSnapshot(
        userId: Long,
        movieId: Int,
        title: String,
        overview: String?
    ) {
        val trailerKey = fetchTrailerKey(movieId)
        val compra = comprasEntity(
            userId = userId,
            movieId = movieId,
            title = title,
            overview = overview,
            trailerKey = trailerKey
        )
        comprasDao.upsert(compra)
    }

    // Podés usarla para abrir trailer desde Mis compras
    suspend fun getTrailerKey(userId: Long, movieId: Int): String? =
        comprasDao.getTrailerKey(userId, movieId)

    private suspend fun fetchTrailerKey(movieId: Int): String? {
        fun pickBest(list: List<VideoDto>): String? {
            list.firstOrNull { it.site.equals("YouTube", true) && it.type.equals("Trailer", true) && it.official == true }?.let { return it.key }
            list.firstOrNull { it.site.equals("YouTube", true) && it.type.equals("Trailer", true) }?.let { return it.key }
            return list.firstOrNull { it.site.equals("YouTube", true) }?.key
        }
        val es = api.getVideos(movieId, apiKey, "es-ES")
        pickBest(es.results)?.let { return it }
        val en = api.getVideos(movieId, apiKey, "en-US")
        return pickBest(en.results)
    }
}
