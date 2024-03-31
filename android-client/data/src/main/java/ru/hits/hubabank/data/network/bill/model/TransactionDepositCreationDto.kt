package ru.hits.hubabank.data.network.bill.model

import kotlinx.serialization.Serializable

@Serializable
internal data class TransactionDepositCreationDto(
    val billId: String,
    val amount: Long,
)
