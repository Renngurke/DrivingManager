package com.example.drivingmanager

import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_add_car.*
import kotlinx.android.synthetic.main.content_show_photo.*
import java.io.File

class ShowPhoto : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_photo)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        toolbar.setNavigationOnClickListener { v: View? -> onBackPressed() }
        setSupportActionBar(toolbar)

        val foo: String = intent.getStringExtra("Datei")
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val datei = File(storageDir, foo)

        val photoURI =
            FileProvider.getUriForFile(this, "com.example.drivingmanager.fileprovider", datei)

        tankrechnungFoto.setImageURI(photoURI)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_car_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        onBackPressed()

        return super.onOptionsItemSelected(item)
    }
}
