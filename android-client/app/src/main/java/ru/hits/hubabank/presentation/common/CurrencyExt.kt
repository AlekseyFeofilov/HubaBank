package ru.hits.hubabank.presentation.common

import ru.hits.hubabank.domain.bill.model.Currency

fun Currency.getSymbol(): String {
    return when (this) {
        Currency.RUB -> "₽"
        Currency.USD -> "$"
        Currency.EUR -> "€"
        Currency.JPY -> "¥"
        Currency.CNY -> "¥"
    }
}
