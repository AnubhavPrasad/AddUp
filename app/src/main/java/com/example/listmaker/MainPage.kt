package com.example.listmaker


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listmaker.databinding.FragmentMainPageBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*

class MainPage : Fragment() {
    lateinit var binding: FragmentMainPageBinding
    private var listvalue = ""
    @SuppressLint("ResourceAsColor", "RestrictedApi", "SimpleDateFormat")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_page, container, false)
        val dia = BottomSheetDialog(context!!)
        val db = DatabaseHelper(context!!)
        lateinit var list: MutableList<Data>
        binding.toolbar.inflateMenu(R.menu.menu)

        val dialog = AlertDialog.Builder(context)
        dialog.create()
        dialog.setTitle("Delete")
        dialog.setMessage("Are you sure ?")
        val dialog2 = AlertDialog.Builder(context)
        dialog2.create()
        dialog2.setTitle("Delete")
        dialog2.setMessage("Are you sure ?")
        list = db.readdata()
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete -> {
                    dialog2.show()

                }
                R.id.about -> {
                    findNavController().navigate(R.id.action_mainPage_to_aboutFrag)
                }
                R.id.theme -> {
                    Toast.makeText(context, "Will be implemeted later", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
        dialog2.setPositiveButton("OK") { d1, _ ->
            db.deletedata()
            list = db.readdata()
            binding.recycler.adapter = MyAdapter(list, dialog, dia)
            d1.dismiss()
        }
        dialog2.setNegativeButton("CLOSE") { d2, _ ->
            d2.dismiss()
        }
        binding.recycler.layoutManager = LinearLayoutManager(context!!)
        dia.setContentView(R.layout.dialog_layout)
        binding.recycler.adapter = MyAdapter(list, dialog, dia)
        binding.floatingActionButton.setOnClickListener {
            dia.findViewById<EditText>(R.id.et_Add)?.setText("")
            dia.findViewById<FloatingActionButton>(R.id.bt_add)?.visibility=View.VISIBLE
            dia.findViewById<FloatingActionButton>(R.id.bt_update)?.visibility=View.GONE
            dia.show()
        }
        dia.findViewById<ImageView>(R.id.close)?.setOnClickListener {
            Log.i("i","close")
            dia.dismiss()
        }
        dia.findViewById<FloatingActionButton>(R.id.bt_add)?.setOnClickListener {
            Log.i("i","called11122")
            listvalue = dia.findViewById<EditText>(R.id.et_Add)?.text.toString()
            if (listvalue.length > 0) {
                Log.i("i","called111")
                list = db.readdata()
                val getdate = Calendar.getInstance().time
                val dateformat = SimpleDateFormat("dd-MMM-yyyy")
                val date = dateformat.format(getdate)
                val data = Data(listvalue, date)
                var j = 0
                for (i in list) {
                    if (i.date == date) {
                        Log.i("i","called")
                        db.updatedata2(i.value, listvalue,date)
                        j += 1
                        break
                    }
                }
                if (j == 0) {
                    db.insertdata(data)
                }
                list = db.readdata()
                binding.recycler.adapter = MyAdapter(list, dialog, dia)

                dia.dismiss()
            } else {
                Toast.makeText(context, "Enter Something", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }


}
