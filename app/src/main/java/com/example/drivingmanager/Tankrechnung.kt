package com.example.drivingmanager

import java.io.Serializable
import java.time.LocalDateTime

class Tankrechnung(preis: Double, liter: Double, bild: String) : Serializable {
    var preis: Double = preis
    var liter: Double = liter
    var bild: String = bild
    var datum: LocalDateTime = LocalDateTime.now()
    fun getDatum(): String {
        return datum.dayOfMonth.toString() + "." + datum.month.toString() + "." + datum.year.toString()
    }
}