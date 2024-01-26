import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import sk.kasv.projekt3.ui.theme.ProjektTheme

@Composable
fun OptimizationScreen() {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Optimization", style = MaterialTheme.typography.headlineMedium)

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = { handleBatteryOptimization(context) }) {
                Text("Battery")
            }
            Button(onClick = { handleDiskOptimization(context) }) {
                Text("Disk")
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = { handleNetworkOptimization(context) }) {
                Text("Network")
            }
            Button(onClick = { handleRAMOptimization(context) }) {
                Text("RAM")
            }
        }
    }
}

private fun handleBatteryOptimization(context: android.content.Context) {
    showToast(context, "Battery optimization clicked")
}

private fun handleDiskOptimization(context: android.content.Context) {
    showToast(context, "Disk optimization clicked")
}

private fun handleNetworkOptimization(context: android.content.Context) {
    showToast(context, "Network optimization clicked")
}

private fun handleRAMOptimization(context: android.content.Context) {
    showToast(context, "RAM optimization clicked")
}

private fun showToast(context: android.content.Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Preview(showBackground = true)
@Composable
fun OptimizationScreenPreview() {
    ProjektTheme {
        OptimizationScreen()
    }
}