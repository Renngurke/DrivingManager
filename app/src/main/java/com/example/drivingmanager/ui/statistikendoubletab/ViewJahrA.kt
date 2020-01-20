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
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.time.LocalDate
import java.util.*

class ViewJahrA : Fragment() {

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

    @Nullable
    override fun onCreateView(inflater: LayoutInflater,  @Nullable container: ViewGroup?,  @Nullable savedInstanceState: Bundle?): View {

        viewA = inflater.inflate(R.layout.view_durchschnitt_jahr,container,false)

        var titel: TextView
        titel = viewA.findViewById(R.id.text_statistik_typ)
        titel.setText("Autobahn")

        var letzterTag = LocalDate.now().toString()
        var ersterTag = LocalDate.now().minusDays(364).toString()
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
        streckeC = viewA.findViewById(R.id.text_co2_ausstoß)
        streckeC.setText("CO2-Ausstoß: \t \t \t \t \t \t " + streckeCo2 + " g/km")

        mpLineChart = viewA.findViewById(R.id.line_chartd) as LineChart

        val km = LineDataSet(KmValuesYear(), "km")
        km.setColor(Color.GREEN)
        km.setLineWidth(2f)
        km.setDrawValues(false)

        val co2 = LineDataSet(Co2ValuesYear(), "kg")
        co2.setColor(Color.MAGENTA)
        co2.setLineWidth(2f)
        co2.setDrawValues(false)

        val verbrauch = LineDataSet(VerbrauchValuesYear(), "l")
        verbrauch.setColor(Color.GRAY)
        verbrauch.setLineWidth(2f)
        verbrauch.setDrawValues(false)

        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(km)
        dataSets.add(co2)
        dataSets.add(verbrauch)

        val data = LineData(dataSets)
        mpLineChart.data = data

        mpLineChart.getDescription().setText("Monat")
        mpLineChart.getDescription().setTextSize(12f)
        mpLineChart.getDescription().setPosition(950f,900f)
        mpLineChart.getXAxis().setValueFormatter(IndexAxisValueFormatter(XMonate()))
        //mpLineChart.getXAxis().setValueFormatter(IndexAxisValueFormatter(XTage()))
        //mpLineChart.getXAxis().setValueFormatter(IndexAxisValueFormatter(XWoche()))
        mpLineChart.axisLeft.axisMinimum = 0f
        mpLineChart.axisRight.axisMinimum = 0f

        mpLineChart.invalidate()
        return viewA
    }

    private fun GefahreneStrecke(): Double {

        val myList: HashMap<LocalDate, FloatArray>
        myList = ListeMap()

        var strecke = 0f
        var date = LocalDate.now()

        for (i in 0..255) {

            if (myList.containsKey(date)) {
                strecke += myList.getValue(date)[0]
            } else {

            }

            date = date.minusDays(1)
        }

        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING

        return NumberFormat.getInstance().parse(df.format(strecke)).toDouble()
    }

    private fun VerbrauchStrecke(): Double {

        val myList: HashMap<LocalDate, FloatArray>
        myList = ListeMap()

        var verbrauch = 0.0
        var date = LocalDate.now()

        for (i in 0..255) {

            if (myList.containsKey(date)) {
                verbrauch += myList.getValue(date)[1]
            } else {

            }

            date = date.minusDays(1)
        }

        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING

        return NumberFormat.getInstance().parse(df.format(verbrauch)).toDouble()
    }

    private fun Co2Strecke(): Double {

        val myList: HashMap<LocalDate, FloatArray>
        myList = ListeMap()

        var co2 = 0.0
        var date = LocalDate.now()

        for (i in 0..255) {

            if (myList.containsKey(date)) {
                co2 += myList.getValue(date)[2] / 1000
            } else {

            }

            date = date.minusDays(1)
        }

        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING

        return NumberFormat.getInstance().parse(df.format(co2)).toDouble()
    }

// Beschriftung der X-Achse
// ---------------------------------------------------------------------------------------------
    fun XMonate(): ArrayList<String> {

        val xAxisLabel = ArrayList<String>()

        for(i in 11 downTo 0) {

            val month = LocalDate.now().minusMonths(i.toLong()).getMonth()
            val month_string = month.toString()

            var firstl : String
            firstl = ""
            for(l in 0..2) {
                firstl += month_string[l]
            }

            xAxisLabel.add(firstl)
        }

        return xAxisLabel

    }

// Verarbeitung der darzustellenden Daten -> Jahr
// ---------------------------------------------------------------------------------------------

    private fun KmValuesYear(): ArrayList<Entry> {

        val myList: HashMap<LocalDate,FloatArray>
        myList = ListeMap()

        val dataVals = ArrayList<Entry>()
        var counter = 0
        var reihenfolge = 11

        while(counter < 12) {

            var month = LocalDate.now().minusMonths(reihenfolge.toLong()).getMonth()
            var year = LocalDate.now().minusMonths(reihenfolge.toLong()).getYear()

            var days = LocalDate.now().minusMonths(reihenfolge.toLong()).lengthOfMonth()

            var value = 0f

            for(i in 1..days) {

                var date = LocalDate.of(year,month,i)

                if(myList.containsKey(date)) {
                    value += myList.getValue(date)[0]
                }

                else {
                    value += 0f
                }
            }

            dataVals.add(Entry(counter.toFloat(), value))
            counter++
            reihenfolge--
        }

        return dataVals
    }

    private fun Co2ValuesYear(): ArrayList<Entry> {

        val myList: HashMap<LocalDate,FloatArray>
        myList = ListeMap()

        val dataVals = ArrayList<Entry>()
        var counter = 0
        var reihenfolge = 11
        while(counter < 12) {

            var month = LocalDate.now().minusMonths(reihenfolge.toLong()).getMonth()
            var year = LocalDate.now().minusMonths(reihenfolge.toLong()).getYear()

            var days = LocalDate.now().minusMonths(reihenfolge.toLong()).lengthOfMonth()

            var value = 0f

            for(i in 1..days) {

                var date = LocalDate.of(year,month,i)

                if(myList.containsKey(date)) {
                    value += myList.getValue(date)[2] / 1000
                }

                else {
                    value += 0f
                }
            }

            dataVals.add(Entry(counter.toFloat(), value))
            counter++
            reihenfolge--
        }

        return dataVals
    }

    private fun VerbrauchValuesYear(): ArrayList<Entry> {

        val myList: HashMap<LocalDate, FloatArray>
        myList = ListeMap()

        val dataVals = ArrayList<Entry>()
        var counter = 0
        var reihenfolge = 11

        while (counter < 12) {

            var month = LocalDate.now().minusMonths(reihenfolge.toLong()).getMonth()
            var year = LocalDate.now().minusMonths(reihenfolge.toLong()).getYear()

            var days = LocalDate.now().minusMonths(reihenfolge.toLong()).lengthOfMonth()

            var value = 0f

            for (i in 1..days) {

                var date = LocalDate.of(year, month, i)

                if (myList.containsKey(date)) {
                    value += myList.getValue(date)[1]
                } else {
                    value += 0f
                }
            }

            dataVals.add(Entry(counter.toFloat(), value))
            counter++
            reihenfolge--
        }

        return dataVals
    }

}