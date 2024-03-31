package ru.hits.hubabank.data.network.bill.model

import kotlinx.serialization.Serializable
import ru.hits.hubabank.domain.bill.model.Bill

@Serializable
internal data class ClientBillDto(
    val id: String,
    val balance: Long,
    val isHidden: Boolean,
)

internal fun ClientBillDto.toDomain(): Bill {
    return Bill(
        id = id,
        balance = balance,
        isHidden = isHidden,
    )
}
