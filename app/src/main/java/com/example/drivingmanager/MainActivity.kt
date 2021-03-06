package com.example.drivingmanager

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
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
            Toast.makeText(this, R.string.car_added, Toast.LENGTH_LONG).show()
        }
        list.adapter?.notifyDataSetChanged()
        size = cl.cars.size
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
            cl.save(this)
            if (cl.cars.size < size) {
                Toast.makeText(this, "Die Autos wurden erfolgreich gelöscht", Toast.LENGTH_LONG)
                    .show()
            }
            if (cl.cars.size < 1) {
                startText.visibility = View.VISIBLE
                list.visibility = View.GONE
            }
        }
        return when (item.itemId) {
            else -> super.onOptionsItemSelected(item)
        }
    }
}
