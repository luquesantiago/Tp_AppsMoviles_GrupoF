package com.example.tp_appsmoviles_grupof.viewmodel.Peliculas

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp_appsmoviles_grupof.R
import com.example.tp_appsmoviles_grupof.database.local.RoomApp
import com.example.tp_appsmoviles_grupof.viewmodel.Opciones_Generales
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ComprasActivity : AppCompatActivity() {

    private lateinit var adapter: ComprasAdapter
    private var currentUserId: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compras)


        RoomApp.init(this)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Mis peliculas"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val recycler = findViewById<RecyclerView>(R.id.recyclerCompras)
        recycler.layoutManager = LinearLayoutManager(this)
        adapter = ComprasAdapter(emptyList()) { compra ->
            openTrailer(compra.trailerKey)
        }
        recycler.adapter = adapter

        val passedUserId = intent.getLongExtra("userId", 0L)
        val nombreUsuario = intent.getStringExtra("nombreIniciado") ?: ""


        lifecycleScope.launch(Dispatchers.IO) {
            currentUserId = if (passedUserId != 0L) {
                passedUserId
            } else {
                RoomApp.database.userDao().buscarPorNombre(nombreUsuario)?.idUser?.toLong() ?: 0L
            }


            RoomApp.database.comprasDao()
                .getComprasByUser(currentUserId)
                .collectLatest { list ->
                    withContext(Dispatchers.Main) {
                        adapter.submit(list)
                    }
                }
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
//            R.id.idItemSalir -> {
//                startActivity(Intent(this, Opciones_Generales::class.java))
//                true
//            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun openTrailer(key: String?) {
        if (key.isNullOrEmpty()) {
            Toast.makeText(this, "Esta compra no tiene tráiler", Toast.LENGTH_SHORT).show()
            return
        }
        startActivity(
            Intent(this, TrailerActivity::class.java)
                .putExtra("VIDEO_ID", key)
        )
    }
}
