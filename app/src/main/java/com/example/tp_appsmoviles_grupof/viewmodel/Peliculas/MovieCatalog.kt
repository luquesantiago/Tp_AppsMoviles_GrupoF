package com.example.tp_appsmoviles_grupof.viewmodel.Peliculas

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tp_appsmoviles_grupof.R
import com.example.tp_appsmoviles_grupof.data.MovieRepository
import com.example.tp_appsmoviles_grupof.database.local.RoomApp
import com.example.tp_appsmoviles_grupof.databinding.ActivityCatalogBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieCatalog : AppCompatActivity() {

    private lateinit var binding: ActivityCatalogBinding
    private val viewModel: MovieViewModel by viewModels()
    private lateinit var adapter: MovieAdapter

    private lateinit var repo: MovieRepository
    private var currentUserId: Long = 0L
    private var canBuy = false
    private val API_KEY = "a900d45013d2d7ea128b1e1d2bb0dc94"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatalogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        RoomApp.init(this)
        repo = MovieRepository(API_KEY)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Catálogo de películas"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val passedUserId = intent.getLongExtra("userId", 0L)
        val nombreUsuario = intent.getStringExtra("nombreIniciado") ?: ""

        if (passedUserId != 0L) {
            currentUserId = passedUserId
            canBuy = true
        } else {
            lifecycleScope.launch(Dispatchers.IO) {
                currentUserId = RoomApp.database.userDao()
                    .buscarPorNombre(nombreUsuario)?.idUser?.toLong() ?: 0L
                withContext(Dispatchers.Main) {
                    canBuy = currentUserId != 0L
                }
            }
        }


        adapter = MovieAdapter(
            movies = emptyList(),
            onBuyClick = { movie ->
                if (!canBuy || currentUserId == 0L) {
                    Toast.makeText(this, "Cargando usuario… intentá de nuevo", Toast.LENGTH_SHORT).show()
                    return@MovieAdapter
                }

                lifecycleScope.launch(Dispatchers.IO) {
                    val ok = repo.buyMovieSnapshot(
                        userId = currentUserId,
                        movieId = movie.id,
                        title = movie.title,
                        overview = movie.overview
                    )

                    withContext(Dispatchers.Main) {
                        if (ok) {
                            Toast.makeText(this@MovieCatalog, "🎉 Compraste: ${movie.title}", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@MovieCatalog, "⚠️ Ya compraste esta película", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        )



        binding.recyclerMovies.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerMovies.adapter = adapter


        viewModel.movies.observe(this) { movies ->
            adapter.setMovies(movies)
        }
        viewModel.loadMovies(API_KEY)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}
