package com.githukudenis.smartduka

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.githukudenis.smartduka.ui.theme.SmartDukaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartDukaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GettingStarted(
                        text = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun GettingStarted(
    text: String = "Hello there",
    modifier: Modifier = Modifier.background(
        color = Color.LightGray
    )
) {
    Text(text = text)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SmartDukaTheme {
        GettingStarted()
    }
}