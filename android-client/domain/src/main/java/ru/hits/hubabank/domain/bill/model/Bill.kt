package ru.hits.hubabank.domain.bill.model

data class Bill(
    val id: String,
    val balance: Long,
    val currency: Currency,
    val isHidden: Boolean,
)
