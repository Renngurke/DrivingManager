package com.example.drivingmanager.ui.kilometerstand

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.drivingmanager.MainActivity
import com.example.drivingmanager.R
import kotlinx.android.synthetic.main.fragment_kmstand.*
import kotlinx.android.synthetic.main.fragment_kmstand.view.*

class KmFragment : Fragment() {

    private lateinit var kmFragmentViewModel: KmViewModel
    private lateinit var root: View

    //enable options menu in this fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    //inflate the menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_kmstand_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    //handle item clicks of menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.savekm) {
            var error = false

            if (add_kmstand.text.toString().isNullOrEmpty()) {
                add_kmstand.setError("Sie haben keine Kilometeranzahl eingegeben")
                error = true
            }
            if (add_kmstadt.text.toString().isNullOrEmpty()) {
                add_kmstadt.setError("Sie haben keine Kilometeranzahl eingegeben")
                error = true
            }
            if (add_kmautobahn.text.toString().isNullOrEmpty()) {
                add_kmautobahn.setError("Sie haben keine Kilometeranzahl eingegeben")
                error = true
            }
            if (add_kmhybrid.text.toString().isNullOrEmpty()) {
                add_kmhybrid.setError("Sie haben keine Kilometeranzahl eingegeben")
                error = true
            }

            if (!error) {

                var kmStadt = add_kmstadt.text.toString().toInt()
                var kmAutobahn = add_kmautobahn.text.toString().toInt()
                var kmHybrid = add_kmhybrid.text.toString().toInt()
                var kmStand = add_kmstand.text.toString().toInt()

                var car = MainActivity.cl.cars[MainActivity.cl.index]

                if ((kmStadt + kmAutobahn + kmHybrid) < (kmStand - car.gesKM)) {
                    val err = "Kilometeranzahl zu niedrig"
                    add_kmstadt.setError(err)
                } else {

                    car.gesKM = kmStand
                    car.kmStand_angeben(kmStadt, kmAutobahn, kmHybrid)
                    MainActivity.cl.save(this.context!!)

                    add_kmstadt.text = null
                    add_kmautobahn.text = null
                    add_kmhybrid.text = null
                    add_kmstand.text = null

                    kmstand_vorher.text = String.format(
                        "Momentaner Kilometerstand: %d",
                        MainActivity.cl.cars[MainActivity.cl.index].gesKM
                    )
                    Toast.makeText(
                        this.context,
                        "Der Kilometerstand wurde erfolgreich angegeben",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        kmFragmentViewModel =
            ViewModelProviders.of(this).get(KmViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_kmstand, container, false)

        val index = MainActivity.cl.index
        root.kmstand_vorher.text =
            String.format("Momentaner Kilometerstand: %d", MainActivity.cl.cars[index].gesKM)

        root.add_kmstand.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (add_kmstand.text.toString().isNullOrEmpty()) {
                    km_diff.text = null
                } else if (add_kmstand.text.toString().toInt() <= MainActivity.cl.cars[index].gesKM) {
                    add_kmstand.setError("Neuer Kilometerstand ist kleiner als der bisherige")
                } else {
                    km_diff.text = String.format(
                        "%d",
                        (add_kmstand.text.toString().toInt() - MainActivity.cl.cars[index].gesKM)
                    )
                }
            }
        })

        return root
    }
}