package ru.hits.hubabank.data.database.core

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.hits.hubabank.data.database.auth.TokensDao
import ru.hits.hubabank.data.database.auth.model.TokensEntity
import ru.hits.hubabank.data.database.bill.BillDao
import ru.hits.hubabank.data.database.bill.BillHistoryDao
import ru.hits.hubabank.data.database.bill.model.BillEntity
import ru.hits.hubabank.data.database.bill.model.BillHistoryItemEntity
import ru.hits.hubabank.data.database.user.UserDao
import ru.hits.hubabank.data.database.user.model.ProfileEntity

@Database(
    entities = [TokensEntity::class, BillEntity::class, BillHistoryItemEntity::class, ProfileEntity::class],
    version = 1,
)
internal abstract class AppDatabase : RoomDatabase() {

    abstract fun getTokensDao(): TokensDao

    abstract fun getBillDao(): BillDao

    abstract fun getBillHistoryDao(): BillHistoryDao

    abstract fun getUserDao(): UserDao
}
