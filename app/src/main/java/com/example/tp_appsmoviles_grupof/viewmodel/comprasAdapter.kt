package com.example.tp_appsmoviles_grupof.viewmodel.Peliculas

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tp_appsmoviles_grupof.R
import com.example.tp_appsmoviles_grupof.database.local.entities.comprasEntity

class ComprasAdapter(
    private var items: List<comprasEntity>,
    private val onTrailerClick: (comprasEntity) -> Unit
) : RecyclerView.Adapter<ComprasAdapter.VH>() {

    class VH(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_compra, parent, false)
    ) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvOverview: TextView = itemView.findViewById(R.id.tvOverview)
        val btnTrailer: Button = itemView.findViewById(R.id.btnTrailer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(parent)

    override fun onBindViewHolder(holder: VH, position: Int) {
        val c = items[position]
        holder.tvTitle.text = c.title
        holder.tvOverview.text = c.overview ?: "(Sin descripción)"
        holder.btnTrailer.setOnClickListener { onTrailerClick(c) }
        holder.btnTrailer.isEnabled = !c.trailerKey.isNullOrEmpty()
    }

    override fun getItemCount(): Int = items.size

    fun submit(list: List<comprasEntity>) {
        items = list
        notifyDataSetChanged()
    }
}
