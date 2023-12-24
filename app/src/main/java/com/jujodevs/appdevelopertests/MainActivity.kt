package com.jujodevs.appdevelopertests

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.jujodevs.appdevelopertests.ui.DeveloperTestsApp
import com.jujodevs.appdevelopertests.ui.DeveloperTestsScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    companion object {
        private const val DELAY = 2000L
        private var showSplashScreen = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashScreen()
        setContent {
            DeveloperTestsScreen {
                DeveloperTestsApp() { finish() }
            }
        }
    }

    private fun splashScreen() {
        installSplashScreen()
            .setKeepOnScreenCondition { showSplashScreen }
        delaySplashScreen()
    }

    private fun delaySplashScreen() {
        lifecycleScope.launch {
            delay(DELAY)
            showSplashScreen = false
        }
    }
}
