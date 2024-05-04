package ru.hits.hubabank.domain.credit.model

import java.time.LocalDate

data class CreditPayment(
    val id: String,
    val creditId: String,
    val paymentStatus: PaymentStatus,
    val paymentDay: LocalDate,
    val paymentAmount: Long,
    val interest: Long,
    val arrears: Long,
    val arrearsInterest: Long,
)
