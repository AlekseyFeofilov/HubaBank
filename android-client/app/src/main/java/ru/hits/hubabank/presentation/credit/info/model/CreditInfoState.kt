package ru.hits.hubabank.presentation.credit.info.model

import ru.hits.hubabank.domain.credit.model.Credit
import ru.hits.hubabank.domain.credit.model.CreditPayment
import ru.hits.hubabank.presentation.core.ScreenState

data class CreditInfoState(
    val isLoading: Boolean,
    val credit: Credit?,
    val creditPayments: List<CreditPayment>,
) : ScreenState
