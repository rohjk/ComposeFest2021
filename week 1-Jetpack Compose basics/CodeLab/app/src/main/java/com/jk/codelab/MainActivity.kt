package com.jk.codelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jk.codelab.ui.theme.CodeLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CodeLabTheme {
                MyApp()
            }
        }
    }
}

@Composable
private fun MyApp(names: List<String> = listOf("world", "compose")) {
    Column(
        modifier = Modifier.padding(
            vertical = 4.dp
        )
    ) {
        for (name in names) {
            Greeting(name = name)
        }
    }
}

@Composable
fun Greeting(name: String) {
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(
            vertical = 4.dp,
            horizontal = 8.dp
        )
    ) {
        Row(
            modifier = Modifier.padding(24.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Hello,")
                Text(text = name)
            }
            OutlinedButton(onClick = {}) {
                Text(text = "Show more")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    CodeLabTheme {
        MyApp()
    }
}
