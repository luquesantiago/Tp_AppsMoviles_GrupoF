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
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.tp_appsmoviles_grupof.viewmodel.Opciones_Generales
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

        RoomApp.init(this)

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

        val preferencias = getSharedPreferences("credenciales", MODE_PRIVATE)
        val usuarioGuardado = preferencias.getString("nombre_usuario", "")


        if (usuarioGuardado != "") {
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
                                val preferencias = getSharedPreferences("credenciales", MODE_PRIVATE)
                                preferencias.edit().putString("nombre_usuario", usuario.nombre).apply()
                                mostrarNotificacionRecordarUsuario()
                            } else {
                                val preferencias = getSharedPreferences("credenciales", MODE_PRIVATE)
                                preferencias.edit().remove("nombre_usuario").apply() // si no esta marcado, o lo saco, tengo que borrar
                            }


                            // 🔹 Esperar un poquito antes de cambiar de pantalla, sino a veces no muestra notificacion
                            etUser.postDelayed({
                                iniciarActividadPrincipal(usuario.nombre)
                            }, 500) // cuando creas un dispositivo virtual nuevo, me los pide justo despues de iniciar sesion, y no antes de iniciar la aplicacion; si es así, le doy mas tiempo antes de que cambie de vista.
                        }


                    }
                }
            }
        }

        btnRegistrar.setOnClickListener {
            val intent = Intent(this, Registro::class.java)
            intent.putExtra("NOMBRE", etUser.text.toString())
            startActivity(intent)

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
    @SuppressLint("MissingPermission") //para que no salte el error de permisos

    private fun mostrarNotificacionRecordarUsuario() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // pregunta si es android 8 o mas, y crea canal
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canal = NotificationChannel(
                "canal_recordar_usuario",
                "Recordar Usuario",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Canal para notificaciones de Recordar Usuario"
            }
            notificationManager.createNotificationChannel(canal)
        }

        // creand o la notificacion
        val builder = NotificationCompat.Builder(this, "canal_recordar_usuario")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Recordar Usuario")
            .setContentText("Se recordará tu usuario en el próximo inicio.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        // Android 13 o superior → verificar permiso

        // antes solo estaba implementada esta parte

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                == PackageManager.PERMISSION_GRANTED
            ) {
                notificationManager.notify(1001, builder.build())
            } else {
                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 100)
            }
        } else {
            // Android 12 o inferior → permiso no necesario
            notificationManager.notify(1001, builder.build())
        }
    }


//borré la funcion de verificar version, la usaba una sola vez

// private fun tienePermisoNotificaciones(): Boolean {



    }




