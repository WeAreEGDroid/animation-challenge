package com.abualgait.msayed.egdroidathomechallenge

import androidx.annotation.DrawableRes
import java.io.Serializable

data class Income(
    @DrawableRes var image: Int
    , var title: String
    , var amount: String
    , var date: String
    , var status: String
    , @DrawableRes var icon: Int
) : Serializable
