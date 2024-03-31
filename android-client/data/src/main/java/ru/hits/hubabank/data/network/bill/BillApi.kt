package ru.hits.hubabank.data.network.bill

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.hits.hubabank.data.network.bill.model.ClientBillDto
import ru.hits.hubabank.data.network.bill.model.TransactionCreationDto

internal interface BillApi {

    @GET("bills")
    suspend fun getAllBills(): List<ClientBillDto>

    @POST("bills")
    suspend fun createNewBill(): ClientBillDto

    @GET("bills/{billId}")
    suspend fun getBillById(@Path("billId") billId: String): ClientBillDto

    @POST("bills/{billId}/hidden")
    suspend fun saveHiddenMode(@Path("billId") billId: String, @Body isHidden: Boolean)

    @DELETE("bills/{billId}")
    suspend fun closeBill(@Path("billId") billId: String)

    @POST("bills/{billId}/transactions")
    suspend fun updateBillBalance(
        @Path("billId") billId: String,
        @Body transactionCreationDto: TransactionCreationDto,
    )
}
