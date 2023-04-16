package com.example.network.util

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


fun Int.priceFormat(): String =
    DecimalFormat("###,###").format(this).plus("원")

fun convertToRFC5545(dateTimeString: String, isAllDay: Boolean = false): String {
    try {
        val inputFormat = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault())
        if (isAllDay) {
            inputFormat.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        }

        val outputFormat = SimpleDateFormat(
            if (isAllDay) "yyyy-MM-dd'T'00:00:00'Z'" else "yyyy-MM-dd'T'HH:mm:ss'Z'",
            Locale.getDefault()
        )
        outputFormat.timeZone = TimeZone.getTimeZone("UTC")

        val dateTime = inputFormat.parse(dateTimeString) ?: return ""

        return outputFormat.format(dateTime)
    } catch (e: Exception) {
        return ""
    }
}

fun convertToDateTime(dateTimeString: String?, format: String = "yyyy.MM.dd"): String {
    if (dateTimeString == null) return ""

    try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")
        val outputFormat = SimpleDateFormat(format, Locale.getDefault())
        outputFormat.timeZone = TimeZone.getTimeZone("Asia/Seoul")

        val dateTime = inputFormat.parse(dateTimeString) ?: return ""

        return outputFormat.format(dateTime)
    } catch (e: Exception) {
        e.printStackTrace()
        return ""
    }
}
