package ru.hits.hubabank.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ru.hits.hubabank.presentation.enter.login.LoginDestination

@Composable
fun AppNavHost(modifier: Modifier = Modifier, navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = LoginDestination.routeTemplate,
        modifier = modifier.fillMaxSize().systemBarsPadding(),
    ) {
        enterDestinations(navHostController)
        mainDestinations(navHostController)
        billAndCreditDestinations(navHostController)
    }
}
