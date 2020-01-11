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
            var c: Car = Car(
                add_brand.text.toString(),
                add_mod.text.toString(),
                add_kmCounter.text.toString().toInt(),
                add_autobahn.text.toString().toInt(),
                add_city.text.toString().toDouble(), //?
                add_autobahn.text.toString().toDouble(),
                add_hybrid.text.toString().toDouble(),
                add_kmCounter.text.toString().toInt(), //?
                false
            )
            MainActivity.cl.cars.add(c)
            MainActivity.cl.save(this)
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}
