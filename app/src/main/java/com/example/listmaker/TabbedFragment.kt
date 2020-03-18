package com.example.listmaker


import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.listmaker.databinding.FragmentTabbedBinding

class TabbedFragment : Fragment() {
    lateinit var binding: FragmentTabbedBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tabbed, container, false)
        binding.maintoolbar.inflateMenu(R.menu.menu)
        val dia_delmonth = AlertDialog.Builder(context)
        dia_delmonth.create()
        dia_delmonth.setTitle("Delete")
        dia_delmonth.setMessage("Are you sure ?")
        val limit_dialog = Dialog(context!!)
        limit_dialog.setContentView(R.layout.limit_dialog)
        binding.viewpager.adapter = PagerAdapter(context!!, childFragmentManager)
        binding.tabalyout.setupWithViewPager(binding.viewpager)
        val db = DatabaseHelper(context!!)
        datelist = db.readdata()
        binding.maintoolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete -> {
                    dia_delmonth.show()

                }
                R.id.about -> {
                    findNavController().navigate(R.id.action_tabbedFragment_to_aboutFrag)
                }
                R.id.theme -> {
                    Toast.makeText(context, "Will be implemeted later", Toast.LENGTH_SHORT).show()
                }
                R.id.limit -> {
                    val limit=db.limitread()
                    limit_dialog.findViewById<EditText>(R.id.daylimit).setText(limit.daywise_limit.toString())
                    limit_dialog.findViewById<EditText>(R.id.monthlimit).setText(limit.monthwise_limit.toString())
                    limit_dialog.show()

                }
            }
            true
        }
        dia_delmonth.setPositiveButton("OK") { d1, _ ->
            Log.i("if", "called")
            db.deletedata()
            db.monthdel()
            datelist = db.readdata()
            monthlist = db.monthread()
            monthrecycler.adapter = MonthAdapter(dia_alldays)
            daterecycler.adapter = MyAdapter(datelist, datedialog_del, bottom_sheetdia)
            d1.dismiss()
        }
        dia_delmonth.setNegativeButton("CLOSE") { d2, _ ->
            Log.i("if", "ok")
            d2.dismiss()
        }
        limit_dialog.findViewById<Button>(R.id.set_bt).setOnClickListener {
            if (limit_dialog.findViewById<EditText>(R.id.daylimit).text.toString() != "" && limit_dialog.findViewById<EditText>(
                    R.id.monthlimit
                ).text.toString() != ""
            ) {
                val daywise_limit =
                    limit_dialog.findViewById<EditText>(R.id.daylimit).text.toString().toInt()
                val monthwise_limit =
                    limit_dialog.findViewById<EditText>(R.id.monthlimit).text.toString().toInt()
                val limit=Limit(daywise_limit, monthwise_limit)
                db.limitinsert(limit)
                monthrecycler.adapter = MonthAdapter(dia_alldays)
                daterecycler.adapter = MyAdapter(datelist, datedialog_del, bottom_sheetdia)
                limit_dialog.dismiss()
            } else {
                Toast.makeText(context, "Enter Limit", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }


}
