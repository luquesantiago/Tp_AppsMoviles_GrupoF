package com.example.tp_appsmoviles_grupof.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.tp_appsmoviles_grupof.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

class PrimerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragmento_detalle_peli, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titulo = arguments?.getString("titulo")
        val movieId = arguments?.getInt("movieId")

        val tvTitulo = view.findViewById<TextView>(R.id.tvDetalleTitulo)
        val tvDescripcion = view.findViewById<TextView>(R.id.tvDetalleDescripcion)
        val tvFecha = view.findViewById<TextView>(R.id.tvDetalleFecha)
        val btnConfirmar = view.findViewById<Button>(R.id.btnConfirmarCompra)
        val btnCerrar = view.findViewById<Button>(R.id.btnCerrar)

        tvTitulo.text = titulo ?: "Sin título"

        // Cargar detalles de TMDB
        if (movieId != null) {
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val apiKey = "TU_API_KEY_AQUI"
                    val url =
                        "https://api.themoviedb.org/3/movie/$movieId?api_key=$apiKey&language=es-ES"
                    val response = URL(url).readText()
                    val json = JSONObject(response)

                    val overview = json.optString("overview", "Sin descripción disponible")
                    val releaseDate = json.optString("release_date", "Fecha no disponible")

                    withContext(Dispatchers.Main) {
                        tvDescripcion.text = overview
                        tvFecha.text = "Fecha de estreno: $releaseDate"
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Error al obtener detalles", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        btnConfirmar.setOnClickListener {
            Toast.makeText(requireContext(), "¡Compra confirmada de '$titulo'!", Toast.LENGTH_SHORT).show()
            cerrarFragment()
        }

        btnCerrar.setOnClickListener {
            cerrarFragment()
        }
    }

    private fun cerrarFragment() {
        // Ocultar el contenedor
        requireActivity().findViewById<View>(R.id.fragmentContainer).visibility = View.GONE
        parentFragmentManager.popBackStack()
    }
}
