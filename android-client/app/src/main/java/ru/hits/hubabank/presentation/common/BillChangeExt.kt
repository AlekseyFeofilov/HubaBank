package ru.hits.hubabank.presentation.common

import androidx.annotation.StringRes
import ru.hits.hubabank.R
import ru.hits.hubabank.domain.bill.model.BillChange

@StringRes
fun BillChange.getTitleRes(): Int {
    return when (this) {
        BillChange.USER -> R.string.bill_screen_refill
        BillChange.TRANSFER -> R.string.bill_screen_transfer
        BillChange.LOAN -> R.string.bill_screen_credit
        BillChange.TERMINAL -> R.string.bill_screen_refill
    }
}

@StringRes
fun BillChange.getActionRes(): Int {
    return when (this) {
        BillChange.USER -> R.string.bill_screen_do_refill
        else -> R.string.bill_screen_do_transfer
    }
}

fun BillChange.getSign(): Char {
    return when (this) {
        BillChange.USER -> '+'
        BillChange.TERMINAL -> '+'
        BillChange.TRANSFER -> '-'
        BillChange.LOAN -> '-'
    }
}
