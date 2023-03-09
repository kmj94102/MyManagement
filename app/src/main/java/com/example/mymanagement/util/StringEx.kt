package com.example.mymanagement.util

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

fun minSecFormat(originSec: Int): String {
    val min = originSec / 60
    val sec = originSec % 60

    return if (min == 0 && sec == 0) {
        "곧 도착"
    } else if(min == 0) {
        "${sec.toString().padStart(2, '0')}초"
    } else {
        "${min}분 ${sec.toString().padStart(2, '0')}초"
    }
}

fun getToday(format: String = "HHmmss"): String {
    val today = Calendar.getInstance().time
    val formatter = SimpleDateFormat(format, Locale.KOREA)

    return formatter.format(today)
}