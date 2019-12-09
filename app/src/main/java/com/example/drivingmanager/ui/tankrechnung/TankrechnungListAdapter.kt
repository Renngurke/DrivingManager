package com.example.drivingmanager

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.core.view.marginTop
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.car_list_row.view.*
import kotlinx.android.synthetic.main.tankrechnung_list_row.view.*


class TankrechnungListAdapter (val items : ArrayList<Tankrechnung>, val context: Context): RecyclerView.Adapter<TankrechnungListAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TankrechnungListAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.tankrechnung_list_row, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TankrechnungListAdapter.ViewHolder, position: Int) {
        holder.datum.setText(items[position].datum)
        holder.kosten.setText(items[position].kosten.toString())
        holder.menge.setText(items[position].menge.toString())
        holder.itemView.setOnClickListener(View.OnClickListener { v: View? ->
            context.startActivity(Intent(context, ShowPhoto::class.java))
        })
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to
        val datum = view.tankrechnung_datum
        val kosten = view.tankrechnung_kosten
        val menge = view.tankrechnung_menge;
    }
}