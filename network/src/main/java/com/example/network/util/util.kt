package com.example.network.util

import java.text.DecimalFormat


fun Int.priceFormat() : String =
    DecimalFormat("###,###").format(this).plus("원")