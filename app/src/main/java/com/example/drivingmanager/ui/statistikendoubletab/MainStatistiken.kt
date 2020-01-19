package com.example.drivingmanagerdoubletabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.drivingmanager.R
import com.google.android.material.tabs.TabLayout

class MainStatistiken : Fragment() {

    //lateinit private var appBarLayout:AppBarLayout
    lateinit private var tablayoutm: TabLayout
    lateinit private var viewPager:ViewPager

    lateinit var viewA : View


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //super.onCreate(savedInstanceState)
        //setContentView(R.layout.fragment_statistik)

        viewA = inflater.inflate(R.layout.fragment_statistik, container, false)

        /*
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        toolbar.setNavigationOnClickListener { v: View? -> onBackPressed() }
        setSupportActionBar(toolbar)
*/
        tablayoutm = viewA.findViewById<TabLayout>(R.id.tabLayoutTyp)
        //appBarLayout = findViewById<AppBarLayout>(R.id.appbarid)
        viewPager = viewA.findViewById<ViewPager>(R.id.viewpager)

        var adapter: ViewPagerAdapter = ViewPagerAdapter(activity?.supportFragmentManager)


        adapter.AddFragment(FragmentDurchschnitt(),"Durchschn.")
        adapter.AddFragment(FragmentStadt(),"Stadt")
        adapter.AddFragment(FragmentAutobahn(),"Autobahn")
        adapter.AddFragment(FragmentHybrid(),"Hybrid")

        viewPager.setAdapter(adapter)
        tablayoutm.setupWithViewPager(viewPager)

        return viewA
    }
}