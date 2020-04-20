package com.abualgait.msayed.egdroidathomechallenge

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_shoe_boxes.*

//TODO need to handle viewpager reverse page changes [LTR]
class ShoeBoxes : AppCompatActivity() {

    var items = arrayListOf<Shoe>().apply {
        add(Shoe(R.drawable.shoe1, "Men's Shoe", "Nike Air Max", "Tiger", "$150", "4.3"))
        add(Shoe(R.drawable.shoe2, "Men's Shoe", "Nike Air MaxVapore", "270", "$180", "3.3"))
        add(
            Shoe(
                R.drawable.shoe3,
                "Men's Shoe",
                "Nike React Prestro",
                "Rabid Panda",
                "$250",
                "4.5"
            )
        )
        add(Shoe(R.drawable.shoe2, "Men's Shoe", "Nike Blade", "Flynik2", "$100", "3.9"))
        add(Shoe(R.drawable.shoe1, "Men's Shoe", "Nike Blade", "Flynik2", "$100", "3.9"))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shoe_boxes)
        setAdapter()
    }

    var isSwipeRight = false
    private fun setAdapter() {

        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin)
        val offsetPx = resources.getDimensionPixelOffset(R.dimen.offset)
        val mAdapter = AdapterShoe(this@ShoeBoxes, items)

        recycler_shoes.apply {

            items[0].isOpen = true
            items[0].isCurrent = true
            mAdapter.notifyItemChanged(0)

            rate.text = items[0].rate

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                    Log.e("progress ", (1 - positionOffset).toString())
                    Log.e("position ", position.toString())
                    Log.e("isSwipeRight ", isSwipeRight.toString())
                    if (positionOffset > 0) {
                        //animate rate
                        rate.text = items[position].rate
                        rate.translationX = rate_frame.width / 2f
                        rate.alpha = 0f
                        rate.animate().translationX(-positionOffset)
                            .alpha(positionOffset)
                            .start()

                        //close current page
                        items[position].isOpen = false
                        items[position].progress = positionOffset
                        mAdapter.notifyItemChanged(position)
                        //open next page
                        items[position + 1].isOpen = true
                        items[position + 1].progress = positionOffset
                        mAdapter.notifyItemChanged(position + 1)

                    }
                }

            })
            setShowSideItems(pageMarginPx, offsetPx)
            adapter = mAdapter


        }


    }

}
