package com.example.tp_appsmoviles_grupof.viewmodel

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tp_appsmoviles_grupof.MainActivity
import com.example.tp_appsmoviles_grupof.R

class registro : AppCompatActivity() {
    lateinit var btnRegistrar: Button
    lateinit var EtUser: EditText
    lateinit var EtPass1: EditText
    lateinit var EtPass2: EditText

    lateinit var btnIniciar: Button



    override fun onCreate(savedInstanceState: Bundle?) {




        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Registro"

        val etUser = findViewById<EditText>(R.id.EtUser)



        val bundle : Bundle? = intent.extras
        if(bundle != null){
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


        btnIniciar.setOnClickListener {

            intent = Intent(this, MainActivity::class.java)
            intent.putExtra("NombreRegistrado",EtUser.text.toString())
            Toast.makeText(this, "Volviendo al Login", Toast.LENGTH_SHORT).show()
            startActivity(intent)
            finish()

        }




            btnRegistrar.setOnClickListener{
                if (etUser.text.toString().isEmpty() || EtPass1.text.toString().isEmpty() || EtPass2.text.toString().isEmpty()) {
                    Toast.makeText(this, "Completar Datos", Toast.LENGTH_SHORT).show()
                }else {
                intent = Intent(this, MainActivity::class.java)
                intent.putExtra("NOMBRE", EtUser.text.toString())
                Toast.makeText(this, "Registro Correcto", Toast.LENGTH_SHORT).show()
                startActivity(intent)
                finish()
            }
        }



    }
}