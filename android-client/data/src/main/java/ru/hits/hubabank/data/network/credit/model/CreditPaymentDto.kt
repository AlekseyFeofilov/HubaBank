package ru.hits.hubabank.data.network.credit.model

import kotlinx.serialization.Serializable
import ru.hits.hubabank.domain.credit.model.CreditPayment
import ru.hits.hubabank.domain.credit.model.PaymentStatus
import java.time.LocalDate

@Serializable
internal data class CreditPaymentDto(
    val id: String,
    val creditId: String,
    val paymentStatus: PaymentStatus,
    val paymentDay: String,
    val paymentAmount: Long,
    val interest: Long,
    val arrears: Long,
    val arrearsInterest: Long,
)

internal fun CreditPaymentDto.toDomain(): CreditPayment {
    return CreditPayment(
        id = id,
        creditId = creditId,
        paymentStatus = paymentStatus,
        paymentDay = LocalDate.parse(paymentDay),
        paymentAmount = paymentAmount,
        interest = interest,
        arrears = arrears,
        arrearsInterest = arrearsInterest
    )
}
