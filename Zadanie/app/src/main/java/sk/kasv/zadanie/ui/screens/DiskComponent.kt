package sk.kasv.zadanie

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DiskComponent(
    totalDiskSpace: String,
    appUsages: List<AppUsage>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Disk Usage",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Display total disk space
        Text(text = "Total Disk Space: $totalDiskSpace GB")

        // Display app usages
        for (appUsage in appUsages) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = appUsage.name)
                Text(text = "${appUsage.sizeGB} GB")
            }
        }
    }
}