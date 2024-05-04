package ru.hits.hubabank.data.network.credit.model

import kotlinx.serialization.Serializable
import ru.hits.hubabank.domain.credit.model.CreditTerms

@Serializable
internal data class CreditTermsDto(
    val id: String,
    val interestRate: Float,
    val title: String?,
    val isDeleted: Boolean,
)

internal fun CreditTermsDto.toDomain(): CreditTerms {
    return CreditTerms(
        id = id,
        interestRate = interestRate,
        title = title,
    )
}
