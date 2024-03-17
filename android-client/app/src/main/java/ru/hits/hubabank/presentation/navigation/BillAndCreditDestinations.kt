package ru.hits.hubabank.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.hits.hubabank.presentation.bill.BillInfoDestination
import ru.hits.hubabank.presentation.bill.BillInfoScreen
import ru.hits.hubabank.presentation.credit.adding.CreditAddingDestination
import ru.hits.hubabank.presentation.credit.adding.CreditAddingScreen
import ru.hits.hubabank.presentation.credit.info.CreditInfoDestination
import ru.hits.hubabank.presentation.credit.info.CreditInfoScreen
import ru.hits.hubabank.presentation.credit.rate.CreditRatesDestination
import ru.hits.hubabank.presentation.credit.rate.CreditRatesScreen

fun NavGraphBuilder.billAndCreditDestinations(navController: NavController) {
    composable(
        route = BillInfoDestination.routeTemplate,
        arguments = listOf(navArgument(BillInfoDestination.billIdArg) { type = NavType.StringType }),
    ) {
        BillInfoScreen(
            onNavigateBack = {
                navController.navigateUp()
            },
        )
    }
    composable(CreditAddingDestination.route) {
        CreditAddingScreen()
    }
    composable(
        route = CreditInfoDestination.routeTemplate,
        arguments = listOf(navArgument(CreditInfoDestination.creditIdArg) { type = NavType.StringType }),
    ) {
        CreditInfoScreen(
            onNavigateBack = {
                navController.navigateUp()
            },
        )
    }
    composable(CreditRatesDestination.route) {
        CreditRatesScreen()
    }
}
