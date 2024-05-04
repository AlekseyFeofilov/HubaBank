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
import ru.hits.hubabank.presentation.main.MainDestination

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
        CreditAddingScreen(
            onNavigateBack = {
                navController.navigateUp()
            },
            onOpenCreditInfoScreen = { creditId ->
                navController.navigate(CreditInfoDestination.routeWithArg(creditId)) {
                    popUpTo(MainDestination.route)
                }
            },
        )
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
}
