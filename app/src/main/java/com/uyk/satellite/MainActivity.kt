package com.uyk.satellite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.uyk.satellite.core.navigation.SatelliteDestinations
import com.uyk.satellite.list.data.Satellite
import com.uyk.satellite.list.data.SatelliteStatus
import com.uyk.satellite.list.presentation.SatelliteViewModel
import com.uyk.satellite.ui.theme.SatellitePositionTheme
import org.koin.compose.viewmodel.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SatellitePositionTheme {
                SatelliteApp()
            }
        }
    }
}

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

@Composable
fun SatelliteScreen(onSatelliteClick: (String, Int) -> Unit) {
    val satelliteViewModel = koinViewModel<SatelliteViewModel>()
    val satellites by satelliteViewModel.filteredList.collectAsState()
    val itemCount = satellites.size

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

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                itemsIndexed(satellites) { index, satellite ->
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
}

@Composable
fun HorizontalDivider() {
    Divider(
        color = Color.LightGray,
        thickness = 1.dp,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SatellitePositionTheme {
    }
}