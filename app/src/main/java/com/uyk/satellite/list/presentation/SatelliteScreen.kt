package com.uyk.satellite.list.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uyk.satellite.R
import com.uyk.satellite.detail.presentation.UiState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SatelliteScreen(onSatelliteClick: (String, Int) -> Unit) {
    val satelliteViewModel = koinViewModel<SatelliteViewModel>()
    val satellites by satelliteViewModel.filteredList.collectAsState()

    val satelliteList = (satellites as? UiState.Success)?.data

    val itemCount = satelliteList?.size ?: 0

    val query by satelliteViewModel.query.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
        ) {
            Spacer(modifier = Modifier.height(16.dp)) // üst boşluk
            // Arama çubuğu
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(vertical = 4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_search_24),
                        contentDescription = "Search Icon",
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    BasicTextField(
                        value = query,
                        onValueChange = satelliteViewModel::onQueryChanged, singleLine = true,
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontSize = 14.sp
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        decorationBox = { innerTextField ->
                            if (query.isEmpty()) {
                                Text(
                                    text = "Search",
                                    color = Color.Gray,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                    lineHeight = 16.sp // daha sıkı görünüm
                                )
                            }
                            innerTextField()
                        }
                    )
                }
            }

            when (satellites) {
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
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(), // ekranı tamamen kaplasın
                        horizontalAlignment = Alignment.CenterHorizontally, // içerikleri yatayda ortala
                        contentPadding = PaddingValues(vertical = 16.dp),
                    ) {
                        if (satelliteList != null) {
                            itemsIndexed(satelliteList) { index, satellite ->
                                SatelliteItem(
                                    satellite = satellite,
                                    modifier = Modifier.clickable {
                                        // Tıklama olayında callback fonksiyonunu çağır
                                        onSatelliteClick(satellite.name, satellite.id.toInt())
                                    }
                                )

                                if (index < itemCount - 1) {
                                    HorizontalDivider()
                                }
                            }

                        }

                    }

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
    }
}