package ru.hits.hubabank.presentation.main.model

import ru.hits.hubabank.domain.bill.model.Bill
import ru.hits.hubabank.domain.credit.model.Credit
import ru.hits.hubabank.presentation.core.ScreenState

data class MainState(
    val isLoading: Boolean,
    val isCreatingDialogOpen: Boolean,
    val bills: List<Bill>,
    val credits: List<Credit>,
) : ScreenState
