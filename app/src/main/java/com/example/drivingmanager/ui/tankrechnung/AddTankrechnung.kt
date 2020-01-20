package com.example.drivingmanager.ui.tankrechnung

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.drivingmanager.MainActivity
import com.example.drivingmanager.R
import kotlinx.android.synthetic.main.activity_add_car.*
import kotlinx.android.synthetic.main.content_add_tankrechnung.*
import java.io.File
import java.io.IOException
import java.util.*

class AddTankrechnung : AppCompatActivity() {

    val REQUEST_TAKE_PHOTO = 1
    var currentPhotoPath = ""

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }


    private fun dispatchTakePictureIntent() {
        //if (!this.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY) {

        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    //...
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.example.drivingmanager.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)

                    val btn = findViewById(R.id.buttonFoto) as ImageButton
                    btn.imageTintList = getColorStateList(R.color.colorPrimary)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tankrechnung)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        toolbar.setNavigationOnClickListener { v: View? -> onBackPressed() }
        setSupportActionBar(toolbar)

        this.setTitle(
            String.format(
                "%s %s",
                MainActivity.cl.cars[MainActivity.cl.index].marke,
                MainActivity.cl.cars[MainActivity.cl.index].modell
            )
        )

        buttonFoto.setOnClickListener {
            dispatchTakePictureIntent()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_car_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if (item.itemId == R.id.save) {
            var error = false

            if (add_tank_eu.text.isNullOrEmpty()) {
                add_tank_eu.setError("Geben Sie den Preis ein")
                error = true
            }

            if (add_tank_l.text.isNullOrEmpty()) {
                add_tank_l.setError("Geben Sie die Liter an")
                error = true
            }

            if (add_tank_km.text.isNullOrEmpty()) {
                add_tank_km.setError("Geben Sie den Kilometerstand an")
                error = true
            } else if (add_tank_km.text.toString().toInt() < MainActivity.cl.cars[MainActivity.cl.index].gesKM) {
                add_tank_km.setError("Eingegebener Kilometerstand niedriger als momentaner Kilomestand")
                error = true
            }

            if (!(error)) {

                val liter = add_tank_l.text.toString().toDouble()
                val preis = add_tank_eu.text.toString().toDouble()
                val km = add_tank_km.text.toString().toInt()

                MainActivity.cl.cars[MainActivity.cl.index].tr_eingabe(
                    liter,
                    preis,
                    km,
                    currentPhotoPath
                )

                MainActivity.cl.save(this)

                onBackPressed()
            }
        } else {
            onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }
}
