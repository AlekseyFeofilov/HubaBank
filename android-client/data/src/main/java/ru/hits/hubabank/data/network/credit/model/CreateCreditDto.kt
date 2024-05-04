package ru.hits.hubabank.data.network.credit.model

import kotlinx.serialization.Serializable
import ru.hits.hubabank.domain.credit.model.CreateCredit

@Serializable
internal data class CreateCreditDto(
    val billId: String,
    val principal: Long,
    val completionDate: String,
    val creditTermsId: String,
)

internal fun CreateCredit.toDto(): CreateCreditDto {
    return CreateCreditDto(
        billId = billId,
        principal = principal,
        completionDate = completionDate.toString(),
        creditTermsId = creditTermsId
    )
}
