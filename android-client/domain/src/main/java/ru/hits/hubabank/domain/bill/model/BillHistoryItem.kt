package ru.hits.hubabank.domain.bill.model

import java.time.LocalDateTime

data class BillHistoryItem(
    val id: String,
    val billId: String,
    val changeSum: Long,
    val currency: Currency,
    val billChangeReason: BillChangeReason,
    val dateTime: LocalDateTime,
)
