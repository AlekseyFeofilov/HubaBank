package ru.hits.hubabank.presentation.bill

object BillInfoDestination {
    const val billIdArg = "billId"
    private const val route = "BillInfo"
    const val routeTemplate = "$route/{$billIdArg}"

    fun routeWithArg(billId: String): String {
        return "$route/$billId"
    }

}
