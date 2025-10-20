package com.example.tp_appsmoviles_grupof

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.Menu
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.tp_appsmoviles_grupof.database.local.RoomApp
import com.example.tp_appsmoviles_grupof.database.local.entities.userEntity
import com.example.tp_appsmoviles_grupof.viewmodel.Opciones_Generales

class MainActivity : AppCompatActivity() {
    lateinit var cbRecordarUsuario: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        RoomApp.init(this)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Login"

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etUser = findViewById<EditText>(R.id.etUser)
        val etPass = findViewById<EditText>(R.id.etPass)
        val cbMostrar = findViewById<CheckBox>(R.id.cbMostrar)
        val btnIniciar = findViewById<Button>(R.id.btnIniciar)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)
        cbRecordarUsuario = findViewById(R.id.cbRecordarUsuario)

        // Shared preferences para login
        val preferencias = getSharedPreferences(getString(R.string.sp_credenciales), MODE_PRIVATE)
        val usuarioGuardado = preferencias.getString(getString(R.string.nombre), "") ?: ""

        if (usuarioGuardado.isNotEmpty()) {
            etUser.setText(usuarioGuardado)
            cbRecordarUsuario.isChecked = true
        }

        obtenerNombreDesdeIntent()?.let { etUser.setText(it) }

        btnIniciar.setOnClickListener {
            val nombre = etUser.text.toString().trim()
            val password = etPass.text.toString().trim()

            if (nombre.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Completar datos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch(Dispatchers.IO) {
                val dao = RoomApp.database.userDao()
                val usuario = dao.buscarPorNombre(nombre)

                withContext(Dispatchers.Main) {
                    when {
                        usuario == null -> {
                            Toast.makeText(this@MainActivity, "Usuario no registrado", Toast.LENGTH_SHORT).show()
                        }
                        usuario.password != password -> {
                            Toast.makeText(this@MainActivity, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            // Guardá el usuario si marcaron “recordar”
                            if (cbRecordarUsuario.isChecked) {
                                preferencias.edit().putString(getString(R.string.nombre), usuario.nombre).apply()
                            } else {
                                preferencias.edit().remove(getString(R.string.nombre)).apply()
                            }

                            Toast.makeText(this@MainActivity, "Bienvenido ${usuario.nombre}", Toast.LENGTH_SHORT).show()
                            irAMenu(usuario)
                        }
                    }
                }
            }
        }

        btnRegistrar.setOnClickListener {
            val intent = Intent(this, Registro::class.java)
            intent.putExtra("NOMBRE", etUser.text.toString())
            startActivity(intent)
            finish()
        }

        cbMostrar.setOnCheckedChangeListener { _, isChecked ->
            etPass.inputType = if (isChecked)
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            else
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            etPass.setSelection(etPass.text.length)
        }
    }


    private fun irAMenu(usuario: userEntity) {
        val intent = Intent(this@MainActivity, Opciones_Generales::class.java)
        intent.putExtra("nombreIniciado", usuario.nombre)
        intent.putExtra("userId", usuario.idUser.toLong())
        startActivity(intent)
        finish()
    }

    private fun obtenerNombreDesdeIntent(): String? {
        val nombreRegistro = intent.getStringExtra("NombreRegistrado")
        val nombreLogin = intent.getStringExtra("NOMBRE")
        return nombreRegistro ?: nombreLogin
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logo_barra, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
