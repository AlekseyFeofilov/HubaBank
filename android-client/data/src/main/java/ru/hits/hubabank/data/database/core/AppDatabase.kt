package ru.hits.hubabank.data.database.core

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.hits.hubabank.data.database.auth.TokensDao
import ru.hits.hubabank.data.database.auth.model.TokensEntity
import ru.hits.hubabank.data.database.bill.BillDao
import ru.hits.hubabank.data.database.bill.model.BillEntity
import ru.hits.hubabank.data.database.credit.CreditDao
import ru.hits.hubabank.data.database.credit.CreditTermsAndPaymentDao
import ru.hits.hubabank.data.database.credit.model.CreditEntity
import ru.hits.hubabank.data.database.credit.model.CreditPaymentEntity
import ru.hits.hubabank.data.database.credit.model.CreditTermsEntity
import ru.hits.hubabank.data.database.user.UserDao
import ru.hits.hubabank.data.database.user.model.ProfileEntity

@Database(
    entities = [
        TokensEntity::class,
        BillEntity::class,
        ProfileEntity::class,
        CreditEntity::class,
        CreditTermsEntity::class,
        CreditPaymentEntity::class,
    ],
    version = 1,
)
internal abstract class AppDatabase : RoomDatabase() {

    abstract fun getTokensDao(): TokensDao

    abstract fun getBillDao(): BillDao

    abstract fun getUserDao(): UserDao

    abstract fun getCreditDao(): CreditDao

    abstract fun getCreditTermsAndPaymentDao(): CreditTermsAndPaymentDao
}
