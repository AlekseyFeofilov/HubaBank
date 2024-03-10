package ru.hits.hubabank.domain.credit.model

data class Credit(
    val id: String,
    val sum: Long,
    val paidOut: Long,
)
