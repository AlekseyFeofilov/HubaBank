package ru.hits.hubabank.data.network.credit

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.hits.hubabank.data.network.credit.model.CreateCreditDto
import ru.hits.hubabank.data.network.credit.model.CreditDto
import ru.hits.hubabank.data.network.credit.model.CreditPaymentDto
import ru.hits.hubabank.data.network.credit.model.CreditTermsDto

internal interface CreditApi {

    @GET("credits/credit/{creditId}")
    suspend fun getCreditById(@Path("creditId") creditId: String): CreditDto

    @GET("credits/credit")
    suspend fun getAllCredit(): List<CreditDto>

    @POST("credits/credit")
    suspend fun createCredit(@Body createCredit: CreateCreditDto)

    @GET("credits/creditTerms")
    suspend fun getAllCreditTerms(): List<CreditTermsDto>

    @GET("credits/payment/{creditId}")
    suspend fun getAllCreditPayment(@Path("creditId") creditId: String): List<CreditPaymentDto>

    @DELETE("credits/credit/{creditId}")
    suspend fun deleteCredit(@Path("creditId") creditId: String)
}
