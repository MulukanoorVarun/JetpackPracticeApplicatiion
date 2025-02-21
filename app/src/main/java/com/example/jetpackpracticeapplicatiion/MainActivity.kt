package com.example.jetpackpracticeapplicatiion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpackpracticeapplicatiion.ui.theme.AppNavigator
import com.example.jetpackpracticeapplicatiion.ui.theme.ProductAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProductAppTheme {
                AppNavigator()
            }
        }
    }
}