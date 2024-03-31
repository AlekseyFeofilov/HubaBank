package ru.hits.hubabank.presentation.navigation

import android.content.Intent
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import ru.hits.hubabank.presentation.enter.login.LoginDestination
import ru.hits.hubabank.presentation.enter.login.LoginScreen
import ru.hits.hubabank.presentation.enter.registration.RegistrationDestination
import ru.hits.hubabank.presentation.enter.registration.RegistrationScreen
import ru.hits.hubabank.presentation.main.MainDestination

fun NavGraphBuilder.enterDestinations(navController: NavController) {
    composable(
        route = LoginDestination.routeTemplate,
        arguments = listOf(navArgument(LoginDestination.tokenSSO) { type = NavType.StringType; nullable = true }),
        deepLinks = listOf(navDeepLink {
            uriPattern = "huba://enter?${LoginDestination.tokenSSO}={${LoginDestination.tokenSSO}}"
            action = Intent.ACTION_VIEW
        })
    ) {
        LoginScreen(
            onOpenRegistrationScreen = {
                navController.navigate(RegistrationDestination.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            onOpenMainScreen = {
                navController.navigate(MainDestination.route) {
                    popUpTo(navController.graph.id)
                }
            },
        )
    }
    composable(RegistrationDestination.route) {
        RegistrationScreen(
            onOpenLoginScreen = {
                navController.navigate(LoginDestination.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            onOpenMainScreen = {
                navController.navigate(MainDestination.route) {
                    popUpTo(navController.graph.id)
                }
            },
        )
    }
}
