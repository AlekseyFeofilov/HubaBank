package ru.hits.hubabank.presentation.common

import androidx.annotation.StringRes
import ru.hits.hubabank.R
import ru.hits.hubabank.domain.bill.model.BillChange

@StringRes
fun BillChange.getTitleRes(): Int {
    return when (this) {
        BillChange.REFILL -> R.string.bill_screen_refill
        BillChange.TRANSFER -> R.string.bill_screen_transfer
        BillChange.CREDIT -> R.string.bill_screen_credit
    }
}

@StringRes
fun BillChange.getActionRes(): Int {
    return when (this) {
        BillChange.REFILL -> R.string.bill_screen_do_refill
        else -> R.string.bill_screen_do_transfer
    }
}

fun BillChange.getSign(): Char {
    return when (this) {
        BillChange.REFILL -> '+'
        BillChange.TRANSFER -> '-'
        BillChange.CREDIT -> '-'
    }
}
