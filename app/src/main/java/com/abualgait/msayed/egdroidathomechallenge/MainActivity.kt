package com.abualgait.msayed.egdroidathomechallenge

import android.content.Intent
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewsListeners()
    }

    private fun initViewsListeners() {
        splash_screen_animation.setOnClickListener(this)
        transition_animation.setOnClickListener(this)
        view_transformation_animation.setOnClickListener(this)
        view_pager_animations.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.splash_screen_animation -> {
                Intent(this, SplashScreenAnimation::class.java).apply {
                    startActivity(this)
                }
            }
            R.id.transition_animation -> {
                Intent(this, TransitionAnimationActivity::class.java).apply {
                    startActivity(this)
                }
            }

            R.id.view_transformation_animation -> {
                Intent(this, ViewTransformationAnimation::class.java).apply {
                    startActivity(this)
                }

            }

            R.id.view_pager_animations -> {
                Intent(this, ShoeBoxes::class.java).apply {
                    startActivity(this)
                }

            }
        }

    }
}
