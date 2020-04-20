package com.abualgait.msayed.egdroidathomechallenge

import android.os.Bundle

class SplashScreenAnimation : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(SplashScreenView(context = this))
    }
}
