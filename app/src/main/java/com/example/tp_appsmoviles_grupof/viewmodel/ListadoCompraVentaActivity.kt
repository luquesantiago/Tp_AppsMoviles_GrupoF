package com.example.tp_appsmoviles_grupof.viewmodel

import Producto
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tp_appsmoviles_grupof.viewmodel.Producto_Adapter
import com.example.tp_appsmoviles_grupof.R

class ListadoCompraVentaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {


        lateinit var rvProductos : RecyclerView

        lateinit var productosAdapter : Producto_Adapter


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_listado_compra_venta)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Lista Productos"

        lateinit var TwNombre : TextView



        val nombreUsuario = intent.getStringExtra("nombreIniciado")

        TwNombre= findViewById(R.id.idUsuarioMostrado)
        TwNombre.text = nombreUsuario

        rvProductos = findViewById(R.id.rvProductos)

        productosAdapter = Producto_Adapter(getProductos(), this)
        rvProductos.adapter = productosAdapter

    }


    private fun getProductos() : MutableList<Producto>{


            val productos: MutableList<Producto> = ArrayList()
            productos.add(Producto(1, "Mouse Logitech G203", 12))
            productos.add(Producto(2, "Teclado Mecánico Redragon Kumara", 7))
            productos.add(Producto(3, "Monitor Samsung 24", 3))
            productos.add(Producto(4, "Auriculares HyperX Cloud II", 0))
            productos.add(Producto(5, "Notebook Lenovo IdeaPad 3", 5))
            productos.add(Producto(6, "Router TP-Link Archer C6", 9))
            productos.add(Producto(7, "Impresora Epson EcoTank L3250", 2))
            productos.add(Producto(8, "Silla Gamer Cougar Armor", 14))
            productos.add(Producto(9, "Disco SSD Kingston 480GB", 6))
            productos.add(Producto(10, "Memoria RAM Corsair 8GB DDR4", 11))
            productos.add(Producto(11, "Cámara Web Logitech C920", 4))
            productos.add(Producto(12, "Smartphone Samsung Galaxy A54", 8))
            productos.add(Producto(13, "Tablet Xiaomi Pad 6", 1))
            productos.add(Producto(14, "Cargador USB-C Anker 20W", 17))
            productos.add(Producto(15, "Cable HDMI 2.1 3m", 10))
            productos.add(Producto(16, "Mousepad XL Razer Goliathus", 0))
            productos.add(Producto(17, "Hub USB 3.0 UGREEN", 13))
            productos.add(Producto(18, "Lámpara LED Escritorio Xiaomi", 15))

            return productos



    }



/*



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.primer_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.idItemLista){

            val intent = Intent(this, ListadoCompraVentaActivity ::class.java)
            startActivity(intent)

        }

        return super.onOptionsItemSelected(item)
    }






 */


}