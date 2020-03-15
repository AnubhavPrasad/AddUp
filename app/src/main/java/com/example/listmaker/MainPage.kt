package com.example.listmaker


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewDebug
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

class MainPage : Fragment() {
    lateinit var binding: FragmentMainPageBinding
    private var listvalue = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_page, container, false)
        val dia = BottomSheetDialog(context!!)
        val db = DatabaseHelper(context!!)
        lateinit var list: MutableList<Data>
        binding.toolbar.inflateMenu(R.menu.menu)

        val dialog=AlertDialog.Builder(context)
        dialog.create()
        dialog.setTitle("Delete")
        dialog.setMessage("Are you sure ?")
        list = db.readdata()
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete -> {
                    db.deletedata()
                    list = db.readdata()
                    binding.recycler.adapter = MyAdapter(list,dialog,dia)
                }
                R.id.about->{
                    findNavController().navigate(R.id.action_mainPage_to_aboutFrag)
                }
            }
            true
        }
        binding.recycler.layoutManager = LinearLayoutManager(context!!)
        dia.setContentView(R.layout.dialog_layout)
        binding.recycler.adapter = MyAdapter(list,dialog,dia)
        binding.floatingActionButton.setOnClickListener {
            dia.findViewById<EditText>(R.id.et_Add)?.setText("")
            dia.findViewById<FloatingActionButton>(R.id.bt_add)?.setImageResource(R.drawable.add_24dp)
            dia.show()
        }
        dia.findViewById<ImageView>(R.id.close)?.setOnClickListener {
            dia.dismiss()
        }
        dia.findViewById<FloatingActionButton>(R.id.bt_add)?.setOnClickListener {
            listvalue = dia.findViewById<EditText>(R.id.et_Add)?.text.toString()
            if (listvalue.length > 0) {
                val data = Data(listvalue)
                db.insertdata(data)
                list = db.readdata()
                binding.recycler.adapter = MyAdapter(list,dialog,dia)
                dia.findViewById<EditText>(R.id.et_Add)?.setText("")
                dia.dismiss()
            } else {
                Toast.makeText(context, "Enter Something", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }


}
