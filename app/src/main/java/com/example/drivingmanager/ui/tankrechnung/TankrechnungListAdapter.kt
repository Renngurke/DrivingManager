package com.example.drivingmanager

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.tankrechnung_list_row.view.*


class TankrechnungListAdapter(val items: MutableList<Tankrechnung>, val context: Context) :
    RecyclerView.Adapter<TankrechnungListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TankrechnungListAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.tankrechnung_list_row, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TankrechnungListAdapter.ViewHolder, position: Int) {
        val filename = MainActivity.cl.cars[MainActivity.cl.index].tankrechnungen[position].bild

        holder.datum.setText(items[position].datum.toString())
        holder.kosten.setText(items[position].preis.toString())
        holder.menge.setText(items[position].liter.toString())
        holder.foto.isVisible = filename.isNotEmpty()

        holder.itemView.setOnClickListener(View.OnClickListener { v: View? ->

            if (filename.isNotEmpty()) {
                val i = Intent(context, ShowPhoto::class.java)
                i.putExtra("filename", filename)
                context.startActivity(i)
            }
        })
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to
        val datum = view.tankrechnung_datum
        val kosten = view.tankrechnung_kosten
        val menge = view.tankrechnung_menge
        val foto = view.tankrechnung_foto
    }
}