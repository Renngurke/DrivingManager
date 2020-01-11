package com.example.drivingmanager

import android.content.Context
import java.io.File
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable


class CarList() : Serializable {
    var cars: MutableList<Car> = ArrayList()

    fun initialize(con: Context) {
        val file = con.getFileStreamPath("CarsList")
        var exists = file.exists()
        if (exists) {
            return
        } else {
            val file = File("CarsList")
            file.setWritable(true)
            file.setReadable(true)
            val fos = con.openFileOutput("CarsList", Context.MODE_PRIVATE)
            val oos = ObjectOutputStream(fos)
            oos.writeObject(this)
            oos.close()
            fos.close()
        }
    }

    fun save(con: Context) {
        val file = File("CarsList")
        file.setWritable(true)
        file.setReadable(true)
        val data = this

        /*val os = ObjectOutputStream(FileOutputStream(file))
        os.writeObject(data)
        os.close()*/

        val fos = con.openFileOutput("CarsList", Context.MODE_PRIVATE)
        val oos = ObjectOutputStream(fos)
        oos.writeObject(data)
        oos.close()
        fos.close()
    }

    fun load(con: Context) {
        val file = File("CarsList")

        /*val iStream = ObjectInputStream(FileInputStream(file))
        var data : CarList = iStream.readObject() as CarList
        this.cars = data.cars
        iStream.close()*/

        val fis = con.openFileInput("CarsList")
        val ois = ObjectInputStream(fis)
        var data: CarList = ois.readObject() as CarList
        this.cars = data.cars
        ois.close()
        fis.close()
    }
}