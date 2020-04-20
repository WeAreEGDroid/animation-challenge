package com.abualgait.msayed.egdroidathomechallenge

import android.animation.AnimatorSet
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnimationUtils
import android.view.animation.AnticipateOvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_income_details.*
import kotlinx.android.synthetic.main.item_income.*


class IncomeDetailsActivity : AppCompatActivity() {
    var income = Income(0, "", "", "", "", 0)

    var items = arrayListOf<MyNotification>().apply {
        add(
            MyNotification(
                "In App Purchase",
                "including VAT 10%",
                "$14,340"
            )
        )

        add(
            MyNotification(
                "Interstitial Ads",
                "including VAT 15%",
                "$12,000"
            )
        )
        add(
            MyNotification(
                "In App Purchase",
                "including VAT 20%",
                "$10,000"
            )
        )
        add(
            MyNotification(
                "In App Purchase",
                "including VAT 10%",
                "$2,400"
            )
        )

        add(
            MyNotification(
                "In App Purchase",
                "including VAT 10%",
                "$14,340"
            )
        )

        add(
            MyNotification(
                "In App Purchase",
                "including VAT 15%",
                "$12,000"
            )
        )
        add(
            MyNotification(
                "In App Purchase",
                "including VAT 20%",
                "$10,000"
            )
        )
        add(
            MyNotification(
                "In App Purchase",
                "including VAT 10%",
                "$2,400"
            )
        )


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_income_details)
        getIntentData()
        animateViews()
        container.viewTreeObserver.addOnGlobalLayoutListener(
            object : OnGlobalLayoutListener {
                override fun onGlobalLayout() { // Layout has happened here.
                    fab_close.translationY = container.height + fab_close.y
                    fab_check.translationY = container.height + fab_close.y
                    constraintLayout.translationY = -(constraintLayout.height).toFloat()
                    container.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
    }

    private fun animateViews() {

        val viewHeightAnimator =
            getValueAnimator(false, DURATION, DELAY, AccelerateInterpolator()) { progress ->
                /* val viewWidth = constraintLayout.width
                 val viewHeight = (progress * 150.px).toInt()
                 val ly = ConstraintLayout.LayoutParams(viewWidth, viewHeight)
                 constraintLayout.layoutParams = ly*/
                constraintLayout.translationY = -progress * constraintLayout.height
            }
        viewHeightAnimator.doOnEnd {
            setAdapter()

        }
        val toolbarAnimator =
            getValueAnimator(true, DURATION, DELAY, AccelerateInterpolator()) { progress ->
                toolbar.alpha = progress
            }


        val fabCloseAnimator =
            getValueAnimator(
                false,
                DURATION,
                DELAY,
                AnticipateOvershootInterpolator()//AccelerateInterpolator()
            ) { progress ->
                fab_close.translationY = progress * (fab_check.height + toolbar.height).toFloat()
            }
        fabCloseAnimator.doOnStart {
            fab_check.show()
        }

        val fabCheckAnimator =
            getValueAnimator(
                false,
                DURATION,
                DELAY + 10L,
                AnticipateOvershootInterpolator()// AccelerateInterpolator()
            ) { progress ->
                fab_check.translationY = progress * (fab_check.height + toolbar.height).toFloat()
            }
        val set = AnimatorSet()
        set.play(viewHeightAnimator).before(fabCheckAnimator).with(fabCloseAnimator)
            .with(toolbarAnimator)
        set.start()

    }

    private fun setAdapter() {
        val mAdapter = AdapterNotification(this, items) { income, view ->
        }
        recyclerNotifications.apply {
            adapter = mAdapter
            layoutAnimation =
                AnimationUtils.loadLayoutAnimation(
                    this@IncomeDetailsActivity,
                    R.anim.layout_animation_fall_down
                )

            layoutManager = LinearLayoutManager(this@IncomeDetailsActivity)

        }
    }

    private fun setIncome() {
        income_image.setImageResource(income.image)
        income_title.text = income.title
        amount.text = income.amount
        date.text = income.date
        state.text = income.status
    }

    private fun getIntentData() {
        income = intent.getSerializableExtra("item") as Income
        setIncome()

    }

    companion object {
        val DURATION = 900L
        val DELAY = 200L
    }

    fun backClick(view: View) {
        finish()
    }
}
