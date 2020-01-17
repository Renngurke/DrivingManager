package com.example.drivingmanager.ui.kilometerstand

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.drivingmanager.Car
import com.example.drivingmanager.MainActivity
import com.example.drivingmanager.R
import kotlinx.android.synthetic.main.activity_add_car.*
import kotlinx.android.synthetic.main.content_add_car.*
import kotlinx.android.synthetic.main.fragment_kmstand.*

class KmStandActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_kmstand)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        toolbar.setNavigationOnClickListener { v: View? -> onBackPressed() }
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_kmstand_menu, menu)
        return true
    }

    override fun onResume() {
        super.onResume()

        var index: Int = MainActivity.cl.index
        var kmdifferenz = add_kmstand.text.toString().toInt() - MainActivity.cl.cars[index].gesKM
        km_diff.setText(kmdifferenz)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if(item.itemId == R.id.savekm) {

            var kmStadt = add_kmstadt.text.toString().toInt()
            var kmAutobahn = add_kmautobahn.text.toString().toInt()
            var kmHybrid = add_kmhybrid.text.toString().toInt()

            var car = MainActivity.cl.cars[MainActivity.cl.index]

            car.kmStand_angeben(kmStadt, kmAutobahn, kmHybrid)

            MainActivity.cl.save(this)
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}