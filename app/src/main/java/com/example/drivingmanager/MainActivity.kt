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
        lateinit var cl: CarList
    }

    init {
        cl = CarList()
    }

    var size: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        cl.initialize(this)
        cl.load(this)

        addButton.setOnClickListener { view ->
            // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            //  .setAction("Action", null).show()
            size = cl.cars.size
            startActivity(Intent(this, AddCarActivity::class.java))
        }
        cl.cars.add(Car("VW", "Yolo", 10000, 60, 8.0, 12.0, 10.0, 120, false))
        cl.cars.add(Car("ASD2", "ASD", 0, 0, 0.0, 0.0, 0.0, 0, false))


        list.layoutManager = LinearLayoutManager(this)
        list.adapter = CarListAdapter(cl,this)
        if(cl.cars.isEmpty()){
            list.visibility = View.GONE
            startText.visibility = View.VISIBLE
        }else{
            startText.visibility = View.GONE
            list.visibility = View.VISIBLE
        }
        size = cl.cars.size
    }

    override fun onResume() {
        super.onResume()
        if(cl.cars.size > size){
            Snackbar.make(list,R.string.car_added,Snackbar.LENGTH_LONG).show()
        }
        list.adapter?.notifyDataSetChanged()
        if(cl.cars.isEmpty()){
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
