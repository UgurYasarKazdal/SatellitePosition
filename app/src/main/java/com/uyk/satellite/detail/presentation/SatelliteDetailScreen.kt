package com.uyk.satellite.detail.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uyk.satellite.detail.data.SatelliteDetail
import org.koin.compose.viewmodel.koinViewModel
import java.text.DecimalFormat


@Composable
fun SatelliteDetailScreen(satelliteName: String, satelliteId: Int) {
    // Gerçek uygulamada, bu id ile ViewModel veya Repository'den detayları alırsınız.
    // Şimdilik, mock data kullanalım.
    val satelliteDetailViewModel = koinViewModel<SatelliteDetailViewModel>()

    val uiState by satelliteDetailViewModel.uiState.collectAsState()

    satelliteDetailViewModel.getSatelliteDetail(satelliteId = satelliteId.toString())

    when (uiState) {
        is UiState.Loading -> {
            // Yüklenme durumunda gösterilecek UI
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is UiState.Success -> {
            val data = (uiState as UiState.Success).data

            DetailBody(satelliteName = satelliteName, satelliteDetail = data)
        }

        is UiState.Error -> {
            // Hata durumunda gösterilecek UI
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Bir hata oluştu. Lütfen tekrar deneyin.")
            }
        }

    }

}

@Composable
fun DetailBody(satelliteName: String, satelliteDetail: SatelliteDetail) {
// Eğer detay bulunamazsa bir hata mesajı gösterebilirsiniz
    if (satelliteDetail == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Uydu detayları bulunamadı.", color = Color.Gray, fontSize = 18.sp)
        }
        return
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top // İçeriği en üste hizala
        ) {
            Spacer(modifier = Modifier.height(60.dp)) // Üstten boşluk bırak

            // Uydu Adı (Örnek: "Starship-1" - bu bilgi list item'dan gelmeli veya buradan çekilmeli)
            // Şu an sadece örnek olarak gösteriyoruz, gerçekte ismi de almalısınız.
            Text(
                text = satelliteName, // Geçici isim
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            // İlk Uçuş Tarihi
            Text(
                text = satelliteDetail.firstFlightDate,
                fontSize = 18.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(48.dp)) // Büyük boşluk

            // Height/Mass
            DetailRow(
                label = "Height/Mass:",
                value = satelliteDetail.heightMass
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Cost
            DetailRow(
                label = "Cost:",
                value = satelliteDetail.cost // Maliyeti formatla
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Last Position
            DetailRow(
                label = "Last Position:",
                value = satelliteDetail.lastPosition
            )
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center, // Metinleri yatayda ortala
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black
        )
        Spacer(modifier = Modifier.width(8.dp)) // Etiket ve değer arasına boşluk
        Text(
            text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

// Maliyet formatlama fonksiyonu
fun formatCost(cost: Long): String {
    val formatter = DecimalFormat("#,###")
    return formatter.format(cost)
}