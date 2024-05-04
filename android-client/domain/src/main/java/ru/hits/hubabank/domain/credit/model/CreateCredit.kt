package ru.hits.hubabank.domain.credit.model

import java.time.LocalDate

data class CreateCredit(
    val billId: String,
    val principal: Long,
    val completionDate: LocalDate,
    val creditTermsId: String,
)
