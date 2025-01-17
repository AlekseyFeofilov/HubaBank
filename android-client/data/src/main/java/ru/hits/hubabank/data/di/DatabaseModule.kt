package ru.hits.hubabank.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.hits.hubabank.data.database.auth.TokensDao
import ru.hits.hubabank.data.database.bill.BillDao
import ru.hits.hubabank.data.database.core.AppDatabase
import ru.hits.hubabank.data.database.credit.CreditDao
import ru.hits.hubabank.data.database.credit.CreditTermsAndPaymentDao
import ru.hits.hubabank.data.database.user.UserDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(applicationContext, AppDatabase::class.java, DATABASE_NAME)
            .build()
    }

    @Provides
    fun provideTokensDao(appDatabase: AppDatabase): TokensDao = appDatabase.getTokensDao()

    @Provides
    fun provideBillDao(appDatabase: AppDatabase): BillDao = appDatabase.getBillDao()

    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserDao = appDatabase.getUserDao()

    @Provides
    fun provideCreditDao(appDatabase: AppDatabase): CreditDao = appDatabase.getCreditDao()

    @Provides
    fun provideCreditTermsAndPaymentDao(appDatabase: AppDatabase): CreditTermsAndPaymentDao =
        appDatabase.getCreditTermsAndPaymentDao()

    private const val DATABASE_NAME = "app_database"
}
