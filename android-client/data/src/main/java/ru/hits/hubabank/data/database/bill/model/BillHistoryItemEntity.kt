package ru.hits.hubabank.data.database.bill.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.hits.hubabank.domain.bill.model.BillChange
import ru.hits.hubabank.domain.bill.model.BillHistoryItem
import java.time.LocalDateTime

@Entity(tableName = "BillHistoryItem")
internal data class BillHistoryItemEntity(
    @PrimaryKey
    val id: String,
    val billId: String,
    val changeSum: Long,
    val billChange: BillChange,
    val dateTime: String,
)

internal fun BillHistoryItem.toEntity(): BillHistoryItemEntity {
    return BillHistoryItemEntity(
        id = id,
        billId = billId,
        changeSum = changeSum,
        billChange = billChange,
        dateTime = dateTime.toString(),
    )
}

internal fun BillHistoryItemEntity.toDomain(): BillHistoryItem {
    return BillHistoryItem(
        id = id,
        billId = billId,
        changeSum = changeSum,
        billChange = billChange,
        dateTime = LocalDateTime.parse(dateTime),
    )
}
