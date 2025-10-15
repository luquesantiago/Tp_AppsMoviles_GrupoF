

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tp_appsmoviles_grupof.viewmodel.Peliculas.
import com.example.tp_appsmoviles_grupof.databinding.ActivityCatalogBinding

class MovieCatalog : AppCompatActivity() {

    private lateinit var binding: ActivityCatalogBinding
    private val viewModel: MovieViewModel by viewModels()
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatalogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = MovieAdapter(onBuyClick = { movie ->
            Toast.makeText(this, "Compraste: ${movie.title}", Toast.LENGTH_SHORT).show()
            // Acá podrías guardar en Room si querés simular compra
        })

        binding.recyclerMovies.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerMovies.adapter = adapter

        // Observa los cambios del ViewModel
        viewModel.movies.observe(this) { movies ->
            adapter.setMovies(movies)
        }

        // Carga desde la API (reemplazá con tu API key)
        viewModel.loadMovies("TU_API_KEY_AQUI")
    }
}
