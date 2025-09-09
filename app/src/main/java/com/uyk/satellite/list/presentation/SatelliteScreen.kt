package com.uyk.satellite.list.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
            modifier = Modifier.fillMaxSize()
        ) {
            // Arama çubuğu
            OutlinedTextField(
                value = query,
                onValueChange = satelliteViewModel::onQueryChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = {
                    Text(
                        "Search",
                        color = Color.Gray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_search_24), // Arama ikonu
                        contentDescription = "Search Icon"
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.Gray
                )
            )

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
                        contentPadding = PaddingValues(16.dp),
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
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