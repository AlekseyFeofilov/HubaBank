package ru.hits.hubabank.data.database.credit.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.hits.hubabank.domain.credit.model.CreditPayment
import ru.hits.hubabank.domain.credit.model.PaymentStatus
import java.time.LocalDate

@Entity(tableName = "CreditPayment")
internal data class CreditPaymentEntity(
    @PrimaryKey
    val id: String,
    val creditId: String,
    val paymentStatus: PaymentStatus,
    val paymentDay: String,
    val paymentAmount: Long,
    val interest: Long,
    val arrears: Long,
    val arrearsInterest: Long,
)

internal fun CreditPayment.toEntity(): CreditPaymentEntity {
    return CreditPaymentEntity(
        id = id,
        creditId = creditId,
        paymentStatus = paymentStatus,
        paymentDay = paymentDay.toString(),
        paymentAmount = paymentAmount,
        interest = interest,
        arrears = arrears,
        arrearsInterest = arrearsInterest,
    )
}

internal fun CreditPaymentEntity.toDomain(): CreditPayment {
    return CreditPayment(
        id = id,
        creditId = creditId,
        paymentStatus = paymentStatus,
        paymentDay = LocalDate.parse(paymentDay),
        paymentAmount = paymentAmount,
        interest = interest,
        arrears = arrears,
        arrearsInterest = arrearsInterest,
    )
}
