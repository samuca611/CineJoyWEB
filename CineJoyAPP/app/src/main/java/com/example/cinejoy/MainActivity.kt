package com.example.cinejoy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.cinejoy.ui.theme.CineJoyTheme
import com.example.cinejoy.ui.navigation.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CineJoyTheme {
                AppNavigation()
            }
        }
    }
}
