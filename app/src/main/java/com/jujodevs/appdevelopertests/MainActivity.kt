package com.jujodevs.appdevelopertests

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jujodevs.appdevelopertests.ui.DeveloperTestsApp
import com.jujodevs.appdevelopertests.ui.DeveloperTestsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DeveloperTestsScreen {
                DeveloperTestsApp() { finish() }
            }
        }
    }
}
