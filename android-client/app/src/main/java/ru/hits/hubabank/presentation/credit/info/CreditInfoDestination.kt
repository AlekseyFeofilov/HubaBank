package ru.hits.hubabank.presentation.credit.info

object CreditInfoDestination {
    const val creditIdArg = "creditId"
    private const val route = "CreditInfoD"
    const val routeTemplate = "$route/{$creditIdArg}"

    fun routeWithArg(creditId: String): String {
        return "$route/$creditId"
    }
}
