package ru.hits.hubabank.data.network.bill

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.hits.hubabank.data.network.bill.model.ClientBillDto
import ru.hits.hubabank.data.network.bill.model.CreatingBillDto
import ru.hits.hubabank.data.network.bill.model.TransactionDepositCreationDto
import ru.hits.hubabank.data.network.bill.model.TransactionToBillCreationDto

internal interface BillApi {

    @GET("bills")
    suspend fun getAllBills(): List<ClientBillDto>

    @POST("bills")
    suspend fun createNewBill(@Body creatingBillDto: CreatingBillDto): ClientBillDto

    @GET("bills/{billId}")
    suspend fun getBillById(@Path("billId") billId: String): ClientBillDto

    @POST("bills/{billId}/hidden")
    suspend fun saveHiddenMode(@Path("billId") billId: String, @Body isHidden: Boolean)

    @DELETE("bills/{billId}")
    suspend fun closeBill(@Path("billId") billId: String)


    @POST("transactions/tobill")
    suspend fun transferMoneyToBill(
        @Body transactionToBillCreationDto: TransactionToBillCreationDto,
    )


    @POST("transactions/deposit")
    suspend fun giveMoneyForBill(
        @Body transactionDepositCreationDto: TransactionDepositCreationDto,
    )
}
