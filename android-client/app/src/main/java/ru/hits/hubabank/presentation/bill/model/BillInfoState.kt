package ru.hits.hubabank.presentation.bill.model

import ru.hits.hubabank.domain.bill.model.Bill
import ru.hits.hubabank.domain.bill.model.BillHistoryItem
import ru.hits.hubabank.domain.bill.model.NewTransactionType
import ru.hits.hubabank.presentation.core.ScreenState
import java.time.LocalDate

data class BillInfoState(
    val isLoading: Boolean,
    val bill: Bill?,
    val billHistory: Map<LocalDate, List<BillHistoryItem>>,
    val today: LocalDate,
    val isChangeBillDialogOpen: Boolean,
    val howChange: NewTransactionType,
    val targetBill: String,
    val changeSum: String,
) : ScreenState
