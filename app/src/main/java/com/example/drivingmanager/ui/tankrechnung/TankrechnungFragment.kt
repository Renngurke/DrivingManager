package com.example.drivingmanager.ui.tankrechnung

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.drivingmanager.R

class TankrechnungFragment : Fragment() {

    private lateinit var tankrechnungViewModel: TankrechnungViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tankrechnungViewModel =
            ViewModelProviders.of(this).get(TankrechnungViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_tankrechnung, container, false)
        val textView: TextView = root.findViewById(R.id.text_tankrechnung)
        tankrechnungViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}