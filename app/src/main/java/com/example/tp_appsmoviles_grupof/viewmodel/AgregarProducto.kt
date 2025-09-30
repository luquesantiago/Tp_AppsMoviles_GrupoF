package com.example.tp_appsmoviles_grupof.viewmodel

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tp_appsmoviles_grupof.viewmodel.Opciones_Generales
import com.example.tp_appsmoviles_grupof.R

class AgregarProducto : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {

        lateinit var etProducto : EditText
        lateinit var etStock : EditText

        lateinit var btnAgregar : Button



        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agregar_producto)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Agregar Producto"



        etProducto = findViewById(R.id.etProducto)
        etStock = findViewById(R.id.etStock)
        btnAgregar = findViewById(R.id.btnAgregar)


        btnAgregar.setOnClickListener {
            val producto = etProducto.text.toString()
            val stockTexto = etStock.text.toString()

            if (producto.isEmpty() || stockTexto.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val stock = stockTexto.toInt()

                Toast.makeText(this, "Producto agregado: $producto ($stock unidades)", Toast.LENGTH_SHORT).show()
            }
        }




    }

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