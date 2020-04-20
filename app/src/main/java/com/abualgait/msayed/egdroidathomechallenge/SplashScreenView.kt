package com.abualgait.msayed.egdroidathomechallenge

import android.animation.Animator
import android.animation.AnimatorSet
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.animation.doOnEnd


class SplashScreenView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    val set = AnimatorSet()
    @ColorInt
    val textColor = resources.getColor(R.color.colorSilver)

    private lateinit var imgs: Array<ImageView?>
    private lateinit var textView: TextView
    private var circle = GradientDrawable()

    val layoutParamsContainer =
        LayoutParams(100.px, ViewGroup.LayoutParams.WRAP_CONTENT)

    init {

        orientation = VERTICAL
        gravity = Gravity.CENTER
        background = resources.getDrawable(R.color.colorBackground)
        val layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        setLayoutParams(layoutParams)
        drawCircularBulbs(20.px)
        drawFadingText()
    }


    private fun animateView() {


        for ((index, it) in imgs.withIndex()) {
            if (index == imgs.size - 1) {
                set.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(p0: Animator?) {
                    }

                    override fun onAnimationEnd(p0: Animator?) {
                        removeSetListeners()
                        animateView()
                        fadTextView()
                    }

                    override fun onAnimationCancel(p0: Animator?) {
                    }

                    override fun onAnimationStart(p0: Animator?) {
                    }

                })

            }
            val scaleDown =
                getValueAnimator(
                    false, DURATION, DURATION * index, AccelerateInterpolator()
                ) { progress ->
                    it?.scaleX = progress
                    it?.scaleY = progress

                }

            val scaleUp =
                getValueAnimator(
                    true,
                    DURATION,
                    DURATION * index,
                    AccelerateInterpolator()
                ) { progress ->

                    it?.scaleX = progress
                    it?.scaleY = progress
                }


            set.play(scaleDown).before(scaleUp)
            set.start()

        }


    }

    private fun fadTextView() {
        val alpha =
            getValueAnimator(
                false, DURATION * 4, DURATION, AccelerateInterpolator()
            ) { progress ->
                textView.alpha = progress
            }

        alpha.doOnEnd {
            textView.alpha = 1f
        }
        alpha.start()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        imgs.reverse()
        animateView()
    }

    private fun drawCircularBulbs(size: Int) {
        removeAllViews()
        imgs = arrayOfNulls<ImageView>(OBJECT_SIZE)

        circle.shape = GradientDrawable.OVAL
        circle.setColor(textColor)

        layoutParamsContainer.gravity = Gravity.CENTER
        layoutParamsContainer.setMargins(MARGIN, MARGIN, MARGIN, MARGIN)

        val rel = arrayOfNulls<LinearLayout>(OBJECT_SIZE)
        for (i in 0 until OBJECT_SIZE) {
            rel[i] = LinearLayout(context)
            rel[i]!!.gravity = if (i % 2 == 0) Gravity.START else Gravity.END
            rel[i]!!.layoutParams = layoutParamsContainer
            imgs[i] = ImageView(context)

            imgs[i]?.setBackgroundDrawable(circle)
            imgs[i]?.layoutParams = LayoutParams(size * (i + 1), size * (i + 1))
            rel[i]!!.addView(imgs[i])
            addView(rel[i])
        }

    }

    private fun drawFadingText() {
        textView = TextView(context)
        textView.text = context.getString(R.string.egdroid_challenge_str).toUpperCase()
        textView.setTextColor(textColor)
        textView.textSize = 8.px.toFloat()
        val ly = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        textView.layoutParams = ly
        ly.setMargins(MARGIN, MARGIN, MARGIN, MARGIN)
        addView(textView)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        removeSetListeners()
    }

    private fun removeSetListeners() {
        set.removeAllListeners()
        set.end()
        set.cancel()
    }

    companion object {
        private var OBJECT_SIZE = 4
        private var DURATION = 600L
        private var MARGIN = 15.px
    }
}