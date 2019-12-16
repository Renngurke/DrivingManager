package com.example.drivingmanager


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
) : java.io.Serializable {
    // private?
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
    val selected: Boolean =
        selected // theoretisch unnoetig, aber CarListAdapter muss dann geaendert werden
    //private var dg_stadt_km: ArrayMap<Date, Int> = ArrayMap()
    //private var dg_stadt_l: ArrayMap<Date, Double> = ArrayMap()

    fun verbDurchschnitt(): Double {
        return ((verbStadt + verbAutobahn + verbHybrid) / 3)
    }

    fun uebrigeKM(): Int {
        return (((tankstand * tankVol) / verbDurchschnitt()) * 100).toInt()
    }

    fun kmStand_angeben(kmStadt: Int, kmAutobahn: Int, kmHybrid: Int) {
        tankstand -= (verbStadt * (kmStadt.toDouble() / 100.toDouble()) + verbAutobahn * (kmAutobahn.toDouble() / 100.toDouble()) + verbHybrid * (kmHybrid.toDouble() / 100.toDouble())) / tankVol.toDouble()
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
        gesKM += km
        gesCo2Aus += km * co2Aus
        tankstand += liter / tankVol
        if (tankstand > 1.00) tankstand = 1.00
        literkosten = ((preis / liter) + literkosten) / 2.0
        var verbDif = verbDurchschnitt() - (liter / (km.toDouble() / 100.toDouble()))
        verbStadt += verbDif
        verbAutobahn += verbDif
        verbHybrid += verbDif
        tr_add(preis, liter, bild)
    }
    /*fun dg_wert_eintragen_stadt(km: Int, l: Double) {

        var time = GregorianCalendar(TimeZone.getDefault())
        var myDate = GregorianCalendar(Calendar.YEAR,Calendar.MONTH,Calendar.DAY_OF_MONTH)
        myDate=time
    }*/

}