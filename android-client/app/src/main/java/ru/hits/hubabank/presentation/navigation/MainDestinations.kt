package ru.hits.hubabank.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.hits.hubabank.presentation.bill.BillInfoDestination
import ru.hits.hubabank.presentation.credit.adding.CreditAddingDestination
import ru.hits.hubabank.presentation.credit.info.CreditInfoDestination
import ru.hits.hubabank.presentation.enter.login.LoginDestination
import ru.hits.hubabank.presentation.main.MainDestination
import ru.hits.hubabank.presentation.main.MainScreen
import ru.hits.hubabank.presentation.profile.ProfileDestination
import ru.hits.hubabank.presentation.profile.ProfileScreen

fun NavGraphBuilder.mainDestinations(navController: NavController) {
    composable(MainDestination.route) {
        MainScreen(
            onOpenProfileScreen = {
                navController.navigate(ProfileDestination.route)
            },
            onOpenBillInfoScreen = { billId ->
                navController.navigate(BillInfoDestination.routeWithArg(billId))
            },
            onOpenCreditInfoScreen = { creditId ->
                navController.navigate(CreditInfoDestination.routeWithArg(creditId))
            },
            onOpenCreditAddingScreen = {
                navController.navigate(CreditAddingDestination.route)
            },
        )
    }
    composable(ProfileDestination.route) {
        ProfileScreen(
            onNavigateBack = {
                navController.navigateUp()
            },
            onExit = {
                navController.navigate(LoginDestination.route) {
                    popUpTo(navController.graph.id)
                }
            }
        )
    }
}
