package com.example.drivingmanager

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_car.*
import kotlinx.android.synthetic.main.content_add_car.*

class AddCarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_car)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        toolbar.setNavigationOnClickListener { v: View? -> onBackPressed() }
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_car_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if(item.itemId == R.id.save){
            var error = false

            if (add_brand.text.isNullOrEmpty()) {
                add_brand.setError("Geben Sie die Automarke an")
                error = true
            }

            if (add_mod.text.isNullOrEmpty()) {
                add_mod.setError("Geben Sie das Modell an")
                error = true
            }

            if (add_kmCounter.text.isNullOrEmpty()) {
                add_kmCounter.setError("Geben Sie den Kilometerstand an")
                error = true
            }

            if (add_volume.text.isNullOrEmpty()) {
                add_volume.setError("Geben Sie das Tankvolumen an")
                error = true
            }

            if (add_city.text.isNullOrEmpty()) {
                add_city.setError("Geben Sie den Stadtverbrauch an")
                error = true
            }

            if (add_autobahn.text.isNullOrEmpty()) {
                add_autobahn.setError("Geben Sie den Autobahnverbrauch an")
                error = true
            }

            if (add_hybrid.text.isNullOrEmpty()) {
                add_hybrid.setError("Geben Sie den Hybridverbrauch an")
                error = true
            }

            if (add_co2.text.isNullOrEmpty()) {
                add_co2.setError("Geben Sie den Co2 Ausstoss an")
                error = true
            }

            if (!error) {
                var c: Car = Car(
                    add_brand.text.toString(),
                    add_mod.text.toString(),
                    add_kmCounter.text.toString().toInt(),
                    add_volume.text.toString().toInt(),
                    add_city.text.toString().toDouble(),
                    add_autobahn.text.toString().toDouble(),
                    add_hybrid.text.toString().toDouble(),
                    add_co2.text.toString().toInt(),
                    false
                )
                MainActivity.cl.cars.add(c)
                MainActivity.cl.save(this)
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
