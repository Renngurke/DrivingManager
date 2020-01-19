package com.example.drivingmanager.ui.kilometerstand

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.drivingmanager.MainActivity
import com.example.drivingmanager.R
import kotlinx.android.synthetic.main.fragment_kmstand.*
import kotlinx.android.synthetic.main.fragment_kmstand.view.*
import kotlinx.android.synthetic.main.fragment_reisekosten.view.*

class KmFragment : Fragment() {

    private lateinit var kmFragmentViewModel: KmViewModel
    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        kmFragmentViewModel =
            ViewModelProviders.of(this).get(KmViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_kmstand, container, false)

        /*
        if(root.add_kmstand.text.toString().isNotEmpty()) {
            var index: Int = MainActivity.cl.index
            var kmdifferenz = add_kmstand.text.toString().toInt() - MainActivity.cl.cars[index].gesKM
            root.km_diff.text = kmdifferenz.toString()
        }
         */ //Dynamisch die Kilometerdifferenzt anzeigen lassen, crasht die App momentan

        root.buttonKms.setOnClickListener {
            if (add_kmstadt.text.toString().isNotEmpty()
                && add_kmstadt.text.toString().isNotEmpty()
                && add_kmhybrid.text.toString().isNotEmpty() ) {

                var kmStadt = add_kmstadt.text.toString().toInt()
                var kmAutobahn = add_kmautobahn.text.toString().toInt()
                var kmHybrid = add_kmhybrid.text.toString().toInt()

                var car = MainActivity.cl.cars[MainActivity.cl.index]

                car.kmStand_angeben(kmStadt, kmAutobahn, kmHybrid)

                add_kmstadt.text = null
                add_kmautobahn.text = null
                add_kmhybrid.text = null

                /*
                Toast.makeText(this, "Der Kilometerstand wurde erfolgreich angegeben", Toast.LENGTH_LONG).show()
                 */ //Toast Nachricht funktioniert irgendwie nicht
            }
        }

        return root
    }
}