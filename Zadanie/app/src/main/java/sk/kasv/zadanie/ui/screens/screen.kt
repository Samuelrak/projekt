// Screen.kt
package sk.kasv.zadanie

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Debug
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import sk.kasv.zadanie.ui.theme.ZadanieTheme1
import androidx.navigation.NavController

sealed class Screen {
    object Optimization : Screen()
    object Battery : Screen()
    object Network : Screen()
}

@Composable
fun LinearProgressIndicatorCustom(progress: Float) {
    // Define your custom linear progress indicator here
}

@Composable
fun MemoryStatusComponent() {
    // Define your memory status component here
}



fun OptimizationScreen() {
    TODO("Not yet implemented")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptimizationOption(name: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier.padding(8.dp),
        onClick = onClick
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = name, style = MaterialTheme.typography.titleLarge)
        }
    }
}

@Composable
fun OptimizationScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Optimization",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OptimizationOption("Battery") {
                // Navigate to the BatteryScreen when "Battery" is clicked
                navController.navigate("batteryScreen/50/10h 30m") // Example values for battery percentage and remaining time
            }
            OptimizationOption("Disk") {
                // Navigate to the Disk screen when "Disk" is clicked
                navController.navigate("diskScreen")
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OptimizationOption("Network") {
                // Navigate to the NetworkScreen when "Network" is clicked
                navController.navigate("networkScreen")
            }
            OptimizationOption("Results") {
                // Handle RAM Optimization click
                navController.navigate("resultsScreen")
            }
        }
    }
}

@Composable
fun BatteryScreen() {
    val batteryPercentage by rememberBatteryPercentage()
    val remainingTime = rememberBatteryTime(batteryPercentage)
    val appRamUsageList by rememberAppRamUsageList()

    // State to track whether optimization is in progress
    var isOptimizing by remember { mutableStateOf(false) }

    // Dynamic variable to control the optimization process
    var optimizationProgress by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Battery",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(24.dp))
        Card(
            modifier = Modifier.size(200.dp),
            colors = CardDefaults.cardColors(containerColor = Color.LightGray)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = "$batteryPercentage%",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = remainingTime,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(24.dp))

        for (appRamUsage in appRamUsageList) {
            Text(
                text = "App: ${appRamUsage.appName}, RAM Usage: ${appRamUsage.ramUsage} MB",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Button to optimize battery and applications
        Button(
            onClick = {
                // Set isOptimizing to true to indicate optimization is in progress
                isOptimizing = true

                // Call optimization functions here
                optimizeBattery(optimizationProgress)
                optimizeApplications(optimizationProgress)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
        ) {
            Text("Optimize")
        }

        // Show a message or loading indicator while optimization is in progress
        if (isOptimizing) {
            Text("Optimizing...")
        }
    }
}

fun optimizeBattery(progress: Int) {
    // Simulate battery optimization by printing a message
    println("Optimizing battery at progress: $progress%")
}

fun optimizeApplications(progress: Int) {
    // Simulate application optimization by printing a message
    println("Optimizing applications at progress: $progress%")
}

data class AppRamUsage(val appName: String, val ramUsage: Int)

@Composable
fun rememberAppRamUsageList(): State<List<AppRamUsage>> {
    val context = LocalContext.current
    val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
    val runningAppProcesses = activityManager?.runningAppProcesses

    val appRamUsageList = remember { mutableStateOf<List<AppRamUsage>>(emptyList()) }

    LaunchedEffect(true) {
        val list = mutableListOf<AppRamUsage>()

        val memoryInfoArray = arrayOfNulls<Debug.MemoryInfo>(runningAppProcesses?.size ?: 0)
        runningAppProcesses?.forEachIndexed { index, processInfo ->
            memoryInfoArray[index] = Debug.MemoryInfo()
            Debug.getMemoryInfo(memoryInfoArray[index]) // Get MemoryInfo for each process
        }

        memoryInfoArray.forEachIndexed { index, memoryInfo ->
            val ramUsage = memoryInfo?.getTotalPss() ?: 0 / (1024 * 1024) // Convert bytes to megabytes
            list.add(AppRamUsage(runningAppProcesses?.get(index)?.processName ?: "", ramUsage.toInt()))
        }

        appRamUsageList.value = list
    }

    return appRamUsageList
}
@Composable
fun handleOptimizeButtonClick() {
    // TODO: Handle Battery Optimization logic here
    // You can implement your battery optimization logic or navigate to another screen
}

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

@Composable
fun rememberBatteryPercentage(): State<Int> {
    val context = LocalContext.current
    val batteryPercentage = remember { mutableStateOf(getBatteryPercentage(context)) }

    LaunchedEffect(true) {
        val batteryIntentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val batteryStatus: Intent? = context.registerReceiver(null, batteryIntentFilter)
        batteryPercentage.value = calculateBatteryPercentage(batteryStatus)
    }

    return batteryPercentage
}

private fun getBatteryPercentage(context: Context): Int {
    val batteryIntentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
    val batteryStatus: Intent? = context.registerReceiver(null, batteryIntentFilter)
    return calculateBatteryPercentage(batteryStatus)
}

private fun calculateBatteryPercentage(intent: Intent?): Int {
    intent?.let {
        val level = it.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        val scale = it.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        if (level != -1 && scale != -1) {
            return (level * 100 / scale.toFloat()).toInt()
        }
    }
    return 0
}

@Composable
fun rememberBatteryTime(batteryPercentage: Int): String {
    // Simple estimation based on battery percentage
    val remainingHours = (100 - batteryPercentage) / 10 // Assuming each 10% reduces 1 hour
    return "$remainingHours hours remaining"
}

@Composable
fun NetworkScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("57.21 MB", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicatorCustom(progress = 0.5721f) // Assuming 57.21 MB is 57.21%
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* TODO: Implement Test Network Connection */ },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("TEST")
        }
        Spacer(modifier = Modifier.height(24.dp))
        MemoryStatusComponent()
    }
}


@Preview(showBackground = true)
@Composable
fun OptimizationScreenPreview() {
    ZadanieTheme1 {

    }
}

@Preview(showBackground = true)
@Composable
fun NetworkScreenPreview() {
    ZadanieTheme1 {
        NetworkScreen()
    }
}
