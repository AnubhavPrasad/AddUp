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
        holder.itemView.setOnClickListener {
            Log.i("inside", "clicked")
            dia.show()
            val et = dia.findViewById<EditText>(R.id.et_Add)
            val str = holder.value.text.toString()
            et?.setText(str)
            val bt = dia.findViewById<FloatingActionButton>(R.id.bt_update)
            bt?.visibility=View.VISIBLE
            dia.findViewById<FloatingActionButton>(R.id.bt_add)?.visibility=View.GONE
            bt?.setOnClickListener {
                db.updatedata(str, et?.text.toString())
                list = db.readdata()
                notifyDataSetChanged()
                dia.dismiss()
            }
        }
        dialog.setPositiveButton("YES") { dialog, _ ->
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