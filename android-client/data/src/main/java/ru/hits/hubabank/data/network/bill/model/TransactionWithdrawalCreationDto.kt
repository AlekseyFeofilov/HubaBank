package ru.hits.hubabank.data.network.bill.model

import kotlinx.serialization.Serializable

@Serializable
internal data class TransactionWithdrawalCreationDto(
    val billId: String,
    val amount: Long,
)
