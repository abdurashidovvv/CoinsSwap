package com.example.coinsswap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.coinsswap.presentation.main_screen.MainScreen
import com.example.coinsswap.presentation.main_screen.MainScreenViewModel
import com.example.coinsswap.presentation.theme.CoinsSwapTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainScreenViewModel = hiltViewModel()
            CoinsSwapTheme {
                MainScreen(
                    state = viewModel.state,
                    onEvent = viewModel::onEvent
                )
            }
        }
    }
}
