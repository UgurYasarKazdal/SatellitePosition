package com.uyk.satellite.list.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uyk.satellite.list.data.Satellite
import com.uyk.satellite.list.data.SatelliteStatus

@Composable
fun SatelliteItem(satellite: Satellite, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Uydu durumuna göre renkli nokta

        val color = when (satellite.status) {
            SatelliteStatus.ACTIVE -> Color.Green
            SatelliteStatus.PASSIVE -> Color.Red
        }

        Box(
            modifier = Modifier
                .size(12.dp)
                .background(color, shape = CircleShape)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Uydu adı ve durumu
        Column {
            Text(
                text = satellite.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = satellite.status.name.lowercase().replaceFirstChar { it.uppercase() },
                fontSize = 14.sp,
            )
        }
    }
}