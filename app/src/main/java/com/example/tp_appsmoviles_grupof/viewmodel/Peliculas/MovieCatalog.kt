package com.example.tp_appsmoviles_grupof.viewmodel.Peliculas

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tp_appsmoviles_grupof.viewmodel.Peliculas.MovieAdapter
import com.example.tp_appsmoviles_grupof.viewmodel.Peliculas.MovieViewModel
import com.example.tp_appsmoviles_grupof.databinding.ActivityCatalogBinding
import Movie
class MovieCatalog : AppCompatActivity() {

    private lateinit var binding: ActivityCatalogBinding
    private val viewModel: MovieViewModel by viewModels()
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatalogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = MovieAdapter(   movies = emptyList(),onBuyClick = { movie ->
            Toast.makeText(this, "Compraste: ${movie.title}", Toast.LENGTH_SHORT).show()
            // Acá podrías guardar en Room si querés simular compra
        })

        binding.recyclerMovies.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerMovies.adapter = adapter

        // Observa los cambios del ViewModel
        viewModel.movies.observe(this) { movies ->
            adapter.setMovies(movies)
        }

        viewModel.loadMovies("a900d45013d2d7ea128b1e1d2bb0dc94")
    }
}
