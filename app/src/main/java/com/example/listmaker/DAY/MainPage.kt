package com.example.listmaker.DAY


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listmaker.MainTab.DatabaseHelper
import com.example.listmaker.Month.MonthAdapter
import com.example.listmaker.Month.MonthData
import com.example.listmaker.Month.dia_alldays
import com.example.listmaker.Month.monthrecycler
import com.example.listmaker.R
import com.example.listmaker.databinding.FragmentMainPageBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*

lateinit var daterecycler: RecyclerView      //Recyclerview for date-wise
lateinit var datedialog_del: AlertDialog.Builder     //Alert Dialog for delete of a date value
lateinit var bottom_sheetdia: BottomSheetDialog     //Bottom sheet dialog that appears from below
lateinit var monthlist: MutableList<MonthData>      //list of month data
lateinit var datelist: MutableList<Data>        //list of days data
lateinit var itemrec:RecyclerView
class MainPage() : Fragment() {
    lateinit var binding: FragmentMainPageBinding
    private var itemprice = ""

    @SuppressLint("ResourceAsColor", "RestrictedApi", "SimpleDateFormat")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main_page, container, false
        )
        bottom_sheetdia =BottomSheetDialog(context!!)
        val db = DatabaseHelper(context!!)
        monthlist = db.monthread()
        datedialog_del = AlertDialog.Builder(context)
        datedialog_del.create()
        datedialog_del.setTitle("Delete")
        datedialog_del.setMessage("Are you sure? It will automatically deduct from month.")
        daterecycler = binding.recycler
        datelist = db.readdata()
        binding.recycler.layoutManager = LinearLayoutManager(context!!)

        bottom_sheetdia.setContentView(
            R.layout.dialog_layout
        )

        binding.recycler.adapter = MyAdapter(
            datelist,
            datedialog_del
        )
        binding.floatingActionButton.setOnClickListener {
            bottom_sheetdia.findViewById<TextView>(
                R.id.textView
            )?.text = "ADD"
            bottom_sheetdia.findViewById<EditText>(
                R.id.item_et
            )?.setText("")
            bottom_sheetdia.findViewById<FloatingActionButton>(R.id.bt_update)?.visibility=View.GONE
            bottom_sheetdia.findViewById<FloatingActionButton>(R.id.bt_add)?.visibility=View.VISIBLE
            bottom_sheetdia.findViewById<EditText>(R.id.itemprice_et)?.setText("")
            bottom_sheetdia.show()
        }
        binding.addText.setOnClickListener {
            bottom_sheetdia.findViewById<TextView>(
                R.id.textView
            )?.text = "ADD"
            bottom_sheetdia.findViewById<EditText>(
                R.id.item_et
            )?.setText("")
            bottom_sheetdia.findViewById<FloatingActionButton>(R.id.bt_update)?.visibility=View.GONE
            bottom_sheetdia.findViewById<FloatingActionButton>(R.id.bt_add)?.visibility=View.VISIBLE
            bottom_sheetdia.findViewById<EditText>(R.id.itemprice_et)?.setText("")
            bottom_sheetdia.show()
        }

        bottom_sheetdia.findViewById<ImageView>(
            R.id.close
        )?.setOnClickListener {
            Log.i("i", "close")
            bottom_sheetdia.dismiss()
        }
        bottom_sheetdia.findViewById<FloatingActionButton>(
            R.id.bt_add
        )?.setOnClickListener {
            itemprice = bottom_sheetdia.findViewById<EditText>(
                R.id.itemprice_et
            )?.text.toString()
            var item= bottom_sheetdia.findViewById<EditText>(R.id.item_et)?.text.toString()
            if (itemprice.length > 0) {
                Log.i("i", "called111")
                datelist = db.readdata()
                monthlist = db.monthread()
                val getdate = Calendar.getInstance().time
                val dateformat = SimpleDateFormat("dd-MMM-yyyy")
                val date = dateformat.format(getdate)
                val datemonthfor = SimpleDateFormat("MMMM")
                val datemonth = datemonthfor.format(getdate)
                val monthData = MonthData(
                    0,
                    itemprice,
                    datemonth
                )
                val itemData=ItemData(item,itemprice,date)
                val data =
                    Data(itemprice, date, datemonth,itemData)
                var j = 0
                var k = 0
                for (i in datelist) {
                    if (i.date == date) {
                        Log.i("i", "called")
                        db.updatedata(i.value, itemprice, date,itemData,datemonth)
                        j += 1
                        break
                    }
                }
                for (i in monthlist) {
                    if (i.month == datemonth) {
                        db.monthupdate(i.monthvalue, itemprice, datemonth)
                        k += 1
                        break
                    }
                }
                if (j == 0) {
                    db.insertdata(data)
                }
                if (k == 0) {
                    db.insertmonth(monthData)
                }
                binding.addText.visibility = View.GONE
                binding.recycler.visibility = View.VISIBLE
                datelist = db.readdata()
                monthlist = db.monthread()
                monthrecycler.adapter = MonthAdapter(
                    dia_alldays
                )
                binding.recycler.adapter = MyAdapter(
                    datelist,
                    datedialog_del
                )
                bottom_sheetdia.dismiss()
            } else {
                Toast.makeText(context, "Enter Something", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if (datelist.size == 0) {
            binding.recycler.visibility = View.GONE
            binding.addText.visibility = View.VISIBLE
        }
    }
}
