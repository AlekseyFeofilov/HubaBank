package ru.hits.hubabank.data.network.credit.model

import kotlinx.serialization.Serializable
import ru.hits.hubabank.domain.credit.model.Credit
import java.time.LocalDate

@Serializable
internal data class CreditDto(
    val id: String,
    val accountId: String,
    val billId: String,
    val completionDate: String,
    val lastArrearsUpdate: String,
    val interestRate: Float,
    val collectionDay: Int,
    val principal: Long,
    val currentAccountsPayable: Long,
    val arrearsInterest: Long,
    val arrears: Long,
    val fine: Long,
)

internal fun CreditDto.toDomain(): Credit {
    return Credit(
        id = id,
        accountId = accountId,
        billId = billId,
        completionDate = LocalDate.parse(completionDate),
        lastArrearsUpdate = LocalDate.parse(lastArrearsUpdate),
        interestRate = interestRate,
        collectionDay = collectionDay,
        principal = principal,
        currentAccountsPayable = currentAccountsPayable,
        arrearsInterest = arrearsInterest,
        arrears = arrears,
        fine = fine,
    )
}
