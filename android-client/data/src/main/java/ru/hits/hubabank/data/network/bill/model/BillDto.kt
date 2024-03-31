package ru.hits.hubabank.data.network.bill.model

import kotlinx.serialization.Serializable
import ru.hits.hubabank.domain.bill.model.Bill
import ru.hits.hubabank.domain.bill.model.Currency

@Serializable
internal data class BillDto(
    val id: String,
    val userId: String,
    val balance: Long,
    val currency: Currency,
    val isHidden: Boolean,
)

internal fun BillDto.toDomain(): Bill {
    return Bill(
        id = id,
        balance = balance,
        currency = currency,
        isHidden = isHidden,
    )
}
