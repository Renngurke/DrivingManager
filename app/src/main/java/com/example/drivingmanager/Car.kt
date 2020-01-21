package com.example.drivingmanager

import java.io.Serializable
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set


class Car(
    marke: String,
    modell: String,
    gesKM: Int,
    tankVol: Int,
    verbStadt: Double,
    verbAutobahn: Double,
    verbHybrid: Double,
    co2Aus: Int,
    selected: Boolean
) : Serializable {
    val marke: String = marke
    val modell: String = modell
    var gesKM: Int = gesKM
    val tankVol: Int = tankVol
    var verbStadt: Double = verbStadt
    var verbAutobahn: Double = verbAutobahn
    var verbHybrid: Double = verbHybrid
    val co2Aus: Int = co2Aus
    var gesCo2Aus: Int = 0  //in gramm
    var tankstand: Double = 1.000
    var literkosten: Double = 1.369
    val tankrechnungen: MutableList<Tankrechnung> = ArrayList()
    var selected: Boolean = selected
    var my_getankt = true
    var my_getanktTankstand = tankstand
    var my_getanktKMStadt = 0
    var my_getankKMAutobahn = 0
    var my_getanktKMHybrid = 0

    var dg_stadt_km_l_g: HashMap<LocalDate, FloatArray> = HashMap()
    var dg_autobahn_km_l_g: HashMap<LocalDate, FloatArray> = HashMap()
    var dg_hybrid_km_l_g: HashMap<LocalDate, FloatArray> = HashMap()
    var dg_durch_km_l_g: HashMap<LocalDate, FloatArray> = HashMap()

    fun verbDurchschnitt(): Double {
        return ((verbStadt + verbAutobahn + verbHybrid) / 3)
    }

    fun uebrigeKM(): Int {
        return (((tankstand * tankVol) / verbDurchschnitt()) * 100).toInt()
    }

    fun kmStand_angeben(kmStadt: Int, kmAutobahn: Int, kmHybrid: Int) {
        my_getankt = false
        my_getanktKMStadt += kmStadt
        my_getankKMAutobahn += kmAutobahn
        my_getanktKMHybrid += kmHybrid

        tankstand -= (verbStadt * (kmStadt.toDouble() / 100.toDouble()) + verbAutobahn * (kmAutobahn.toDouble() / 100.toDouble()) + verbHybrid * (kmHybrid.toDouble() / 100.toDouble())) / tankVol.toDouble()

        dg_stadt_add(kmStadt.toFloat(), (kmStadt.toDouble() / 100.toDouble()) * verbStadt)
        dg_autobahn_add(
            kmAutobahn.toFloat(),
            (kmAutobahn.toDouble() / 100.toDouble()) * verbAutobahn
        )
        dg_hybrid_add(kmHybrid.toFloat(), (kmHybrid.toDouble() / 100.toDouble()) * verbHybrid)
        dg_durch_update()
    }

    fun rkv_kosten(km: Int): Double {
        return (km.toDouble() / 100.toDouble()) * verbDurchschnitt() * literkosten
    }

    fun rkv_anzStops(km: Int): Int {
        var stops = 0
        var temp = km - uebrigeKM()
        while (temp > 0) {
            stops++
            temp -= ((tankVol / verbDurchschnitt()) * 100.0).toInt()
        }
        return stops
    }

    fun rkv_co2(km: Int): Int {
        return km * co2Aus
    }

    fun tr_add(preis: Double, liter: Double, bild: String) {
        val tr = Tankrechnung(preis, liter, bild)
        tankrechnungen.add(tr)
    }

    fun tr_eingabe(liter: Double, preis: Double, km: Int, bild: String) {
        var strecke = km - gesKM        // 40
        gesKM = km
        gesCo2Aus = km * co2Aus
        // aktualisieren des Tankstands für die gefahrenen km
        tankstand -= (verbStadt * (strecke.toDouble() / 3 / 100.toDouble()) + verbAutobahn * (strecke.toDouble() / 3 / 100.toDouble()) + verbHybrid * (strecke.toDouble() / 3 / 100.toDouble())) / tankVol.toDouble()
        tankstand += liter / tankVol
        if (tankstand > 1.00) tankstand = 1.00
        literkosten = ((preis / liter) + literkosten) / 2.0
        dg_stadt_add((strecke.toFloat() / 3), (strecke * verbStadt / 100) / 3)
        dg_autobahn_add((strecke.toFloat() / 3), (strecke * verbAutobahn / 100) / 3)
        dg_hybrid_add((strecke.toFloat() / 3), (strecke * verbHybrid / 100) / 3)
        dg_durch_update()
        tr_add(preis, liter, bild)
    }

    fun dg_stadt_add(km: Float, liter: Double) {
        if(dg_stadt_km_l_g.containsKey(LocalDate.now())) {
            var km_neu = dg_stadt_km_l_g.getValue(LocalDate.now()).elementAt(0)+km.toFloat()
            var l_neu = dg_stadt_km_l_g.getValue(LocalDate.now()).elementAt(1)+liter.toFloat()
            var g_neu =
                dg_stadt_km_l_g.getValue(LocalDate.now()).elementAt(2) + (km * co2Aus.toFloat()).toFloat()
            var updated_values = FloatArray(3)
            updated_values[0]=km_neu
            updated_values[1]=l_neu
            updated_values[2]=g_neu
            dg_stadt_km_l_g.set(LocalDate.now(), updated_values)
        }
        else {
            var new_values = FloatArray(3)
            new_values[0]=km.toFloat()
            new_values[1]=liter.toFloat()
            new_values[2] = (km * co2Aus.toFloat()).toFloat()
            dg_stadt_km_l_g.set(LocalDate.now(), new_values)
        }
    }

    fun dg_autobahn_add(km: Float, liter: Double) {
        if(dg_autobahn_km_l_g.containsKey(LocalDate.now())) {
            var km_neu = dg_autobahn_km_l_g.getValue(LocalDate.now()).elementAt(0)+km.toFloat()
            var l_neu = dg_autobahn_km_l_g.getValue(LocalDate.now()).elementAt(1)+liter.toFloat()
            var g_neu =
                dg_autobahn_km_l_g.getValue(LocalDate.now()).elementAt(2) + (km * co2Aus.toFloat()).toFloat()
            var updated_values = FloatArray(3)
            updated_values[0]=km_neu
            updated_values[1]=l_neu
            updated_values[2]=g_neu
            dg_autobahn_km_l_g.set(LocalDate.now(), updated_values)
        }
        else {
            var new_values = FloatArray(3)
            new_values[0]=km.toFloat()
            new_values[1]=liter.toFloat()
            new_values[2] = (km * co2Aus.toFloat()).toFloat()
            dg_autobahn_km_l_g.set(LocalDate.now(), new_values)
        }
    }

    fun dg_hybrid_add(km: Float, liter: Double) {
        if(dg_hybrid_km_l_g.containsKey(LocalDate.now())) {
            var km_neu = dg_hybrid_km_l_g.getValue(LocalDate.now()).elementAt(0)+km.toFloat()
            var l_neu = dg_hybrid_km_l_g.getValue(LocalDate.now()).elementAt(1)+liter.toFloat()
            var g_neu =
                dg_hybrid_km_l_g.getValue(LocalDate.now()).elementAt(2) + (km * co2Aus.toFloat()).toFloat()
            var updated_values = FloatArray(3)
            updated_values[0]=km_neu
            updated_values[1]=l_neu
            updated_values[2]=g_neu
            dg_hybrid_km_l_g[LocalDate.now()] = updated_values
        }
        else {
            var new_values = FloatArray(3)
            new_values[0]=km.toFloat()
            new_values[1]=liter.toFloat()
            new_values[2] = (km * co2Aus.toFloat()).toFloat()
            dg_hybrid_km_l_g.set(LocalDate.now(), new_values)
        }
    }
    fun dg_durch_update() {
        var km_durch =
            (dg_stadt_km_l_g.getValue(LocalDate.now()).elementAt(0) + dg_autobahn_km_l_g.getValue(
                LocalDate.now()
            ).elementAt(0) + dg_hybrid_km_l_g.getValue(LocalDate.now()).elementAt(0))
        var l_durch =
            (dg_stadt_km_l_g.getValue(LocalDate.now()).elementAt(1) + dg_autobahn_km_l_g.getValue(
                LocalDate.now()
            ).elementAt(1) + dg_hybrid_km_l_g.getValue(LocalDate.now()).elementAt(1))
        var g_durch = km_durch * co2Aus.toFloat()
        var durch_values = FloatArray(3)
        durch_values[0] = km_durch
        durch_values[1] = l_durch
        durch_values[2] = g_durch
        dg_durch_km_l_g.set(LocalDate.now(), durch_values)
    }
}