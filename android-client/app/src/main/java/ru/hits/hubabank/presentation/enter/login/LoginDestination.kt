package ru.hits.hubabank.presentation.enter.login

object LoginDestination {
    const val route = "Login"
    const val tokenSSO = "token"
    const val routeTemplate = "$route?$tokenSSO={$tokenSSO}"
}