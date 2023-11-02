package com.alsif.tingting.util.extension

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

private const val TAG = "DateUtil"
/** Long을 LocalDateTime으로 파싱 */
fun Long.parseLocalDateTime(): LocalDateTime {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDateTime.ofInstant(Instant.ofEpochMilli(this),
            ZoneId.systemDefault())
    } else {
        TODO("VERSION.SDK_INT < O")
    }
}

/** LocalDateTime을 Long으로 파싱 **/
fun LocalDateTime.parseLong(): Long {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        this.atZone(
            ZoneId.systemDefault()).toInstant().toEpochMilli()
    } else {
        TODO("VERSION.SDK_INT < O")
    }
}

/** LocalDateTime을 String(년-일-시)으로 파싱 */
fun LocalDateTime.toDateString(): String {
    return this.toString().subSequence(0, 10).toString()
}