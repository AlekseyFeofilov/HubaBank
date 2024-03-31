package ru.hits.hubabank.presentation.common

import androidx.annotation.StringRes
import ru.hits.hubabank.R
import ru.hits.hubabank.domain.bill.model.BillChangeReason

@StringRes
fun BillChangeReason.getTitleRes(sum: Long): Int {
    return when (this) {
        BillChangeReason.USER -> R.string.bill_screen_transfer
        BillChangeReason.TRANSFER -> R.string.bill_screen_transfer
        BillChangeReason.LOAN -> R.string.bill_screen_credit
        BillChangeReason.TERMINAL -> if (sum < 0) R.string.bill_screen_withdrawal else R.string.bill_screen_refill
    }
}

@StringRes
fun BillChangeReason.getActionRes(): Int {
    return when (this) {
        BillChangeReason.TERMINAL -> R.string.bill_screen_do_refill
        else -> R.string.bill_screen_do_transfer
    }
}
