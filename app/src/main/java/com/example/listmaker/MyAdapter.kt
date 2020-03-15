package com.example.listmaker

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class MyAdapter(var list: MutableList<Data>): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    class MyViewHolder(itemview: View):RecyclerView.ViewHolder(itemview){
        var value=itemview.findViewById<TextView>(R.id.text)
        var bt=itemview.findViewById<Button>(R.id.bt_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.recycler_item,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.value.text=list[position].value
        val db=DatabaseHelper(holder.itemView.context)
        holder.bt.setOnClickListener {
            db.deletespec(holder.value.text.toString())
            list=db.readdata()
            notifyDataSetChanged()

        }

    }

}