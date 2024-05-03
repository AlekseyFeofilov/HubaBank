package ru.hits.hubabank.presentation.common

import androidx.annotation.StringRes
import ru.hits.hubabank.R
import ru.hits.hubabank.domain.bill.model.NewTransactionType

@StringRes
fun NewTransactionType.getTitleRes(): Int {
    return when (this) {
        NewTransactionType.DEPOSIT -> R.string.bill_screen_refill
        NewTransactionType.WITHDRAWAL -> R.string.bill_screen_withdrawal
        NewTransactionType.TO_BILL -> R.string.bill_screen_transfer
    }
}

@StringRes
fun NewTransactionType.getActionRes(): Int {
    return when (this) {
        NewTransactionType.DEPOSIT -> R.string.bill_screen_do_refill
        NewTransactionType.WITHDRAWAL -> R.string.bill_screen_do_withdrawal
        NewTransactionType.TO_BILL -> R.string.bill_screen_do_transfer
    }
}
