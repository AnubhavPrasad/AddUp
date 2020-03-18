package com.example.listmaker

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MyAdapter(
    var list: MutableList<Data>,
    var dialog: AlertDialog.Builder,
    var dia: BottomSheetDialog
) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    lateinit var del: String

    class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        var value = itemview.findViewById<TextView>(R.id.text)
        var bt = itemview.findViewById<Button>(R.id.bt_delete)
        val date = itemview.findViewById<TextView>(R.id.date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.i("pu",list.size.toString())
        return list.size
    }

    @SuppressLint("RestrictedApi", "SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.value.text = "\u20B9 "+list[position].value
        holder.date.text = list[position].date
        if(list[position].value.toInt()>500){
            holder.value.setTextColor(Color.parseColor("#E22323"))
        }else{
            holder.value.setTextColor(Color.BLACK)
        }
        val db = DatabaseHelper(holder.itemView.context)
        holder.bt.setOnClickListener {
            del = holder.date.text.toString()
            dialog.show()
        }
        holder.value.setOnClickListener {
            Log.i("inside", "clicked")
            dia.show()
            val et = dia.findViewById<EditText>(R.id.et_Add)
            val str = holder.value.text.toString()
            val s=str.replace("₹ ","")
            et?.setText(s)
            val bt = dia.findViewById<FloatingActionButton>(R.id.bt_update)
            bt?.visibility=View.VISIBLE
            dia.findViewById<TextView>(R.id.textView)?.text="EDIT"
            dia.findViewById<FloatingActionButton>(R.id.bt_add)?.visibility=View.GONE
            bt?.setOnClickListener {
                if(et?.text.toString()==""){
                    Toast.makeText(holder.itemView.context,"Enter a Value",Toast.LENGTH_SHORT).show()

                }
                else {
                    db.editdata(list[position].date, et?.text.toString())
                    list = db.readdata()
                    notifyDataSetChanged()
                    for (i in monthlist) {
                        if (i.month == list[position].month) {
                            db.editmonth(i.month, s, et?.text.toString(), i.monthvalue)
                            break
                        }
                    }
                    monthlist = db.monthread()
                    monthrecycler.adapter = MonthAdapter(dia_alldays)
                    dia.dismiss()
                }
            }
        }
        dialog.setPositiveButton("YES") { dialog, _ ->
            for(i in monthlist){
                if(i.month==list[position].month){
                    db.deductmonth(i.monthvalue,list[position].value,i.month)

                }
            }
            monthlist=db.monthread()
            monthrecycler.adapter=MonthAdapter(dia_alldays )
            db.deletespec(del)
            list = db.readdata()
            notifyDataSetChanged()
            db.close()
        }
        dialog.setNegativeButton("CLOSE") { dialog, _ ->
            dialog.dismiss()
        }

    }

}