package ru.hits.hubabank.domain.credit.model

data class CreditTerms(
    val id: String,
    val interestRate: Float,
    val title: String?,
)
