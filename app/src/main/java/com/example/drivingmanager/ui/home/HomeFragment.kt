package com.example.drivingmanager.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.drivingmanager.MainActivity
import com.example.drivingmanager.R
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        //val textView: TextView = root.findViewById(R.id.text_home)

        root.haupt_co2.text = MainActivity.cl.cars[MainActivity.cl.index].co2Aus.toString() // ?
        root.haupt_vorher.text =
            MainActivity.cl.cars[MainActivity.cl.index].uebrigeKM().toString() // richtige funktion?
        root.haupt_verbrauch.text = String.format(
            "%.2f",
            MainActivity.cl.cars[MainActivity.cl.index].verbDurchschnitt()
        ) // richtige funktion?

        homeViewModel.text.observe(this, Observer {
            //textView.text = it
        })
        return root
    }
}