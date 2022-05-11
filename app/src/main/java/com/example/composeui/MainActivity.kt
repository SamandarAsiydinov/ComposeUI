package com.example.composeui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.composeui.expand.ExpandableScreen
import com.example.composeui.spin_calendar.SpinnerView
import com.example.composeui.ui.theme.ComposeUITheme
import com.example.composeui.viewmodel.ExpandableViewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<ExpandableViewModel>()

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeUITheme {
                SpinnerView()
            }
        }
    }
}