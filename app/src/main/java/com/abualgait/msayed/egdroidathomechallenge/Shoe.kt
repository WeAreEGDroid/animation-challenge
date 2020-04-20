package com.abualgait.msayed.egdroidathomechallenge

import androidx.annotation.DrawableRes

data class Shoe(
    @DrawableRes var image: Int
    , var type: String
    , var model: String
    , var details: String
    , var price: String
    , var rate: String
    , var isOpen: Boolean = false
    , var isCurrent: Boolean = false
    , var progress: Float = 0f

)
