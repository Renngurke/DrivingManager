package com.example.drivingmanagerdoubletabs

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.example.drivingmanager.MainActivity
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

class ViewWocheS : Fragment() {

// Übernehmen der HaspMap aus der Klasse
//---------------------------------------------------------------------------------------------------------------------------------------------

    val index = MainActivity.cl.index;

    private fun ListeMap(): HashMap<LocalDate,FloatArray> {

        val myList: HashMap<LocalDate, FloatArray>
        myList = MainActivity.cl.cars[index].dg_autobahn_km_l_g

        return myList
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------
    lateinit var viewA : View
    lateinit var mpLineChart: LineChart

    // Konstruktor -- automatisch ???

    @Nullable
    override fun onCreateView(inflater: LayoutInflater,  @Nullable container: ViewGroup?,  @Nullable savedInstanceState: Bundle?): View {

        viewA = inflater.inflate(R.layout.view_durchschnitt_woche,container,false)

        var titel: TextView
        titel = viewA.findViewById(R.id.text_statistik_typ)
        titel.setText("Stadt")

        var letzterTag = LocalDate.now().toString()
        var ersterTag = LocalDate.now().minusDays(6).toString()
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

        val km = LineDataSet(KmValuesWeek(), "km")
        km.setColor(Color.GREEN)
        km.setLineWidth(2f)
        km.setDrawValues(false)

        val co2 = LineDataSet(Co2ValuesWeek(), "kg")
        co2.setColor(Color.MAGENTA)
        co2.setLineWidth(2f)
        co2.setDrawValues(false)

        val verbrauch = LineDataSet(VerbrauchValuesWeek(), "l")
        verbrauch.setColor(Color.GRAY)
        verbrauch.setLineWidth(2f)
        verbrauch.setDrawValues(false)

        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(km)
        dataSets.add(co2)
        dataSets.add(verbrauch)

        val data = LineData(dataSets)
        mpLineChart.data = data

        mpLineChart.getDescription().setText("Tag")
        mpLineChart.getDescription().setTextSize(12f)
        mpLineChart.getDescription().setPosition(950f,900f)
        //mpLineChart.getXAxis().setValueFormatter(IndexAxisValueFormatter(XMonate()))
        //mpLineChart.getXAxis().setValueFormatter(IndexAxisValueFormatter(XTage()))
        mpLineChart.getXAxis().setValueFormatter(IndexAxisValueFormatter(XTage()))
        mpLineChart.axisLeft.axisMinimum = 0f
        mpLineChart.axisRight.axisMinimum = 0f

        mpLineChart.invalidate()
        return viewA
    }


    private fun GefahreneStrecke(): Double {

        val myList: HashMap<LocalDate, FloatArray>
        myList = ListeMap()

        var strecke = 0.0
        var date = LocalDate.now()

        for (i in 0..6) {

            if (myList.containsKey(date)) {
                strecke += myList.getValue(date)[0]
            } else {

            }

            date = date.minusDays(1)
        }

        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING

        return df.format(strecke).toDouble()
    }

    private fun VerbrauchStrecke(): Double {

        val myList: HashMap<LocalDate, FloatArray>
        myList = ListeMap()

        var verbrauch = 0.0
        var date = LocalDate.now()

        for (i in 0..6) {

            if (myList.containsKey(date)) {
                verbrauch += myList.getValue(date)[1]
            } else {

            }

            date = date.minusDays(1)
        }

        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING

        return df.format(verbrauch).toDouble()
    }

    private fun Co2Strecke(): Double {

        val myList: HashMap<LocalDate, FloatArray>
        myList = ListeMap()

        var co2 = 0.0
        var date = LocalDate.now()

        for (i in 0..6) {

            if (myList.containsKey(date)) {
                co2 += myList.getValue(date)[3]
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
    fun XTage(): ArrayList<String> {

        val xAxisLabel = ArrayList<String>()

        for(i in 6 downTo 0) {

            val day = LocalDate.now().minusDays(i.toLong()).getDayOfWeek()
            val day_string = day.toString()

            var firstl : String
            firstl = ""
            for(l in 0..2) {
                firstl += day_string[l]
            }

            xAxisLabel.add(firstl)
        }

        return xAxisLabel

    }

    // Verarbeitung der darzustellenden Daten -> Woche
// ---------------------------------------------------------------------------------------------

    private fun KmValuesWeek(): ArrayList<Entry> {

        val myList: HashMap<LocalDate,FloatArray>
        myList = ListeMap()

        val dataVals = ArrayList<Entry>()

        var reihenfolge = 6
        var date = LocalDate.now().minusDays(reihenfolge.toLong())

        for (i in 0..6) {

            // Falls der Benutzer die Statistik aufrufen möchte, obwohl noch nicht gefahren wurde
            if(myList.containsKey(date)) {
                dataVals.add(Entry(i.toFloat(), myList.getValue(date)[0]))
            }

            else {
                dataVals.add(Entry(i.toFloat(), 0f))
            }

            reihenfolge--
            date = LocalDate.now().minusDays(reihenfolge.toLong())
        }

        return dataVals
    }

    private fun Co2ValuesWeek(): ArrayList<Entry> {

        val myList: HashMap<LocalDate,FloatArray>
        myList = ListeMap()

        val dataVals = ArrayList<Entry>()

        var reihenfolge = 6
        var date = LocalDate.now().minusDays(reihenfolge.toLong())

        for (i in 0..6) {

            // Falls der Benutzer die Statistik aufrufen möchte, obwohl noch nicht gefahren wurde
            if(myList.containsKey(date)) {
                dataVals.add(Entry(i.toFloat(), myList.getValue(date)[3]))
            }

            else {
                dataVals.add(Entry(i.toFloat(), 0f))
            }

            reihenfolge--
            date =LocalDate.now().minusDays(reihenfolge.toLong())
        }

        return dataVals
    }

    private fun VerbrauchValuesWeek(): ArrayList<Entry> {

        val myList: HashMap<LocalDate,FloatArray>
        myList = ListeMap()

        val dataVals = ArrayList<Entry>()

        var reihenfolge = 6
        var date = LocalDate.now().minusDays(reihenfolge.toLong())

        for (i in 0..6) {

            // Falls der Benutzer die Statistik aufrufen möchte, obwohl noch nicht gefahren wurde
            if(myList.containsKey(date)) {
                dataVals.add(Entry(i.toFloat(), myList.getValue(date)[1]))
            }

            else {
                dataVals.add(Entry(i.toFloat(), 0f))
            }

            reihenfolge--
            date = LocalDate.now().minusDays(reihenfolge.toLong())
        }

        return dataVals
    }


}