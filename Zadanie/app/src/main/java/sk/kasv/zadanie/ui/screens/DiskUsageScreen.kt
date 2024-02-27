// DiskUsageScreen.kt
package sk.kasv.zadanie.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import sk.kasv.zadanie.AppInfo
import android.text.format.Formatter
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.platform.LocalContext

@Composable
fun DiskUsageScreen(
    totalDiskSpace: String,
    appUsages: List<AppInfo>,
    onUninstallApp: (context: Context, packageName: String) -> Unit // Modified callback function
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Text(
                text = "Disk Usage",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Display total disk space
            Text(text = "Total Disk Space: $totalDiskSpace GB")
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Display app usages
        items(appUsages) { appUsage ->
            AppUsageItem(appUsage, onUninstallApp)
        }
    }
}
@Composable
fun AppUsageItem(
    appUsage: AppInfo,
    onUninstallApp: (context: Context, packageName: String) -> Unit
) {
    val context = LocalContext.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = appUsage.name)
        Text(text = "${Formatter.formatFileSize(context, appUsage.size)}")

        // Add uninstall button
        Button(
            onClick = { onUninstallApp(context, appUsage.packageName) }, // Pass context and package name
            colors = ButtonDefaults.buttonColors(contentColor = Color.White)
        ) {
            Text("Uninstall")
        }
    }
}
