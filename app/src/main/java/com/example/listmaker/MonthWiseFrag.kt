package com.example.listmaker


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listmaker.databinding.FragmentMonthWiseBinding

lateinit var monthrecycler: RecyclerView        //  recyclerview for month-wise
lateinit var dia_alldays: Dialog             //Dialog for click on a month item

class MonthWiseFrag : Fragment() {
    lateinit var binding: FragmentMonthWiseBinding
    lateinit var dialog: AlertDialog.Builder

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_month_wise, container, false)
        monthrecycler = binding.recyclermonth
        dialog = AlertDialog.Builder(context!!)
        dialog.create()
        dialog.setTitle("Delete")
        dialog.setMessage("Are you sure ?")
        binding.recyclermonth.layoutManager = LinearLayoutManager(context!!)
        val db = DatabaseHelper(context!!)
        monthlist = db.monthread()
        dia_alldays = Dialog(context!!)
        dia_alldays.setContentView(R.layout.alldays_layout)
        monthrecycler.adapter = MonthAdapter(dia_alldays)
        return binding.root

    }

    override fun onStart() {
        super.onStart()
        val db = DatabaseHelper(context!!)
        Log.i("start", "start")
        monthlist = db.monthread()
        monthrecycler.adapter = MonthAdapter(dia_alldays)
    }


}
