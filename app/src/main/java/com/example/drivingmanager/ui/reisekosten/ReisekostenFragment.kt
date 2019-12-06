package com.example.drivingmanager.ui.reisekosten

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.drivingmanager.R

class ReisekostenFragment : Fragment() {

    private lateinit var reisekostenViewModel: ReisekostenViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        reisekostenViewModel =
            ViewModelProviders.of(this).get(ReisekostenViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_resiekosten, container, false)
        return root
    }
}