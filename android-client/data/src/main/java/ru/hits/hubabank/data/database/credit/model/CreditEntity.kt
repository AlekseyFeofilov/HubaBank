package ru.hits.hubabank.data.database.credit.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.hits.hubabank.domain.credit.model.Credit
import java.time.LocalDate

@Entity(tableName = "Credit")
internal data class CreditEntity(
    @PrimaryKey
    val id: String,
    val accountId: String,
    val billId: String,
    val completionDate: String,
    val lastArrearsUpdate: String,
    val interestRate: Float,
    val collectionDay: Int,
    val principal: Long,
    val currentAccountsPayable: Long,
    val arrearsInterest: Long,
    val arrears: Long,
    val fine: Long,
)

internal fun Credit.toEntity(): CreditEntity {
    return CreditEntity(
        id = id,
        accountId = accountId,
        billId = billId,
        completionDate = completionDate.toString(),
        lastArrearsUpdate = lastArrearsUpdate.toString(),
        interestRate = interestRate,
        collectionDay = collectionDay,
        principal = principal,
        currentAccountsPayable = currentAccountsPayable,
        arrearsInterest = arrearsInterest,
        arrears = arrears,
        fine = fine,
    )
}

internal fun CreditEntity.toDomain(): Credit {
    return Credit(
        id = id,
        accountId = accountId,
        billId = billId,
        completionDate = LocalDate.parse(completionDate),
        lastArrearsUpdate = LocalDate.parse(lastArrearsUpdate),
        interestRate = interestRate,
        collectionDay = collectionDay,
        principal = principal,
        currentAccountsPayable = currentAccountsPayable,
        arrearsInterest = arrearsInterest,
        arrears = arrears,
        fine = fine,
    )
}
