package com.example.drivingmanager

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var cars: ArrayList<Car>
    }

    init {
        cars = ArrayList()
    }

    var size: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        addButton.setOnClickListener { view ->
            // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            //  .setAction("Action", null).show()
            size = cars.size
            startActivity(Intent(this, AddCarActivity::class.java))
        }
        cars.add(Car("ASD1", "ASD", 0, 0, 0.0, 0.0, 0.0, 0, false))
        cars.add(Car("ASD2", "ASD", 0, 0, 0.0, 0.0, 0.0, 0, false))
//        cars.add(Car("ASD3","ASD",0,0,0f,0f,0f,0f,false))
//        cars.add(Car("ASD4","ASD",0,0,0f,0f,0f,0f,false))
//        cars.add(Car("ASD5","ASD",0,0,0f,0f,0f,0f,false))
//        cars.add(Car("ASD","ASD",0,0,0f,0f,0f,0f,false))
//        cars.add(Car("ASD","ASD",0,0,0f,0f,0f,0f,false))
//        cars.add(Car("ASD","ASD",0,0,0f,0f,0f,0f,false))
//        cars.add(Car("ASD","ASD",0,0,0f,0f,0f,0f,false))
//        cars.add(Car("ASD","ASD",0,0,0f,0f,0f,0f,false))
//        cars.add(Car("ASD","ASD",0,0,0f,0f,0f,0f,false))
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = CarListAdapter(cars,this)
        if(cars.isEmpty()){
            list.visibility = View.GONE
            startText.visibility = View.VISIBLE
        }else{
            startText.visibility = View.GONE
            list.visibility = View.VISIBLE
        }
        size = cars.size
    }

    override fun onResume() {
        super.onResume()
        if(cars.size > size){
            Snackbar.make(list,R.string.car_added,Snackbar.LENGTH_LONG).show()
        }
        list.adapter?.notifyDataSetChanged()
        if(cars.isEmpty()){
            list.visibility = View.GONE
            startText.visibility = View.VISIBLE
        }else{
            startText.visibility = View.GONE
            list.visibility = View.VISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if(item.itemId == R.id.delete){
            var adapter: CarListAdapter = list.adapter as CarListAdapter
            adapter.selectionMode = !adapter.selectionMode;
        }
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
