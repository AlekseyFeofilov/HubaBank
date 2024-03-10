package ru.hits.hubabank.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.hits.hubabank.presentation.enter.login.LoginDestination
import ru.hits.hubabank.presentation.enter.login.LoginScreen
import ru.hits.hubabank.presentation.enter.registration.RegistrationDestination
import ru.hits.hubabank.presentation.enter.registration.RegistrationScreen
import ru.hits.hubabank.presentation.main.MainDestination

fun NavGraphBuilder.enterDestinations(navController: NavController) {
    composable(LoginDestination.route) {
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
                navController.graph.setStartDestination(MainDestination.route)
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
                navController.graph.setStartDestination(MainDestination.route)
            },
        )
    }
}
