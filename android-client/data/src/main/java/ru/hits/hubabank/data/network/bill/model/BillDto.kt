package ru.hits.hubabank.data.network.bill.model

import kotlinx.serialization.Serializable
import ru.hits.hubabank.domain.bill.model.Bill

@Serializable
internal data class BillDto(
    val id: String,
    val userId: String,
    val balance: Long,
    val isHidden: Boolean,
)

internal fun BillDto.toDomain(): Bill {
    return Bill(
        id = id,
        balance = balance,
        isHidden = isHidden,
    )
}
