package com.example.listmaker

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MonthAdapter (): RecyclerView.Adapter<MonthAdapter.MyViewHolder>() {
    lateinit var dialog:AlertDialog.Builder
    class MyViewHolder(itemview: View):RecyclerView.ViewHolder(itemview){
        var month=itemview.findViewById<TextView>(R.id.date)
        var value=itemview.findViewById<TextView>(R.id.text)
        var delete=itemview.findViewById<Button>(R.id.bt_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.recycler_item,parent,false)

        dialog=AlertDialog.Builder(parent.context)
        dialog.setTitle("Delete")
        dialog.setMessage("Are you Sure ?")
        dialog.create()
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return monthlist.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val db=DatabaseHelper(holder.itemView.context)
        holder.month.text=monthlist[position].month
        holder.delete.setOnClickListener {
            dialog.show()
        }
        holder.value.text="\u20B9"+ monthlist[position].monthvalue
        dialog.setNegativeButton("CLOSE") { d1, _ ->
            d1.dismiss()
        }
        dialog.setPositiveButton("OK") { d2, _ ->
            db.monthdelspec(monthlist[position].month)
            monthlist=db.monthread()
            notifyDataSetChanged()
            d2.dismiss()
        }
    }
}