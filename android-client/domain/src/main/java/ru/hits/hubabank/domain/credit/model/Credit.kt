package ru.hits.hubabank.domain.credit.model

import java.time.LocalDate

data class Credit(
    val id: String,
    val accountId: String,
    val billId: String,
    val completionDate: LocalDate,
    val lastArrearsUpdate: LocalDate,
    val interestRate: Float,
    val collectionDay: Int,
    val principal: Long,
    val currentAccountsPayable: Long,
    val arrearsInterest: Long,
    val arrears: Long,
    val fine: Long,
)
