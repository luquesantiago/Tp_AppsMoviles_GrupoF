package com.example.tp_appsmoviles_grupof.viewmodel

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tp_appsmoviles_grupof.MainActivity
import com.example.tp_appsmoviles_grupof.R
import com.example.tp_appsmoviles_grupof.viewmodel.Peliculas.ComprasActivity
import com.example.tp_appsmoviles_grupof.viewmodel.Peliculas.MovieCatalog

class Opciones_Generales : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_opciones_generales)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //lateinit var btnLista : Button

        lateinit var btnCerrar: Button

        lateinit var btnPeliculas: Button

        lateinit var btnPelisCompradas: Button



        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Opciones Generales"

        lateinit var TwNombre: TextView


        val nombreUsuario = intent.getStringExtra("nombreIniciado")
        val userId = intent.getLongExtra("userId", 0L)


        TwNombre = findViewById(R.id.idUsuarioMostrado)
        TwNombre.text = nombreUsuario

        //btnLista = findViewById(R.id.btnListaStock)

        btnCerrar = findViewById(R.id.btnCerrarSesion)

        btnPeliculas = findViewById(R.id.btnListaPeliculas)

        btnPelisCompradas = findViewById(R.id.btnPelisCompradas)


        /*btnLista.setOnClickListener {
        val intent = Intent(this, ListadoCompraVentaActivity::class.java)
        startActivity(intent)
        //finish()
        */




        btnCerrar.setOnClickListener {
            getSharedPreferences(getString(R.string.sp_credenciales), MODE_PRIVATE)
                .edit().putString(getString(R.string.nombre), "").apply()

            val i = Intent(this, MainActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK) // ✅
            startActivity(i)
        }

        btnPeliculas.setOnClickListener {
            startActivity(
                Intent(this, MovieCatalog::class.java)
                    .putExtra("userId", userId)
                    .putExtra("nombreIniciado", nombreUsuario)
            )
        }


        btnPelisCompradas.setOnClickListener {
            startActivity(
                Intent(this, ComprasActivity::class.java)
                    .putExtra("userId", userId)
                    .putExtra("nombreIniciado", nombreUsuario)
            )
        }



        }






}
