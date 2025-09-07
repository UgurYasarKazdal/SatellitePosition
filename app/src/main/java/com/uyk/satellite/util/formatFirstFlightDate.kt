package com.uyk.satellite.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatFirstFlightDate(dateString: String): String {
    // 1. Giriş formatını tanımla: "YYYY-MM-DD"
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    
    // 2. String'i LocalDate nesnesine dönüştür
    val date = LocalDate.parse(dateString, inputFormatter)
    
    // 3. Çıkış formatını tanımla: "dd.MM.yyyy"
    // Locale.getDefault() ile sistemin varsayılan dilini kullanırız.
    val outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.getDefault())
    
    // 4. LocalDate'i yeni formata dönüştür ve döndür
    return date.format(outputFormatter)
}