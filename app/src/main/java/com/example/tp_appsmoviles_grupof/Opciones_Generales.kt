package com.example.tp_appsmoviles_grupof

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

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

        lateinit var btnLista : Button

        lateinit var btnCerrar : Button



        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Opciones Generales"

        lateinit var TwNombre: TextView


        val nombreUsuario = intent.getStringExtra("nombreIniciado")

        TwNombre = findViewById(R.id.idUsuarioMostrado)
        TwNombre.text = nombreUsuario

        btnLista = findViewById(R.id.btnListaStock)

        btnCerrar = findViewById(R.id.btnCerrarSesion)


        btnLista.setOnClickListener {
            val intent = Intent(this, ListadoCompraVentaActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnCerrar.setOnClickListener {
                // Volver al login
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
        }



    }






}





