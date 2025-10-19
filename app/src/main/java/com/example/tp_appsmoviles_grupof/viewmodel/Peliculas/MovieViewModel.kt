package com.example.tp_appsmoviles_grupof.viewmodel.Peliculas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import Movie
class MovieViewModel : ViewModel() {
    private val api = MovieApiService.create()

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    fun loadMovies(apiKey: String) {
        viewModelScope.launch {
            try {
                val response = api.getPopularMovies(apiKey)
                _movies.value = response.results
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}