/*
* Copyright 2026 Denis Githuku
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* https://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.githukudenis.smartduka

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.githukudenis.smartduka.designsystem.ui.SmartDukaAppTheme
import com.githukudenis.smartduka.ui.components.MainScaffold
import com.githukudenis.smartduka.ui.navigation.MainNavGraph
import com.githukudenis.smartduka.ui.navigation.rememberAppState

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartDukaAppTheme {
                val appState = rememberAppState()
                MainScaffold(modifier = Modifier.fillMaxSize(), appState = appState) { innerPadding ->
                    MainNavGraph(modifier = Modifier.padding(innerPadding), appState = appState)
                }
            }
        }
    }
}

@Composable
fun GettingStarted(
    text: String = "Hello there",
    modifier: Modifier = Modifier.background(color = Color.LightGray)
) {
    Text(text = text, modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SmartDukaAppTheme { GettingStarted() }
}
