package ru.hits.hubabank.data.database.credit.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.hits.hubabank.domain.credit.model.Credit

@Entity(tableName = "Credit")
internal data class CreditEntity(
    @PrimaryKey
    val id: String,
    val sum: Long,
    val paidOut: Long,
)

internal fun Credit.toEntity(): CreditEntity {
    return CreditEntity(
        id = id,
        sum = sum,
        paidOut = paidOut,
    )
}

internal fun CreditEntity.toDomain(): Credit {
    return Credit(
        id = id,
        sum = sum,
        paidOut = paidOut,
    )
}
