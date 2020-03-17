package com.example.listmaker


import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.listmaker.databinding.FragmentTabbedBinding

class TabbedFragment : Fragment() {
    lateinit var binding: FragmentTabbedBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_tabbed,container,false)
        binding.maintoolbar.inflateMenu(R.menu.menu)
        val dialog2 = AlertDialog.Builder(context)
        dialog2.create()
        dialog2.setTitle("Delete")
        dialog2.setMessage("Are you sure ?")
        binding.viewpager.adapter=PagerAdapter(context!!,childFragmentManager)
        binding.tabalyout.setupWithViewPager(binding.viewpager)
        val db=DatabaseHelper(context!!)
        datelist = db.readdata()
        binding.maintoolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete -> {
                    dialog2.show()

                }
                R.id.about -> {
                    findNavController().navigate(R.id.action_tabbedFragment_to_aboutFrag)
                }
                R.id.theme -> {
                    Toast.makeText(context, "Will be implemeted later", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
        dialog2.setPositiveButton("OK") { d1, _ ->
            Log.i("if","called")
            db.deletedata()
            db.monthdel()
            datelist = db.readdata()
            monthlist=db.monthread()
            monthrecycler.adapter=MonthAdapter(dia_alldays)
            daterecycler.adapter = MyAdapter(datelist, datedialog_del, bottom_sheetdia)
            d1.dismiss()
        }
        dialog2.setNegativeButton("CLOSE") { d2, _ ->
            Log.i("if","ok")
            d2.dismiss()
        }
        return binding.root
    }


}
