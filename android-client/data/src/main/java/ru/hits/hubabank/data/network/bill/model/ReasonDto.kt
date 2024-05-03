package ru.hits.hubabank.data.network.bill.model

import kotlinx.serialization.Serializable

@Serializable
enum class ReasonDto {
    TERMINAL,
    LOAN,
    USER,
}
