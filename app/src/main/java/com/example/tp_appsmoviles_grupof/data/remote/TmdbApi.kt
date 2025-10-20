package com.example.tp_appsmoviles_grupof.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TmdbApi {
    val service: TmdbService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/") // base de la API de TMDB
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TmdbService::class.java)
    }
}
