package com.example.drivingmanager.ui.statistiken

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.drivingmanager.R

class StatistikFragment : Fragment() {

    private lateinit var statistikViewModel: StatistikViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        statistikViewModel =
            ViewModelProviders.of(this).get(StatistikViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_statistik, container, false)
        return root
    }

}