package ru.hits.hubabank.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.hits.hubabank.data.database.auth.AuthLocalDataSourceImpl
import ru.hits.hubabank.data.database.bill.BillLocalDataSourceImpl
import ru.hits.hubabank.data.database.credit.CreditLocalDataSourceImpl
import ru.hits.hubabank.data.database.user.UserLocalDataSourceImpl
import ru.hits.hubabank.data.network.auth.AuthRemoteDataSourceImpl
import ru.hits.hubabank.data.network.bill.BillRemoteDataSourceImpl
import ru.hits.hubabank.data.network.credit.CreditRemoteDataSourceImpl
import ru.hits.hubabank.data.network.user.UserRemoteDataSourceImpl
import ru.hits.hubabank.domain.auth.AuthLocalDataSource
import ru.hits.hubabank.domain.auth.AuthRemoteDataSource
import ru.hits.hubabank.domain.bill.BillLocalDataSource
import ru.hits.hubabank.domain.bill.BillRemoteDataSource
import ru.hits.hubabank.domain.credit.CreditLocalDataSource
import ru.hits.hubabank.domain.credit.CreditRemoteDataSource
import ru.hits.hubabank.domain.user.UserLocalDataSource
import ru.hits.hubabank.domain.user.UserRemoteDataSource

@Module
@InstallIn(SingletonComponent::class)
internal interface DataSourceModule {

    @Binds
    fun bindAuthLocalDataSource(authLocalDataSourceImpl: AuthLocalDataSourceImpl): AuthLocalDataSource

    @Binds
    fun bindAuthRemoteDataSource(authRemoteDataSourceImpl: AuthRemoteDataSourceImpl): AuthRemoteDataSource

    @Binds
    fun bindBillLocalDataSource(billLocalDataSourceImpl: BillLocalDataSourceImpl): BillLocalDataSource

    @Binds
    fun bindBillRemoteDataSource(billRemoteDataSourceImpl: BillRemoteDataSourceImpl): BillRemoteDataSource

    @Binds
    fun bindUserLocalDataSource(userLocalDataSourceImpl: UserLocalDataSourceImpl): UserLocalDataSource

    @Binds
    fun bindUserRemoteDataSource(userRemoteDataSourceImpl: UserRemoteDataSourceImpl): UserRemoteDataSource

    @Binds
    fun bindCreditLocalDataSource(creditLocalDataSourceImpl: CreditLocalDataSourceImpl): CreditLocalDataSource

    @Binds
    fun bindCreditRemoteDataSource(creditRemoteDataSourceImpl: CreditRemoteDataSourceImpl): CreditRemoteDataSource
}
