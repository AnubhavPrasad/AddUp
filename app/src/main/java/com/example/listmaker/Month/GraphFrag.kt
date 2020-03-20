package com.example.listmaker.Month

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.listmaker.DAY.monthlist
import com.example.listmaker.R
import com.example.listmaker.databinding.GraphBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate

class GraphFrag : Fragment() {
    lateinit var binding: GraphBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.graph, container, false)
        val graph = binding.graph
        graph.setDrawValueAboveBar(true)
        graph.axisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        graph.xAxis.position = XAxis.XAxisPosition.TOP
        graph.scaleY = 1f
        graph.setPinchZoom(true)
        graph.setDescription("Month-Wise Money Spent")
        graph.setDrawBarShadow(false)
        graph.isDoubleTapToZoomEnabled = false
        val barentries = arrayListOf<BarEntry>()
        for (i in 0 until monthlist.size) {
            when (monthlist[i].month) {
                "January" -> barentries.add(BarEntry(monthlist[i].monthvalue.toFloat(), 0))
                "February" -> barentries.add(BarEntry(monthlist[i].monthvalue.toFloat(), 1))
                "March" -> barentries.add(BarEntry(monthlist[i].monthvalue.toFloat(), 2))
                "April" -> barentries.add(BarEntry(monthlist[i].monthvalue.toFloat(), 3))
                "May" -> barentries.add(BarEntry(monthlist[i].monthvalue.toFloat(), 4))
                "June" -> barentries.add(BarEntry(monthlist[i].monthvalue.toFloat(), 5))
                "July" -> barentries.add(BarEntry(monthlist[i].monthvalue.toFloat(), 6))
                "August" -> barentries.add(BarEntry(monthlist[i].monthvalue.toFloat(), 7))
                "September" -> barentries.add(BarEntry(monthlist[i].monthvalue.toFloat(), 8))
                "October" -> barentries.add(BarEntry(monthlist[i].monthvalue.toFloat(), 9))
                "November" -> barentries.add(BarEntry(monthlist[i].monthvalue.toFloat(), 10))
                "December" -> barentries.add(BarEntry(monthlist[i].monthvalue.toFloat(), 11))
            }
        }
        val dates = arrayListOf<String>()
        dates.add("Jan")
        dates.add("Feb")
        dates.add("Mar")
        dates.add("Apr")
        dates.add("May")
        dates.add("Jun")
        dates.add("Jul")
        dates.add("Aug")
        dates.add("Sep")
        dates.add("Oct")
        dates.add("Nov")
        dates.add("Dec")
        val barDataSet = BarDataSet(barentries, "Money Spent")
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS)
        val bardata = BarData(dates, barDataSet)
        graph.data = bardata
        graph.invalidate()
        return binding.root
    }
}
