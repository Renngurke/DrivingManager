package com.example.drivingmanagerdoubletabs

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.example.drivingmanager.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import java.time.LocalDate
import java.util.ArrayList
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.random.Random

class ViewMonatA : Fragment() {

// Übernehmen der HaspMap aus der Klasse
//---------------------------------------------------------------------------------------------------------------------------------------------

    // Anhand von Klasse abrufen
    val co2_value  = 190.4f

    // !!!HashMap!!! zum Verbrauch wird mir ebenfalls von der Klasse angegeben | Durchschnitt liegt bei 8L/100km
    val verbrauch_1km = 0.08f

    // Anhand von Klasse aufrufen
    private fun ListeMap(): HashMap<LocalDate,Float> {

        val myList: HashMap<LocalDate, Float>
        myList = HashMap<LocalDate, Float>()

        for (i in 0..30) {
            myList.put(LocalDate.of(2019,12,i+1), Random.nextInt(20).toFloat())
        }

        return myList
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------
    lateinit var viewA : View
    lateinit var mpLineChart: LineChart

    // Konstruktor -- automatisch ???

    @Nullable
    override fun onCreateView(inflater: LayoutInflater,  @Nullable container: ViewGroup?,  @Nullable savedInstanceState: Bundle?): View {

        viewA = inflater.inflate(R.layout.view_durchschnitt_monat,container,false)

        var titel: TextView
        titel = viewA.findViewById(R.id.text_statistik_typ)
        titel.setText("Autobahn")

        var letzterTag = LocalDate.now().toString()
        var ersterTag = LocalDate.now().minusDays(30).toString()
        var datum: TextView
        datum = viewA.findViewById(R.id.text_zeitraum)
        datum.setText(ersterTag + "  bis  " + letzterTag)

        var streckeGesamt = GefahreneStrecke()
        var streckeG: TextView
        streckeG = viewA.findViewById(R.id.text_gefahrene_strecke)
        streckeG.setText("Gefahrene Strecke: \t \t \t \t \t \t " + streckeGesamt + " km")


        var streckeVerbrauch = VerbrauchStrecke()
        var streckeV: TextView
        streckeV = viewA.findViewById(R.id.text_kraftstoffverbrauch)
        streckeV.setText("Kraftstoffverbrauch: \t \t \t \t \t \t " + streckeVerbrauch + " l")


        var streckeCo2 = Co2Strecke()
        var streckeC: TextView
        streckeC = viewA.findViewById(R.id.text_co2_ausstoss)
        streckeC.setText("CO2-Ausstoss: \t \t \t \t \t \t " + streckeCo2 + " g/km")





        mpLineChart = viewA.findViewById(R.id.line_chartd) as LineChart

        val km = LineDataSet(KmValuesMonth(), "km")
        km.setColor(Color.GREEN)
        km.setLineWidth(2f)

        val co2 = LineDataSet(Co2ValuesMonth(), "kg")
        co2.setColor(Color.MAGENTA)
        co2.setLineWidth(2f)

        val verbrauch = LineDataSet(VerbrauchValuesMonth(), "l")
        verbrauch.setColor(Color.GRAY)
        verbrauch.setLineWidth(2f)
        verbrauch.setDrawValues(false)

        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(km)
        dataSets.add(co2)
        dataSets.add(verbrauch)

        val data = LineData(dataSets)
        mpLineChart.data = data

        mpLineChart.getDescription().setText("Woche")
        mpLineChart.getDescription().setTextSize(12f)
        mpLineChart.getDescription().setPosition(950f,900f)
        //mpLineChart.getXAxis().setValueFormatter(IndexAxisValueFormatter(XMonate()))
        //mpLineChart.getXAxis().setValueFormatter(IndexAxisValueFormatter(XTage()))
        mpLineChart.getXAxis().setValueFormatter(IndexAxisValueFormatter(XWoche()))
        mpLineChart.axisLeft.axisMinimum = 0f
        mpLineChart.axisRight.axisMinimum = 0f

        mpLineChart.invalidate()
        return viewA
    }


    private fun GefahreneStrecke(): Double {

        val myList: HashMap<LocalDate, Float>
        myList = ListeMap()

        var strecke = 0.0
        var date = LocalDate.now()

        for (i in 0..30) {

            if (myList.containsKey(date)) {
                strecke += myList.getValue(date)
            } else {

            }

            date = date.minusDays(1)
        }

        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING

        return df.format(strecke).toDouble()
    }

    private fun VerbrauchStrecke(): Double {

        val myList: HashMap<LocalDate, Float>
        myList = ListeMap()

        var verbrauch = 0.0
        var date = LocalDate.now()

        for (i in 0..30) {

            if (myList.containsKey(date)) {
                verbrauch += (myList.getValue(date)*verbrauch_1km)
            } else {

            }

            date = date.minusDays(1)
        }

        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING

        return df.format(verbrauch).toDouble()
    }

    private fun Co2Strecke(): Double {

        val myList: HashMap<LocalDate, Float>
        myList = ListeMap()

        var co2 = 0.0
        var date = LocalDate.now()

        for (i in 0..30) {

            if (myList.containsKey(date)) {
                co2 += (myList.getValue(date)*co2_value)
            } else {

            }

            date = date.minusDays(1)
        }

        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING

        return df.format(co2).toDouble()
    }

    // Beschriftung der X-Achse
// ---------------------------------------------------------------------------------------------
    fun XWoche(): ArrayList<String> {

        val xAxisLabel = ArrayList<String>()

        var counter = 1
        for(i in 0..4) {

            xAxisLabel.add(counter.toString())
            counter++
        }

        return xAxisLabel

    }

    // Verarbeitung der darzustellenden Daten -> Monat
// ---------------------------------------------------------------------------------------------

    private fun KmValuesMonth(): ArrayList<Entry> {

        val myList: HashMap<LocalDate,Float>
        myList = ListeMap()

        val dataVals = ArrayList<Entry>()
        var counter = 0

        var day = LocalDate.now().minusDays(30)

        while(counter < 4) {

            var value = 0f

            for(i in 0..6) {

                if(myList.containsKey(day)) {
                    value += myList.getValue(day)
                }

                else {
                    value += 0f
                }

                day = day.plusDays(1)
            }

            dataVals.add(Entry(counter.toFloat(), value))
            counter++

            if(counter == 4){

                value = 0f

                for(i in 0..2) {

                    if(myList.containsKey(day)) {
                        value += myList.getValue(day)
                    }

                    else {
                        value += 0f
                    }

                    day = day.plusDays(1)
                }

                dataVals.add(Entry(counter.toFloat(), value))

            }
        }


        return dataVals
    }

    private fun Co2ValuesMonth(): ArrayList<Entry> {

        // g in kg umwandeln, da sonst die Werte zu hoch sind
        val co2_kg  = (co2_value/1000)

        val myList: HashMap<LocalDate,Float>
        myList = ListeMap()

        val dataVals = ArrayList<Entry>()
        var counter = 0

        var day = LocalDate.now().minusDays(30)

        while(counter < 4) {

            var value = 0f

            for(i in 0..6) {

                if(myList.containsKey(day)) {
                    value += (myList.getValue(day)*co2_kg)
                }

                else {
                    value += 0f
                }

                day = day.plusDays(1)
            }

            dataVals.add(Entry(counter.toFloat(), value))
            counter++

            if(counter == 4){

                value = 0f

                for(i in 0..2) {

                    if(myList.containsKey(day)) {
                        value += (myList.getValue(day)*co2_kg)
                    }

                    else {
                        value += 0f
                    }

                    day = day.plusDays(1)
                }

                dataVals.add(Entry(counter.toFloat(), value))

            }
        }


        return dataVals
    }

    private fun VerbrauchValuesMonth(): ArrayList<Entry> {

        val myList: HashMap<LocalDate,Float>
        myList = ListeMap()

        val dataVals = ArrayList<Entry>()
        var counter = 0

        var day = LocalDate.now().minusDays(30)

        while(counter < 4) {

            var value = 0f

            for(i in 0..6) {

                if(myList.containsKey(day)) {
                    value += (myList.getValue(day)*verbrauch_1km)
                }

                else {
                    value += 0f
                }

                day = day.plusDays(1)
            }

            dataVals.add(Entry(counter.toFloat(), value))
            counter++

            if(counter == 4){

                value = 0f

                for(i in 0..2) {

                    if(myList.containsKey(day)) {
                        value += (myList.getValue(day)*verbrauch_1km)
                    }

                    else {
                        value += 0f
                    }

                    day = day.plusDays(1)
                }

                dataVals.add(Entry(counter.toFloat(), value))

            }
        }


        return dataVals
    }

}