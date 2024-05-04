package ru.hits.hubabank.presentation.credit.adding.model

import ru.hits.hubabank.domain.bill.model.Bill
import ru.hits.hubabank.domain.credit.model.CreditTerms
import ru.hits.hubabank.presentation.core.ScreenState
import java.time.LocalDate

data class CreditAddingState(
    val isLoading: Boolean,
    val allCreditTerms: List<CreditTerms>,
    val isCreditTermsMenuOpen: Boolean,
    val selectedTerms: CreditTerms?,
    val allBills: List<Bill>,
    val isBillsMenuOpen: Boolean,
    val selectedBill: Bill?,
    val creditSum: String,
    val creditDate: LocalDate,
    val isDateDialogOpen: Boolean,
) : ScreenState
