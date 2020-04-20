package com.abualgait.msayed.egdroidathomechallenge

import android.animation.AnimatorSet
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.recyclerview.widget.LinearLayoutManager
import com.abualgait.msayed.egdroidathomechallenge.ArcAnimator.ArcMetric
import com.abualgait.msayed.egdroidathomechallenge.ArcAnimator.ArcUtils
import com.abualgait.msayed.egdroidathomechallenge.ArcAnimator.Side
import kotlinx.android.synthetic.main.activity_view_transformation_animation.*

class ViewTransformationAnimation : AppCompatActivity() {

    var items = arrayListOf<AlarmItem>().apply {
        add(AlarmItem(timeNow, true, mutableSetOf(1, 3)))
        add(AlarmItem(timeNow, false, mutableSetOf(1, 4)))
        add(AlarmItem(timeNow, false, mutableSetOf(1, 3)))
        add(AlarmItem(timeNow, false, mutableSetOf(1, 2, 3, 4, 5, 6)))
        add(AlarmItem(timeNow, true, mutableSetOf(5, 1)))
        add(AlarmItem(timeNow, true, mutableSetOf(1)))
    }


    private lateinit var mAdapter: AdapterAlarms
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_transformation_animation)

        fabAdd.setOnClickListener {
            applayForwardAnimation()
        }


        mAdapter = AdapterAlarms(this, items) { income, view ->


        }

        recyclerAlrams.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@ViewTransformationAnimation)


        }
    }

    private fun applayForwardAnimation() {

        val recyclerViewAnimator =
            getValueAnimator(false, DURATION, 0L, AccelerateInterpolator()) { progress ->
                recyclerAlrams.alpha = progress
            }

        val recyclerViewAnimatorX =
            getValueAnimator(true, DURATION, 0L, AccelerateInterpolator()) { progress ->
                recyclerAlrams.translationX = -progress * recyclerAlrams.width / 4
            }


        val set = AnimatorSet()
        set.play(recyclerViewAnimator).with(recyclerViewAnimatorX)
        set.start()
        set.doOnEnd {
            recyclerAlrams.hide()
        }


        val endX = containerView.x
        val endWidth = containerView.width
        val endHeight = containerView.height
        var fabx2 = 0f


        val arcMetric = ArcMetric.evaluate(
            fabAdd.x,
            fabAdd.y,
            frameContainer.width / 2f - fabAdd.width / 2f,
            (coordinator.height - frameContainer.height / 2f - fabAdd.height / 2f),
            90f,
            Side.RIGHT
        )


        val arcShapeAnimator =
            getValueAnimator(true, DURATION, 0L, AccelerateInterpolator()) { progress ->
                val degree = arcMetric.getDegree(progress).toDouble()
                fabAdd.x = arcMetric.axisPoint.x + arcMetric.mRadius * ArcUtils.cos(degree)
                fabAdd.y = arcMetric.axisPoint.y - arcMetric.mRadius * ArcUtils.sin(degree)
            }

        arcShapeAnimator.start()

        val circularRevealAnimator =
            getValueAnimator(true, DURATION / 2, 0L, AccelerateInterpolator()) { progress ->
                if (progress <= 0.8f) {
                    if (endWidth >= 0) fabAdd.layoutParams.width =
                        getProgressValue(fabAdd.width * 1f, endWidth * 1f, progress / 0.8f).toInt()
                    if (endHeight >= 0) fabAdd.layoutParams.height =
                        getProgressValue(
                            fabAdd.height * 1f,
                            endHeight * 1f,
                            progress / 0.8f
                        ).toInt()
                    if (endWidth >= 0 || endHeight >= 0) fabAdd.requestLayout()

                    if (endX >= 0) fabAdd.x = getProgressValue(fabx2, endX, progress)

                }
            }
        circularRevealAnimator.doOnEnd {
            containerView.show()
            fabAdd.visibility = View.INVISIBLE
        }
        arcShapeAnimator.doOnEnd {

            fabAdd.setImageDrawable(null)
            fabx2 = fabAdd.x

            circularRevealAnimator.start()
        }


    }

    private fun getProgressValue(start: Float, end: Float, progress: Float): Float =
        start + (end - start) * progress

    companion object {
        val DURATION = 800L

    }
}
