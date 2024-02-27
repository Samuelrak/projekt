// BatteryScreen.kt
package sk.kasv.zadanie.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sk.kasv.zadanie.ui.theme.ZadanieTheme1

@Composable
fun BatteryScreen(
    batteryPercentage: Int,
    remainingTime: String,
    onOptimizeClick: () -> Unit // Callback for optimization button click
) {
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
            shape = CircleShape,
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
        Button(
            onClick = onOptimizeClick, // Call the provided callback function
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
        ) {
            Text("Optimize")
        }
        // TODO: Add the list of applications with the battery usage time.
    }
}



@Preview(showBackground = true)
@Composable
fun OptimizationScreenPreview() {
    ZadanieTheme1 {

    }
}