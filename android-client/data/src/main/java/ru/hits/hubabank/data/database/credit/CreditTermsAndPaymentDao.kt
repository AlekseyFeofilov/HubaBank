package ru.hits.hubabank.data.database.credit

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.hits.hubabank.data.database.credit.model.CreditPaymentEntity
import ru.hits.hubabank.data.database.credit.model.CreditTermsEntity

@Dao
internal interface CreditTermsAndPaymentDao {

    @Upsert
    suspend fun insertCreditTerms(creditTerms: List<CreditTermsEntity>)

    @Upsert
    suspend fun insertCreditPayments(creditPayments: List<CreditPaymentEntity>)

    @Query("SELECT * FROM CreditTerms")
    fun observeAllCreditsTerms(): Flow<List<CreditTermsEntity>>

    @Query("DELETE FROM CreditTerms WHERE id IN (:creditTermsIds)")
    suspend fun deleteCreditTerms(creditTermsIds: List<String>)

    @Query("SELECT * FROM CreditPayment WHERE creditId = :creditId")
    fun observeCreditPayments(creditId: String): Flow<List<CreditPaymentEntity>>

    @Query("DELETE FROM CreditPayment WHERE creditId = :creditId AND id IN (:creditPaymentsIds)")
    suspend fun deleteCreditPayments(creditId: String, creditPaymentsIds: List<String>)

    @Query("DELETE FROM CreditTerms")
    suspend fun deleteAllCreditTerms()

    @Query("DELETE FROM CreditPayment")
    suspend fun deleteAllCreditPayments()
}
