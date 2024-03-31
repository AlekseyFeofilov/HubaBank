package ru.hits.hubabank.data.network.bill.model

import kotlinx.serialization.Serializable
import ru.hits.hubabank.domain.bill.model.Currency

@Serializable
data class CreatingBillDto(
    val currency: Currency
)
