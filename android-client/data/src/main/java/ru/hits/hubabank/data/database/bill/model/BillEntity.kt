package ru.hits.hubabank.data.database.bill.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.hits.hubabank.domain.bill.model.Bill

@Entity(tableName = "Bill")
internal data class BillEntity(
    @PrimaryKey
    val id: String,
    val balance: Long,
)

internal fun Bill.toEntity(): BillEntity {
    return BillEntity(
        id = id,
        balance = balance,
    )
}

internal fun BillEntity.toDomain(): Bill {
    return Bill(
        id = id,
        balance = balance,
    )
}