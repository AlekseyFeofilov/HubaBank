package ru.hits.hubabank.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.hits.hubabank.presentation.core.LocalSnackbarController
import ru.hits.hubabank.presentation.core.SnackbarController
import ru.hits.hubabank.presentation.navigation.AppNavHost
import ru.hits.hubabank.presentation.ui.components.ErrorSnackbar
import ru.hits.hubabank.presentation.ui.theme.HubaTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val viewModel: MainActivityViewModel = viewModel()
            val state by viewModel.screenState.collectAsState()

            val navController = rememberNavController()
            HubaTheme(
                darkTheme = state.isDarkTheme,
            ) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val snackbarHostState = remember { SnackbarHostState() }
                    CompositionLocalProvider(
                        LocalSnackbarController providesDefault SnackbarController(
                            snackbarHostState,
                            rememberCoroutineScope(),
                            this.resources,
                        )
                    ) {
                        Scaffold(
                            snackbarHost = {
                                ErrorSnackbar(
                                    snackbarHostState = snackbarHostState,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .navigationBarsPadding()
                                        .padding(start = 4.dp, bottom = 24.dp, end = 4.dp),
                                )
                            },
                            contentWindowInsets = WindowInsets(0, 0, 0, 0),
                        ) {
                            AppNavHost(navHostController = navController, modifier = Modifier.padding(it))
                        }
                    }
                }
            }
        }
    }
}
