package com.example.listmaker

import android.app.AlertDialog
import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MonthAdapter(var alldaysdia: Dialog) : RecyclerView.Adapter<MonthAdapter.MyViewHolder>() {
    lateinit var dialog: AlertDialog.Builder

    class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        var month = itemview.findViewById<TextView>(R.id.date)
        var value = itemview.findViewById<TextView>(R.id.text)
        var delete = itemview.findViewById<Button>(R.id.bt_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.monthrecycler_item, parent, false)
        alldaysdia.findViewById<RecyclerView>(R.id.alldays_recycler).layoutManager =
            LinearLayoutManager(parent.context)
        dialog = AlertDialog.Builder(parent.context)
        dialog.setTitle("Delete")
        dialog.setMessage("Are you Sure ?")
        dialog.create()
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return monthlist.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val db = DatabaseHelper(holder.itemView.context)
        holder.month.text = monthlist[position].month
        holder.delete.setOnClickListener {
            dialog.show()
        }
        holder.value.text = "\u20B9" + monthlist[position].monthvalue
        dialog.setNegativeButton("CLOSE") { d1, _ ->
            d1.dismiss()
        }
        dialog.setPositiveButton("OK") { d2, _ ->
            db.monthdelspec(monthlist[position].month)
            monthlist = db.monthread()
            notifyDataSetChanged()
            d2.dismiss()
        }
        holder.itemView.setOnClickListener {
            val list = mutableListOf<Data>()
            datelist=db.readdata()
            for (i in datelist) {
                if (i.month == monthlist[position].month) {
                    list.add(i)
                }
            }
            for (i in 0 until list.size) {
                for (j in i until list.size) {
                    val f = list[i].date.take(2)
                    val s = list[j].date.take(2)
                    if (f > s) {
                        val t = list[i]
                        list[j] = list[i]
                        list[i] = t
                    }

                }
            }
            alldaysdia.show()
            alldaysdia.findViewById<RecyclerView>(R.id.alldays_recycler).adapter =
                AllDaysAdapter(list)

        }
    }
}