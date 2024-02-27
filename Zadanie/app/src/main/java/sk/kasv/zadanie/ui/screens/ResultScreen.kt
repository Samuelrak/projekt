package sk.kasv.zadanie.ui.screens

import androidx.compose.material3.MaterialTheme

// ResultsScreen.kt
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ResultsScreen(uninstalledApps: List<AppInfo>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Uninstalled Apps",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Display app usages
        for (appInfo in uninstalledApps) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = appInfo.name)
                Text(text = "${appInfo.size} bytes") // Adjust the formatting as needed
                Text(text = appInfo.packageName)
            }
        }
    }
}
