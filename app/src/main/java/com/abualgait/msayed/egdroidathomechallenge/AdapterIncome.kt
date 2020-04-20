package com.abualgait.msayed.egdroidathomechallenge

import android.animation.ValueAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.core.animation.doOnEnd
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AdapterIncome(
    val context: Context,
    val items: ArrayList<Income>,
    val callback: (item: Income, view: View) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var recyclerView: RecyclerView
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_income, parent, false)
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
        val callback: (item: Income, view: View) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Income) {

            itemView.apply {

                findViewById<ImageView>(R.id.income_image).setImageResource(item.image)
                findViewById<TextView>(R.id.income_title).text = (item.title)
                findViewById<TextView>(R.id.amount).text = (item.amount)
                findViewById<TextView>(R.id.date).text = (item.date)
                findViewById<TextView>(R.id.state).text = (item.status)


            }
            itemView.setOnClickListener {
                callback(item, itemView)

            }
        }
    }

    private inline val LinearLayoutManager.visibleItemsRange: IntRange
        get() = findFirstVisibleItemPosition()..findLastVisibleItemPosition()

    fun getScaleDownAnimator(
        isScaledDown: Boolean,
        income: Income
    ): ValueAnimator {
        val lm = recyclerView.layoutManager as LinearLayoutManager

        val animator = getValueAnimator(
            isScaledDown,
            duration = 1000L, delay = 0L, interpolator = AccelerateDecelerateInterpolator()
        ) { progress ->

            // Get viewHolder for all visible items and animate attributes
            for (i in lm.visibleItemsRange) {
                val holder = recyclerView.findViewHolderForLayoutPosition(i) as MyViewHolder
                setScaleDownProgress(holder, i, progress, income)
            }
        }

        // For all the non visible items in the layout manager, notify them to adjust the
        // view to the new size
        animator.doOnEnd {
            repeat(lm.itemCount) { if (it !in lm.visibleItemsRange) notifyItemChanged(it) }
        }
        return animator
    }

    private fun setScaleDownProgress(
        holder: MyViewHolder,
        position: Int,
        progress: Float,
        income: Income
    ) {
        holder.itemView.requestLayout()
        if (items[position] != income) {
            holder.itemView.alpha = progress
        }
    }
}
