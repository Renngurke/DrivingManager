package com.example.drivingmanager

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.car_list_row.view.*


class CarListAdapter (val cl: CarList, val context: Context): RecyclerView.Adapter<CarListAdapter.ViewHolder>(){

    var selectionMode: Boolean = false
        get() = field
        set(value) {
            if(value == false){
                for (i in (MainActivity.cl.cars.size-1) downTo 0){
                    if(MainActivity.cl.cars[i].selected){
                        MainActivity.cl.cars.removeAt(i)
                    }
                }
            }
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarListAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.car_list_row, parent, false))
    }

    override fun getItemCount(): Int {
        return MainActivity.cl.cars.size
    }

    override fun onBindViewHolder(holder: CarListAdapter.ViewHolder, position: Int) {
        if(selectionMode){
            holder.selectionCB.visibility = View.VISIBLE
        }else{
            holder.selectionCB.visibility = View.GONE
        }

        holder.selectionCB.isChecked = MainActivity.cl.cars[position].selected
        holder.selectionCB.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener({ cb: CompoundButton?, checked: Boolean ->
            MainActivity.cl.cars[holder.layoutPosition].selected = checked
        }))
        holder.brand.setText(MainActivity.cl.cars[position].marke)
        holder.mod.setText(MainActivity.cl.cars[position].modell)

        holder.itemView.setOnClickListener(View.OnClickListener { v: View? ->
            // TODO: implement on list item clicked listener
            MainActivity.cl.index = holder.layoutPosition
            context.startActivity(Intent(context, CarActivity::class.java))
        })
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to
        val brand = view.brand
        val mod = view.mod
        val selectionCB = view.selectionCB;
    }
}