package ru.hits.hubabank.domain.bill.model

data class ChangeBillBalanceModel(
    val changeReason: BillChangeReason,
    val billId: String,
    val balanceChange: Long,
    val targetBill: String,
)
