package com.example.drivingmanagerdoubletabs

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import butterknife.ButterKnife
import com.example.drivingmanager.R
import com.google.android.material.tabs.TabLayout

class FragmentStadt : Fragment() {

    lateinit var viewA : View

    // Konstruktor -- automatisch ???

    fun setupViewPager(viewPager: ViewPager) {

        val adapterD: ViewPagerAdapter2 = ViewPagerAdapter2(childFragmentManager)

        adapterD.AddFragment(ViewWocheS(),"Woche")
        adapterD.AddFragment(ViewMonatS(),"Monat")
        adapterD.AddFragment(ViewJahrS(),"Jahr")

        viewPager.setAdapter(adapterD)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @Nullable
    override fun onCreateView(inflater: LayoutInflater,  @Nullable container: ViewGroup?,  @Nullable savedInstanceState: Bundle?): View {

        viewA = inflater.inflate(R.layout.stadt_statistik,container,false)
        super.onCreate((savedInstanceState))

        val viewPagerD = ButterKnife.findById<ViewPager>(viewA,R.id.viewpagerS)
        setupViewPager(viewPagerD)

        val tabLayoutD = ButterKnife.findById<TabLayout>(viewA,R.id.tabLayoutTypS)
        tabLayoutD.setupWithViewPager(viewPagerD)

        return viewA
    }
}