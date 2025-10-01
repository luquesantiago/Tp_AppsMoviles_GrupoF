package com.example.tp_appsmoviles_grupof

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Patterns
import android.widget.*
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.tp_appsmoviles_grupof.database.local.entities.userEntity





class Registro : AppCompatActivity() {
    lateinit var btnRegistrar: Button
    lateinit var EtUser: EditText
    lateinit var EtPass1: EditText
    lateinit var EtPass2: EditText


    lateinit var EtMail: EditText

    lateinit var EtTelefono: EditText

    lateinit var btnIniciar: Button

    lateinit var  cbMostrar:CheckBox


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

        btnRegistrar =  findViewById(R.id.btnRegistrar)
        EtUser =        findViewById(R.id.EtUser)
        EtPass1 =       findViewById(R.id.EtPass1)
        EtPass2 =       findViewById(R.id.EtPass2)
        btnIniciar =    findViewById(R.id.btnIniciar)
        EtMail =        findViewById(R.id.EtMail)
        EtTelefono =    findViewById(R.id.EtTelefono)
        cbMostrar = findViewById<CheckBox>(R.id.CbMostrar)
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

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("NombreRegistrado", EtUser.text.toString())
            Toast.makeText(this, "Volviendo al Login", Toast.LENGTH_SHORT).show()
            startActivity(intent)
            finish()

        }

        cbMostrar.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                EtPass1.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                EtPass2.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                EtPass1.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                EtPass2.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }

            EtPass2.setSelection(EtPass2.text.length)
        }



        btnRegistrar.setOnClickListener {
            val user = EtUser.text.toString().trim()
            val p1 = EtPass1.text.toString()
            val p2 = EtPass2.text.toString()
            val mail = EtMail.text.toString().trim()
            val tel = EtTelefono.text.toString().trim()
            val direccion = "Sin dirección"

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

            lifecycleScope.launch {
                val dao = RoomApp.database.userDao()
                val existente = dao.getAllUsers().find { it.nombre == user }

                if (existente != null) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@Registro, "El usuario ya existe", Toast.LENGTH_SHORT).show()
                    }
                    return@launch
                }

                val nuevoUsuario = userEntity(
                    idUser = 0,
                    nombre = user,
                    password = p1,
                    email = mail,
                    telefono = tel,
                    direccion = direccion
                )

                val id = dao.insertUser(nuevoUsuario)

                withContext(Dispatchers.Main) {
                    Toast.makeText(this@Registro, "Usuario creado correctamente", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@Registro, MainActivity::class.java)
                    intent.putExtra("NOMBRE", user)
                    startActivity(intent)
                    finish()
                }
            }
        }

    }



}
