package ru.hits.hubabank.presentation.common

import androidx.annotation.StringRes
import ru.hits.hubabank.R
import ru.hits.hubabank.domain.credit.model.PaymentStatus

@StringRes
internal fun PaymentStatus.getTitleRes(): Int {
    return when (this) {
        PaymentStatus.Paid -> R.string.payment_status_paid
        PaymentStatus.PaidLate -> R.string.payment_status_paid_late
        PaymentStatus.Overdue -> R.string.payment_status_overdue
        PaymentStatus.Scheduled -> R.string.payment_status_scheduled
    }
}
