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
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.widget.Toolbar
import com.example.tp_appsmoviles_grupof.R
import com.example.tp_appsmoviles_grupof.viewmodel.Opciones_Generales
import com.example.tp_appsmoviles_grupof.viewmodel.PrimerFragment

class MovieCatalog : AppCompatActivity() {

    private lateinit var binding: ActivityCatalogBinding
    private val viewModel: MovieViewModel by viewModels()
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatalogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        adapter = MovieAdapter(
            movies = emptyList(),
            onBuyClick = { movie ->
                val fragment = PrimerFragment()

                val args = Bundle()
                args.putString("titulo", movie.title)
                args.putInt("movieId", movie.id)
                fragment.arguments = args

                val container = findViewById<View>(R.id.fragmentContainer)
                container.visibility = View.VISIBLE

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        )



        binding.recyclerMovies.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerMovies.adapter = adapter

        // Observa los cambios del ViewModel
        viewModel.movies.observe(this) { movies ->
            adapter.setMovies(movies)
        }

        viewModel.loadMovies("a900d45013d2d7ea128b1e1d2bb0dc94")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.idItemSalir -> {
                // Volver al menu
                val intent = Intent(this, Opciones_Generales::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.primer_menu, menu)
        return true
    }
}
