package com.abualgait.msayed.egdroidathomechallenge

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterAlarms(
    val context: Context,
    val items: ArrayList<AlarmItem>,
    val callback: (item: AlarmItem, view: View) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var recyclerView: RecyclerView
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_alarm, parent, false)
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
        val callback: (item: AlarmItem, view: View) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: AlarmItem) {


            itemView.apply {

                findViewById<TextView>(R.id.time).text = (item.formattedTime)
                findViewById<Switch>(R.id.isSetSwitch).isChecked = (item.iSet)

                for (index in 0 until (itemView as ViewGroup).childCount) {
                    (itemView).getChildAt(index).apply {
                        if (tag == context.getString(R.string.day_tag)) {

                            if (index + 1 in item.indecesDayOfWeek) {
                                this.isSelected = true
                            }
                            setOnClickListener {

                                it.isSelected = !it.isSelected
                                if (it.isSelected)
                                    item.indecesDayOfWeek.add(index + 1)
                            }
                        }
                    }

                }

            }
            itemView.setOnClickListener {
                callback(item, itemView)

            }
        }
    }


}
