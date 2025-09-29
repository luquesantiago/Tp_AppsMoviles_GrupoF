package com.example.tp_appsmoviles_grupof.viewmodel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tp_appsmoviles_grupof.R
import com.example.tp_appsmoviles_grupof.viewmodel.Producto

class Producto_Adapter (var productos : MutableList<Producto>, var context : Context) : RecyclerView.Adapter<Producto_Adapter.ProductoViewHolder>(){
    class ProductoViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        val txtNombre : TextView
        val txtStock : TextView


        init {
            txtNombre = view.findViewById(R.id.tv_nombre)
            txtStock = view.findViewById(R.id.tv_stock)

        }
    }


    override fun getItemCount()=productos.size

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_producto, viewGroup, false)
        return ProductoViewHolder (view)
    }
    override fun onBindViewHolder(viewHolder: ProductoViewHolder, position: Int) {
        val item = productos.get(position)
        viewHolder.txtNombre.text = item.nombre
        viewHolder.txtStock.text = item.stock.toString()
    }
}