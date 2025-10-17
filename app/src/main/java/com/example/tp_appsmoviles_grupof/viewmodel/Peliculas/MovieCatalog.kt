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
import androidx.appcompat.widget.Toolbar
import com.example.tp_appsmoviles_grupof.R
import com.example.tp_appsmoviles_grupof.viewmodel.Opciones_Generales

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

        })

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Toolbar"

        binding.recyclerMovies.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerMovies.adapter = adapter

        // Observa los cambios del ViewModel
        viewModel.movies.observe(this) { movies ->
            adapter.setMovies(movies)
        }

        viewModel.loadMovies("a900d45013d2d7ea128b1e1d2bb0dc94")
    }


    //toolbar del "agregar prodyucto" que al final no usamos, solo tiene una flecha a opciones generales
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_agregar, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_volver -> {
                // Abrir pantalla para agregar producto
                //Toast.makeText(this, "Agregar producto", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Opciones_Generales::class.java)
                startActivity(intent)
                finish()
                true
            }


            else -> super.onOptionsItemSelected(item)
        }
    }





}



