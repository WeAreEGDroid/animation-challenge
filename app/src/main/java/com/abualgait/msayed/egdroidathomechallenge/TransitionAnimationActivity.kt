package com.abualgait.msayed.egdroidathomechallenge

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.cardview.widget.CardView
import androidx.core.animation.doOnEnd
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_view_transitions.*

class TransitionAnimationActivity : BaseActivity() {

    var items = arrayListOf<Income>().apply {
        add(
            Income(
                R.drawable.egdroid_logo,
                "Eg Droid",
                "120",
                "1/2/2000",
                "Recieved",
                R.drawable.egdroid_logo
            )
        )

        add(
            Income(
                R.drawable.ic_google_play,
                "Google Play",
                "200",
                "20/3/2010",
                "Recieved",
                R.drawable.ic_google_play
            )
        )

        add(
            Income(
                R.drawable.ic_youtube,
                "Youtube",
                "300",
                "22/4/2020",
                "Recieved",
                R.drawable.ic_youtube
            )
        )

        add(
            Income(
                R.drawable.ic_twitter,
                "Twitter",
                "120",
                "20/3/2019",
                "Recieved",
                R.drawable.ic_twitter
            )
        )

        add(
            Income(
                R.drawable.egdroid_logo,
                "Eg Droid",
                "120",
                "20/3/1993",
                "Recieved",
                R.drawable.egdroid_logo
            )
        )


        add(
            Income(
                R.drawable.ic_behance,
                "Behance",
                "120",
                "20/3/1993",
                "Recieved",
                R.drawable.ic_behance
            )
        )
    }


    private lateinit var mAdapter: AdapterIncome
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_transitions)
        mAdapter = AdapterIncome(this, items) { income, view ->

            //scaleDownAnimation
            val animator = mAdapter.getScaleDownAnimator(false,income)
            animator.start()
            animator.doOnEnd {

                Intent(this, IncomeDetailsActivity::class.java).apply {
                    putExtra("item", income)

                    val options =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            this@TransitionAnimationActivity,
                            view as CardView,
                            "income"
                        )

                    startActivity(this, options.toBundle())

                    mAdapter.getScaleDownAnimator(true, income).apply {
                        startDelay = 300L
                        start()
                    }
                }

            }
        }

        recyclerIncome.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@TransitionAnimationActivity)
            layoutAnimation =
                AnimationUtils.loadLayoutAnimation(
                    this@TransitionAnimationActivity,
                    R.anim.layout_animation_fall_down
                )

        }

    }
}
