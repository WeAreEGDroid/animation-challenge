package com.abualgait.msayed.egdroidathomechallenge

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterShoe(
    val context: Context,
    val items: ArrayList<Shoe>

) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var recyclerView: RecyclerView

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_shoe, parent, false)
        return MyViewHolder(view)
    }


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as MyViewHolder).bind(
            items[position],
            position
        )
    }

    class MyViewHolder(
        itemView: View

    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            item: Shoe,
            position: Int
        ) {

            itemView.apply {


                val box_ceil = findViewById<ImageView>(R.id.box_ceil)
                val cover = findViewById<View>(R.id.cover)
                val cover_open = findViewById<View>(R.id.cover_open)

                val type = findViewById<TextView>(R.id.type)
                val model = findViewById<TextView>(R.id.model)
                val details = findViewById<TextView>(R.id.details)
                val price = findViewById<TextView>(R.id.price)


                if (!item.isOpen && item.progress > 0) {
                    animateCloseBox(box_ceil!!, cover!!, item.progress, cover_open)
                } else {
                    if (item.isCurrent) {
                        box_ceil.show()
                        cover.hide()
                        cover_open.hide()
                    } else {
                        with(item.progress * 100) {
                            type.translationX = this
                            model.translationX = this
                            details.translationX = this
                            price.translationX = this

                        }

                        type.animate().translationX(-item.progress).alpha(item.progress)
                            .startDelay =
                            100
                        model.animate().translationX(-item.progress).alpha(item.progress)
                            .startDelay =
                            200
                        details.animate().translationX(-item.progress).alpha(item.progress)
                            .startDelay =
                            200
                        price.animate().translationX(-item.progress).alpha(item.progress)
                            .startDelay =
                            350

                        animateOpenBox(
                            box_ceil!!,
                            cover!!,
                            item.progress,
                            cover_open
                        )
                    }
                }

                findViewById<ImageView>(R.id.product_image).apply {
                    setImageResource(item.image)
                }

                type.text = (item.type)
                model.text = (item.model)
                details.text = (item.details)
                price.text = (item.price)

            }
        }

        private fun animateOpenBox(
            box_ceil: ImageView?,
            cover: View?,
            progress: Float,
            coverOpen: View
        ) {
            box_ceil?.visibility = View.INVISIBLE
            cover?.visibility = View.INVISIBLE
            coverOpen.show()

            box_ceil?.pivotY =
                box_ceil?.height!!.toFloat()
            coverOpen.pivotY =
                0f

            if (progress <= 0.5) {
                coverOpen.rotationX = progress * 180
                box_ceil.rotationX = -progress * 180
            } else {
                coverOpen.alpha = 0f
                box_ceil.show()
                box_ceil.rotationX = (progress - 1) * 180
            }

        }

        private fun animateCloseBox(
            box_ceil: ImageView?,
            cover: View?,
            progress: Float,
            coverOpen: View
        ) {

            coverOpen.visibility = View.INVISIBLE
            box_ceil?.show()

            box_ceil?.pivotY =
                box_ceil?.height!!.toFloat()
            cover?.pivotY =
                cover?.height!!.toFloat()

            if (progress <= 0.5) {
                box_ceil.rotationX = progress * -180
            } else {
                box_ceil.alpha = 0f
                cover.show()
                cover.rotationX = progress * -180
            }


        }

    }


}
