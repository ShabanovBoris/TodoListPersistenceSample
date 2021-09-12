package com.bosha.notespersistencesample.data.utils

import java.text.SimpleDateFormat
import java.util.*

private val isoFormatter = SimpleDateFormat("MM-dd HH:mm", Locale("en"))

fun Date.toISOFormat() : String = isoFormatter.format(this)

//fun Date.plusDays(days: Int): Date {
//    return Date(this.time + (days * ONE_DAY_MS))
//}
//
//fun Date.plusMillis(millis: Long): Date {
//    return Date(this.time + millis)
//}