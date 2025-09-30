package com.example.tp_appsmoviles_grupof

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.widget.Button
import com.example.tp_appsmoviles_grupof.viewmodel.Opciones_Generales


class MainActivity : AppCompatActivity() {
    lateinit var cbRecordarUsuario : CheckBox //defino afuera del onCreate porque tiraba error la funcion login()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


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
         cbRecordarUsuario = findViewById<CheckBox>(R.id.cbRecordarUsuario)

        //shared preferences para login

        var preferencias = getSharedPreferences(resources.getString(R.string.sp_credenciales),MODE_PRIVATE)
        var usuarioGuardado = preferencias.getString(resources.getString(R.string.nombre),"")


        //var passwordGuardada = preferencias.getString(resources.getString(R.string.password),"")

        //if(usuarioGuardado!="" && passwordGuardada !="")
        //    iniciarActividadPrincipal(usuarioGuardado.toString())




        if (usuarioGuardado != "") { // no inicia sesion directamente como en el ejemplo, solo marca
            etUser.setText(usuarioGuardado)
            cbRecordarUsuario.isChecked = true
        }

        val nombre = obtenerNombreDesdeIntent()
        if (nombre != null) {
            etUser.setText(nombre) // despues de volver del registrar, guarda el nombre previamente escrito
        }


        btnIniciar.setOnClickListener {
            val mensaje = "Boton iniciar sesion"

            if (etUser.text.toString().isEmpty() || etPass.text.toString().isEmpty()) {
                Toast.makeText(this, "Completar Datos", Toast.LENGTH_SHORT).show()
            } else {
                    login(etUser.text.toString(),etPass.text.toString())

            }
        }


        btnRegistrar.setOnClickListener {

            val intent = Intent(this, registro::class.java)
            intent.putExtra("NOMBRE", etUser.text.toString())
            startActivity(intent)


        }
        cbMostrar.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                etPass.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                etPass.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            etPass.setSelection(etPass.text.length)
        }


    }
    private fun login(user: String, pass: String) { //funcion login de clase grabada; adaptada para que solo recuerde usuario

            if (cbRecordarUsuario.isChecked) {
                val preferencias = getSharedPreferences(getString(R.string.sp_credenciales), MODE_PRIVATE)
                preferencias.edit().putString(getString(R.string.nombre), user).apply()
                // preferencias.edit().putString(getString(R.string.password), pass).apply()
            }

            iniciarActividadPrincipal(user)

    }


    private fun obtenerNombreDesdeIntent(): String? {

        val nombreRegistro = intent.getStringExtra("NombreRegistrado")

        val nombreLogin = intent.getStringExtra("NOMBRE")

        return nombreRegistro ?: nombreLogin
    }


    private fun iniciarActividadPrincipal(nombreUsuario: String) {
        val intent = Intent(this, Opciones_Generales::class.java)
        intent.putExtra("nombreIniciado", nombreUsuario)
        startActivity(intent)
        finish()
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logo_barra, menu)
        return super.onCreateOptionsMenu(menu)
    }




}


