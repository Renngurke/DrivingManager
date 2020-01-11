package com.example.drivingmanager.ui.kilometerstand

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.drivingmanager.R

class KmFragment : Fragment() {

    private lateinit var kmFragmentViewModel: KmViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        kmFragmentViewModel =
            ViewModelProviders.of(this).get(KmViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_kmstand, container, false)
        //val textView: TextView = root.findViewById(R.id.text_kmstand)
        kmFragmentViewModel.text.observe(this, Observer {
        //    textView.text = it
        })



        return root
    }
}