package com.example.mymanagement.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.naver.maps.geometry.LatLng
import kotlin.math.roundToInt

fun <T> Activity.startActivity(clazz: Class<T>) {
    startActivity(Intent(this, clazz))
}

fun <T> Activity.startActivity(clazz: Class<T>, vararg flags : Int) {
    startActivity(Intent(this, clazz).also { intent ->
        flags.forEach {
            intent.flags = it
        }
    })
}

fun <T> Context.startActivity(clazz: Class<T>, vararg flags : Int) {
    startActivity(Intent(this, clazz).also { intent ->
        flags.forEach {
            intent.flags = it
        }
    })
}

fun <T> Activity.createIntent(clazz: Class<T>) =
    Intent(this, clazz)


fun <T> Activity.createIntent(clazz: Class<T>, vararg flags : Int) =
    Intent(this, clazz).also { intent ->
        flags.forEach {
            intent.flags = it
        }
    }

fun getSeoulLatLng() = LatLng(37.5527752,126.96857219999998)

fun dpToPx(context: Context?, dp: Int): Int {
    val density = context?.resources?.displayMetrics?.density ?: 1.0f
    return (dp.toFloat() * density).roundToInt()
}

fun spToPx(context: Context?, sp: Int): Float {
    val density = context?.resources?.displayMetrics?.scaledDensity ?: 1.0f
    return sp * density
}

/**
 * 키보드 숨김 처리
 * */
fun View.hideKeyBoard(){
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(windowToken, 0)
}