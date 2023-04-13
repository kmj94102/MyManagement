package com.example.network.util

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


fun Int.priceFormat(): String =
    DecimalFormat("###,###").format(this).plus("원")

fun convertToRFC5545(dateTimeString: String): String {
    try {
        val inputFormat = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        val outputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        outputFormat.timeZone = TimeZone.getTimeZone("UTC")

        val dateTime = inputFormat.parse(dateTimeString) ?: return ""

        return outputFormat.format(dateTime)
    } catch (e: Exception) {
        return ""
    }
}

fun convertToDateTime(dateTimeString: String?): String {
    if (dateTimeString == null) return ""

    try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")
        val outputFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        outputFormat.timeZone = TimeZone.getTimeZone("Asia/Seoul")

        val dateTime = inputFormat.parse(dateTimeString) ?: return ""

        return outputFormat.format(dateTime)
    } catch (e: Exception) {
        e.printStackTrace()
        return ""
    }
}
