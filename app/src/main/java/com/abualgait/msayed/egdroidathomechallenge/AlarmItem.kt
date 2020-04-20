package com.abualgait.msayed.egdroidathomechallenge

import java.io.Serializable
import java.sql.Timestamp

data class AlarmItem(
    var time: String
    , var iSet: Boolean = false
    , var indecesDayOfWeek: MutableSet<Int>
) : Serializable {

    val formattedTime: String
        get() = if (time.isNotEmpty()) time else timeNow

}
