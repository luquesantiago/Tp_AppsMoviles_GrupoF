package com.example.tp_appsmoviles_grupof

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
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
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.tp_appsmoviles_grupof.viewmodel.Opciones_Generales
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.tp_appsmoviles_grupof.RoomApp

class MainActivity : AppCompatActivity() {
    lateinit var cbRecordarUsuario: CheckBox //defino afuera del onCreate porque tiraba error la funcion login()


    override fun onCreate(savedInstanceState: Bundle?) {


        //// crear el canal para la noti

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "canal_recordar_usuario",
                "Recordar Usuario",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Canal para notificaciones de Recordar Usuario"
            }

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }



        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        /// nombre a la toolbar
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

        var preferencias =
            getSharedPreferences(resources.getString(R.string.sp_credenciales), MODE_PRIVATE)
        var usuarioGuardado = preferencias.getString(resources.getString(R.string.nombre), "")

        //shared preferences para contraseña --> var passwordGuardada = preferencias.getString(resources.getString(R.string.password),"")


        //if(usuarioGuardado!="" && passwordGuardada !="")
        //    iniciarActividadPrincipal(usuarioGuardado.toString())


        if (usuarioGuardado != "") {
            etUser.setText(usuarioGuardado)
            cbRecordarUsuario.isChecked = true
        }

        val nombre = obtenerNombreDesdeIntent()
        if (nombre != null) {
            etUser.setText(nombre) // despues de volver del registrar, guarda el nombre previamente escrito
        }


        btnIniciar.setOnClickListener {
            val nombre = etUser.text.toString().trim()
            val password = etPass.text.toString().trim()

            if (nombre.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Completar datos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val dao = RoomApp.database.userDao()
                val usuario = dao.buscarPorNombre(nombre)

                withContext(Dispatchers.Main) {
                    when {
                        usuario == null -> {
                            Toast.makeText(
                                this@MainActivity,
                                "Usuario no registrado",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        usuario.password != password -> {
                            Toast.makeText(
                                this@MainActivity,
                                "Contraseña incorrecta",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        else -> {
                            Toast.makeText(
                                this@MainActivity,
                                "Bienvenido ${usuario.nombre}",
                                Toast.LENGTH_SHORT
                            ).show()

                            if (cbRecordarUsuario.isChecked) {
                                val preferencias = getSharedPreferences(
                                    getString(R.string.sp_credenciales),
                                    MODE_PRIVATE
                                )
                                preferencias.edit()
                                    .putString(getString(R.string.nombre), usuario.nombre)
                                    .apply()

                                mostrarNotificacionRecordarUsuario()
                            }

                            // 🔹 Esperar un poquito antes de cambiar de pantalla
                            etUser.postDelayed({
                                iniciarActividadPrincipal(usuario.nombre)
                            }, 300)
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
            if (isChecked) {
                etPass.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                etPass.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            etPass.setSelection(etPass.text.length)
        }


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

///cosas para las notificaciones aca abajo

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 100) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // si hay permis os, muestro
                mostrarNotificacionRecordarUsuario()
            } else {
                Toast.makeText(this, "Permiso de notificaciones denegado", Toast.LENGTH_SHORT).show()
            }
        }
    }
    @SuppressLint("MissingPermission")
    private fun mostrarNotificacionRecordarUsuario() {
        val builder = NotificationCompat.Builder(this, "canal_recordar_usuario")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Recordar Usuario")
            .setContentText("Se recordará tu usuario en el próximo inicio.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)


        // Verificar y pedir permiso si hace falta
        //no me muestra la noti sino
        if (tienePermisoNotificaciones()) {
            NotificationManagerCompat.from(this).notify(1001, builder.build())
        } else {
            requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 100)
        }


    }

    private fun tienePermisoNotificaciones(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }


    }






