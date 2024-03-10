package ru.hits.hubabank.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ru.hits.hubabank.presentation.enter.login.LoginDestination

@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = LoginDestination.route,
        modifier = modifier.fillMaxSize().systemBarsPadding(),
    ) {
        enterDestinations(navController)
        mainDestinations(navController)
        billAndCreditDestinations(navController)
    }
}
