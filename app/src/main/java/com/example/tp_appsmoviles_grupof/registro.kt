package com.example.tp_appsmoviles_grupof


import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Patterns
import android.widget.*
import android.view.Menu
import android.widget.Button

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged

class registro : AppCompatActivity() {
    lateinit var btnRegistrar: Button
    lateinit var EtUser: EditText
    lateinit var EtPass1: EditText
    lateinit var EtPass2: EditText

    lateinit var EtMail: EditText

    lateinit var EtTelefono: EditText

    lateinit var btnIniciar: Button


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Registro"

        val etUser = findViewById<EditText>(R.id.EtUser)


        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            val usuario = bundle.getString("NOMBRE")
            if (usuario != null) {
                etUser.setText(usuario)
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnRegistrar = findViewById(R.id.btnRegistrar)
        EtUser = findViewById(R.id.EtUser)
        EtPass1 = findViewById(R.id.EtPass1)
        EtPass2 = findViewById(R.id.EtPass2)
        btnIniciar = findViewById<Button>(R.id.btnIniciar)
        EtMail = findViewById(R.id.EtMail)
        EtTelefono = findViewById(R.id.EtTelefono)

        //validacion tiempo real

        EtPass1.doOnTextChanged { text, _, _, _ ->
            val p1 = text?.toString().orEmpty()
            EtPass1.error = when {
                p1.isEmpty() -> "Ingresá una contraseña"
                p1.length < 6 -> "Mínimo 6 caracteres"
                else -> null
            }
            // si ya escribió la 2, re-chequear coincidencia
            val p2 = EtPass2.text?.toString().orEmpty()
            if (p2.isNotEmpty()) EtPass2.error = if (p1 == p2) null else "No coinciden"
        }

        EtPass2.doOnTextChanged { text, _, _, _ ->
            val p2 = text?.toString().orEmpty()
            val p1 = EtPass1.text?.toString().orEmpty()
            EtPass2.error = when {
                p2.isEmpty() -> "Repetí la contraseña"
                p1.isEmpty() -> "Completá la contraseña de arriba"
                p1 == p2 -> null
                else -> "No coinciden"
            }
        }

        EtMail.doOnTextChanged { text, _, _, _ ->
            val mail = text?.toString().orEmpty()
            EtMail.error = when {
                mail.isEmpty() -> "Ingresá tu email"
                !Patterns.EMAIL_ADDRESS.matcher(mail).matches() -> "Email inválido"
                else -> null
            }
        }

        EtTelefono.doOnTextChanged { text, _, _, _ ->
            val tel = text?.toString().orEmpty().replace(" ", "")
            val ok = Regex("^\\+?\\d{12,15}$").matches(tel)
            EtTelefono.error = when {
                tel.isEmpty() -> "Ingresá tu teléfono"
                !ok -> "Teléfono inválido"
                else -> null
            }
        }


        btnIniciar.setOnClickListener {

            intent = Intent(this, MainActivity::class.java)
            intent.putExtra("NombreRegistrado", EtUser.text.toString())
            Toast.makeText(this, "Volviendo al Login", Toast.LENGTH_SHORT).show()
            startActivity(intent)
            finish()

        }




        btnRegistrar.setOnClickListener {
            val user = EtUser.text.toString().trim()
            val p1 = EtPass1.text.toString()
            val p2 = EtPass2.text.toString()
            val mail = EtMail.text.toString().trim()
            val tel = EtTelefono.text.toString().trim()

            if (user.isEmpty() || p1.isEmpty() || p2.isEmpty() || mail.isEmpty() || tel.isEmpty()) {
                Toast.makeText(this, "Completar Datos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (p1.length < 6) {
                Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (p1 != p2) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                Toast.makeText(this, "Email inválido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!Regex("^\\+?\\d{12,15}$").matches(tel.replace(" ", ""))) {
                Toast.makeText(this, "Teléfono inválido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            intent = Intent(this, MainActivity::class.java)
            intent.putExtra("NOMBRE", user)
            Toast.makeText(this, "Registro Correcto", Toast.LENGTH_SHORT).show()
            startActivity(intent)
            finish()
        }
    }



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logo_barra, menu)
        return super.onCreateOptionsMenu(menu)
    }


}