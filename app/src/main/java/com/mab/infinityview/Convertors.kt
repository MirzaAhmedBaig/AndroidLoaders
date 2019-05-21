package com.mab.infinityview

import android.content.res.Resources


/**
 * Created by Mirza Ahmed Baig on 2019-05-21.
 * Avantari Technologies
 * mirza@avantari.org
 */
fun Int.dpToPx(): Int {
    return ((this * Resources.getSystem().displayMetrics.density).toInt())
}

fun Float.dpToPx(): Float {
    return ((this * Resources.getSystem().displayMetrics.density))
}