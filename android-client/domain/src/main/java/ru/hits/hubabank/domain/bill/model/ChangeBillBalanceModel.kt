package ru.hits.hubabank.domain.bill.model

data class ChangeBillBalanceModel(
    val newTransactionType: NewTransactionType,
    val billId: String,
    val balanceChange: Long,
    val targetBill: String,
)
