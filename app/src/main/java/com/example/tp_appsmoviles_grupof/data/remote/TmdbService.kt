package com.example.tp_appsmoviles_grupof.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

data class VideoResponse(
    val id: Int,
    val results: List<VideoDto>
)

data class VideoDto(
    val id: String,
    val key: String,
    val site: String?,
    val type: String?,
    val official: Boolean?
)

interface TmdbService {
    @GET("3/movie/{movie_id}/videos")
    suspend fun getVideos(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "es-ES"
    ): VideoResponse
}
