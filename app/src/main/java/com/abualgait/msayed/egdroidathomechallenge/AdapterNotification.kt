package com.abualgait.msayed.egdroidathomechallenge

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterNotification(
    val context: Context,
    val items: ArrayList<MyNotification>,
    val callback: (item: MyNotification, view: View) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_notification, parent, false)
        return MyViewHolder(view, callback)
    }


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MyViewHolder).bind(items[position])
    }


    class MyViewHolder(
        itemView: View,
        val callback: (item: MyNotification, view: View) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: MyNotification) {
            itemView.apply {

                findViewById<TextView>(R.id.title).text = (item.title)
                findViewById<TextView>(R.id.details).text = (item.details)
                findViewById<TextView>(R.id.price).text = (item.price)


            }
            itemView.setOnClickListener {
                callback(item, itemView)
            }
        }
    }
}
