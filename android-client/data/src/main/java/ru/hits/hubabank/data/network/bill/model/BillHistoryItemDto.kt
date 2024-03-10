package ru.hits.hubabank.data.network.bill.model

import kotlinx.serialization.Serializable
import ru.hits.hubabank.domain.bill.model.BillChange
import ru.hits.hubabank.domain.bill.model.BillHistoryItem
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Serializable
internal data class BillHistoryItemDto(
    val id: String,
    val billId: String,
    val balanceChange: Long,
    val reason: ReasonDto,
    val instant: String,
)

internal fun BillHistoryItemDto.toDomain(): BillHistoryItem {
    return BillHistoryItem(
        id = id,
        billId = billId,
        changeSum = balanceChange,
        billChange = when {
            reason == ReasonDto.LOAN -> BillChange.CREDIT
            balanceChange < 0 -> BillChange.TRANSFER
            else -> BillChange.REFILL
        },
        dateTime = LocalDateTime.parse(instant, DateTimeFormatter.ISO_DATE_TIME).plusSeconds(
            ZoneId.systemDefault().rules.getOffset(Instant.now()).totalSeconds.toLong()
        )
    )
}
