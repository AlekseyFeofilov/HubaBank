package ru.hits.hubabank.data.network.bill.model

import kotlinx.serialization.Serializable

@Serializable
data class TransactionToBillCreationDto(
    val sourceBillId: String,
    val targetBillId: String,
    val amount: Long,
)
