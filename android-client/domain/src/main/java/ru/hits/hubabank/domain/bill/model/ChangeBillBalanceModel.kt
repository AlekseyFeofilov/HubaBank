package ru.hits.hubabank.domain.bill.model

data class ChangeBillBalanceModel(
    val billId: String,
    val balanceChange: Long,
)
