package com.example.drivingmanager

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_car.*

class CarActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration



    override fun onCreate(savedInstanceState: Bundle?) {
        val index: Int = MainActivity.cl.index

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_km, R.id.nav_statistic, R.id.nav_cost,
                R.id.nav_receipt, R.id.nav_back
            ), drawerLayout
        )
        toolbar.setupWithNavController(navController,drawerLayout)
        navView.setupWithNavController(navController)


        val header: View = navView.getHeaderView(0)

        val model = header.findViewById<TextView>(R.id.nav_mod)
        val marke = header.findViewById<TextView>(R.id.nav_brand)

        model.text = MainActivity.cl.cars[index].modell
        marke.text = MainActivity.cl.cars[index].marke


        nav_back.setOnClickListener(View.OnClickListener { v: View? ->
            onBackPressed()
            try {
                onBackPressed()
            }catch (e: Exception){}
        })
    }
    //test blub
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.car, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration)
    }
}
