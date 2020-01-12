package com.example.drivingmanager.ui.reisekosten

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.drivingmanager.MainActivity
import com.example.drivingmanager.R
import kotlinx.android.synthetic.main.fragment_reisekosten.view.*

class ReisekostenFragment : Fragment() {

    private lateinit var reisekostenViewModel: ReisekostenViewModel
    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        reisekostenViewModel =
            ViewModelProviders.of(this).get(ReisekostenViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_reisekosten, container, false)

        root.buttonRkv.setOnClickListener { view ->

            if (root.rkv_eingabe_km.text.toString().isNotEmpty()) {
                val car = MainActivity.cl.cars[MainActivity.cl.index]
                val eingabe = root.rkv_eingabe_km.text.toString().toInt()

                root.rkv_co2.text = (car.rkv_co2(eingabe).toString() + " g")
                root.rkv_kosten.text = (car.rkv_kosten(eingabe).toString() + " €")
                root.rkv_stopps.text = car.rkv_anzStops(eingabe).toString()
            }
        }

        return root
    }

}