package com.example.drivingmanagerdoubletabs

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.drivingmanager.R
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout

class MainStatistiken : AppCompatActivity() {

    //lateinit private var appBarLayout:AppBarLayout
    lateinit private var tablayoutm: TabLayout
    lateinit private var viewPager:ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_statistik)

        /*
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        toolbar.setNavigationOnClickListener { v: View? -> onBackPressed() }
        setSupportActionBar(toolbar)
*/
        tablayoutm = findViewById<TabLayout>(R.id.tabLayoutTyp)
        //appBarLayout = findViewById<AppBarLayout>(R.id.appbarid)
        viewPager = findViewById<ViewPager>(R.id.viewpager)

        var adapter: ViewPagerAdapter = ViewPagerAdapter(getSupportFragmentManager())

        adapter.AddFragment(FragmentDurchschnitt(),"Durchschnitt")
        adapter.AddFragment(FragmentStadt(),"Stadt")
        adapter.AddFragment(FragmentAutobahn(),"Autobahn")
        adapter.AddFragment(FragmentHybrid(),"Hybrid")

        viewPager.setAdapter(adapter)
        tablayoutm.setupWithViewPager(viewPager)
    }
}