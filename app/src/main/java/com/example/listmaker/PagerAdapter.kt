package com.example.listmaker

import android.app.AlertDialog
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


private val TAB_TITLES = arrayOf(
    "DAY-WISE",
    "MONTH-WISE"
)


class PagerAdapter(
    context: Context,
    fm: FragmentManager
) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {

        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = MainPage()

            1 -> fragment =MonthWiseFrag()
        }
        return fragment!!
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return TAB_TITLES[position]
    }

    override fun getCount(): Int {
        return 2
    }
}
