package com.example.drivingmanager.ui.tankrechnung

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drivingmanager.MainActivity
import com.example.drivingmanager.R
import com.example.drivingmanager.Tankrechnung
import com.example.drivingmanager.TankrechnungListAdapter
import kotlinx.android.synthetic.main.fragment_tankrechnung.*
import kotlinx.android.synthetic.main.fragment_tankrechnung.view.*


class TankrechnungFragment : Fragment() {

    private lateinit var tankrechnungViewModel: TankrechnungViewModel
    private lateinit var root: View

    val rechnungen: MutableList<Tankrechnung> =
        MainActivity.cl.cars[MainActivity.cl.index].tankrechnungen

    var size: Int = 0

    fun toggleVisibility() {
        if (rechnungen.isEmpty()) {
            root.tankrechnung_list.visibility = View.GONE
            root.startText1.visibility = View.VISIBLE
            root.startText2.visibility = View.VISIBLE
        } else {
            root.startText1.visibility = View.GONE
            root.startText2.visibility = View.GONE
            root.tankrechnung_list.visibility = View.VISIBLE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tankrechnungViewModel =
            ViewModelProviders.of(this).get(TankrechnungViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_tankrechnung, container, false)

        val int = Intent(this.context, AddTankrechnung::class.java)

        root.addTankrechnung.setOnClickListener { view ->
            startActivity(int)
        }

        root.tankrechnung_list.layoutManager = LinearLayoutManager(this.context)
        root.tankrechnung_list.adapter = TankrechnungListAdapter(rechnungen, this.requireContext())

        toggleVisibility()

        size = rechnungen.size
        return root
    }

    override fun onResume() {
        super.onResume()
        if (rechnungen.size > size) {
            Toast.makeText(this.context, "Tankrechnung hinzugefügt", Toast.LENGTH_LONG).show()
            toggleVisibility()
        }
        tankrechnung_list.adapter?.notifyDataSetChanged()

    }
}