package sk.kasv.zadanie.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ZadanieTheme1(content: @Composable () -> Unit) {
    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            content()
        }
    }
}

@Composable
fun ZadanieTheme2(content: @Composable () -> Unit) {
    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            content()
        }
    }
}

@Composable
fun ZadanieTheme3(content: @Composable () -> Unit) {
    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            content()
        }
    }
}

@Composable
fun ZadanieTheme4(content: @Composable () -> Unit) {
    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ZadanieTheme1Preview() {
    ZadanieTheme1 {
        // Any composable can be previewed here
    }
}

@Preview(showBackground = true)
@Composable
fun ZadanieTheme2Preview() {
    ZadanieTheme2 {
        // Any composable can be previewed here
    }
}

@Preview(showBackground = true)
@Composable
fun ZadanieTheme3Preview() {
    ZadanieTheme3 {
        // Any composable can be previewed here
    }
}

@Preview(showBackground = true)
@Composable
fun ZadanieTheme4Preview() {
    ZadanieTheme4 {
        // Any composable can be previewed here
    }
}
