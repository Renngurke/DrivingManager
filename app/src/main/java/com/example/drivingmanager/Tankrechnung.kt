package com.example.drivingmanager

import java.time.LocalDateTime

class Tankrechnung(preis: Double, liter: Double, bild: String) : java.io.Serializable {
    var preis: Double = preis
    var liter: Double = liter
    var bild: String = bild
    var datum: LocalDateTime = LocalDateTime.now()
    fun getDatum(): String {
        return datum.dayOfMonth.toString() + "." + datum.month.toString() + "." + datum.year.toString()
    }
}