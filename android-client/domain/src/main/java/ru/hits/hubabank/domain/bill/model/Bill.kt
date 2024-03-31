package ru.hits.hubabank.domain.bill.model

data class Bill(
    val id: String,
    val balance: Long,
    val isHidden: Boolean,
)
