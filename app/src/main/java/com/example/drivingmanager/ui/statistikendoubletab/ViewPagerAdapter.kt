package com.example.drivingmanagerdoubletabs

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.util.*


class ViewPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter((fm!!)) {

    // Konstruktor ist hier etwas seltsam

    // Normale List würde nicht add beinhalten
    private val fragmentList : MutableList<Fragment> = ArrayList()
    private val fragmentListTitles : MutableList<String> = ArrayList()


    override fun getItem(position: Int): Fragment {
        return fragmentList.get(position)
    }

    override fun getCount(): Int {
        return fragmentListTitles.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return fragmentListTitles.get(position)
    }

    fun AddFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        fragmentListTitles.add(title)
    }
}