package com.uyk.satellite.util

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

fun formatCost(cost: Long): String {
    // #,### formatı, binlik ayırıcıları kullanır.
    // Locale.getDefault() ile sistemin yerel ayarlarını kullanarak
    // doğru ayırıcıyı (nokta veya virgül) otomatik olarak seçer.
    val formatter = DecimalFormat("#.###", DecimalFormatSymbols.getInstance(Locale.getDefault()))
    return formatter.format(cost)
}