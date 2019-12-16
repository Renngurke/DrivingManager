package com.example.drivingmanager

import android.content.Context
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable


class CarList(context: Context) : Serializable {
    private val context: Context = context
    private var cars: MutableList<Car> = ArrayList()

    fun initialize() {
        val filey = context.getFileStreamPath("CarsList.DrivingManager")
        var exists = filey.exists()
        if (exists) {
            return
        } else {
            val fos = context.openFileOutput("CarsList.DrivingManager", Context.MODE_PRIVATE)
            val oos = ObjectOutputStream(fos)
            oos.writeObject(this)
            oos.close()
            fos.close()
        }
    }

    fun save() {
        val file = "CarsList.DrivingManager"
        val data = this

        /*val os = ObjectOutputStream(FileOutputStream(file))
        os.writeObject(data)
        os.close()*/

        val fos = context.openFileOutput(file, Context.MODE_PRIVATE)
        val oos = ObjectOutputStream(fos)
        oos.writeObject(data)
        oos.close()
        fos.close()
    }

    fun load() {
        val file = "CarsList.DrivingManager"

        /*val iStream = ObjectInputStream(FileInputStream(file))
        var data : CarList = iStream.readObject() as CarList
        this.cars = data.cars
        iStream.close()*/

        val fis = context.openFileInput(file)
        val ois = ObjectInputStream(fis)
        var data: CarList = ois.readObject() as CarList
        ois.close()
        fis.close()
        this.cars = data.cars
    }
}