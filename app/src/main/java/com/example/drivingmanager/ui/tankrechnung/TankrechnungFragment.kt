package com.example.drivingmanager.ui.tankrechnung

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drivingmanager.R
import com.example.drivingmanager.Tankrechnung
import com.example.drivingmanager.TankrechnungListAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_tankrechnung.*
import kotlinx.android.synthetic.main.fragment_tankrechnung.view.*


class TankrechnungFragment : Fragment() {

    private lateinit var tankrechnungViewModel: TankrechnungViewModel

    companion object {
        var rechnungen: ArrayList<Tankrechnung> = ArrayList()
    }

    var size: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tankrechnungViewModel =
            ViewModelProviders.of(this).get(TankrechnungViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_tankrechnung, container, false)

        val int = Intent(this.context, AddTankrechnung::class.java)

        root.addTankrechnung.setOnClickListener { view ->
            int.putExtra("Datei", rechnungen.last().bild)
            startActivity(int)
        }

        rechnungen.add(Tankrechnung(13.37, 42.69, ""))

        root.tankrechnung_list.layoutManager = LinearLayoutManager(this.context)
        root.tankrechnung_list.adapter = TankrechnungListAdapter(rechnungen, this.requireContext())

        if(rechnungen.isEmpty()){
            root.tankrechnung_list.visibility = View.GONE
            root.startText1.visibility = View.VISIBLE
            root.startText2.visibility = View.VISIBLE
        }else{
            root.startText1.visibility = View.GONE
            root.startText2.visibility = View.GONE
            root.tankrechnung_list.visibility = View.VISIBLE
        }


        return root
    }

    override fun onResume() {
        super.onResume()
        if (rechnungen.size > size) {
            Snackbar.make(tankrechnung_list, "Test", Snackbar.LENGTH_LONG).show()
        }
        tankrechnung_list.adapter?.notifyDataSetChanged()

    }
}