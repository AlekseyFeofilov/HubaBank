package ru.hits.hubabank.data.database.credit.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.hits.hubabank.domain.credit.model.CreditTerms

@Entity(tableName = "CreditTerms")
internal data class CreditTermsEntity(
    @PrimaryKey
    val id: String,
    val interestRate: Float,
    val title: String?,
)

internal fun CreditTerms.toEntity(): CreditTermsEntity {
    return CreditTermsEntity(
        id = id,
        interestRate = interestRate,
        title = title,
    )
}

internal fun CreditTermsEntity.toDomain(): CreditTerms {
    return CreditTerms(
        id = id,
        interestRate = interestRate,
        title = title,
    )
}
