package com.example.drivingmanager.ui.tankrechnung

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drivingmanager.R
import com.example.drivingmanager.Tankrechnung
import com.example.drivingmanager.TankrechnungListAdapter
import kotlinx.android.synthetic.main.fragment_tankrechnung.*
import java.util.*
import kotlin.collections.ArrayList
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_tankrechnung.view.*


class TankrechnungFragment : Fragment() {

    private lateinit var tankrechnungViewModel: TankrechnungViewModel

    var rechnungen: ArrayList<Tankrechnung>

    init {
        rechnungen = ArrayList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tankrechnungViewModel =
            ViewModelProviders.of(this).get(TankrechnungViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_tankrechnung, container, false)

        root.addTankrechnung.setOnClickListener { view ->
            startActivity(Intent(this.context, AddTankrechnung::class.java))
        }

        rechnungen.add(Tankrechnung("01.01.2019", 13.37F, 42))

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
}