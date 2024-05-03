package ru.hits.hubabank.data.network.bill

import android.util.Log
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.logging.HttpLoggingInterceptor
import ru.hits.hubabank.data.network.bill.model.CreatingBillDto
import ru.hits.hubabank.data.network.bill.model.TransactionDepositCreationDto
import ru.hits.hubabank.data.network.bill.model.TransactionToBillCreationDto
import ru.hits.hubabank.data.network.bill.model.TransactionWithdrawalCreationDto
import ru.hits.hubabank.data.network.bill.model.toDomain
import ru.hits.hubabank.data.network.core.AuthInterceptor
import ru.hits.hubabank.data.network.core.TokenAuthenticator
import ru.hits.hubabank.domain.bill.BillRemoteDataSource
import ru.hits.hubabank.domain.bill.model.Bill
import ru.hits.hubabank.domain.bill.model.BillHistoryItem
import ru.hits.hubabank.domain.bill.model.Currency
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class BillRemoteDataSourceImpl @Inject constructor(
    private val billApi: BillApi,
    private val authInterceptor: AuthInterceptor,
    private val authenticator: TokenAuthenticator,
) : BillRemoteDataSource {

    private lateinit var socket: WebSocket

    override suspend fun createNewBill(currency: Currency): Bill {
        return billApi.createNewBill(CreatingBillDto(currency)).toDomain()
    }

    override suspend fun getAllBills(): List<Bill> {
        return billApi.getAllBills().map { it.toDomain() }
    }

    override suspend fun getBillById(billId: String): Bill {
        return billApi.getBillById(billId).toDomain()
    }

    override suspend fun saveHiddenMode(billId: String, isHidden: Boolean) {
        billApi.saveHiddenMode(billId, isHidden)
    }

    override suspend fun giveMoneyForBill(billId: String, balanceChange: Long) {
        billApi.giveMoneyForBill(
            TransactionDepositCreationDto(billId = billId, amount = balanceChange)
        )
    }

    override suspend fun withdrawMoneyFromBill(billId: String, balanceChange: Long) {
        billApi.withdrawMoneyFromBill(
            TransactionWithdrawalCreationDto(billId = billId, amount = balanceChange)
        )
    }

    override suspend fun transferMoneyToBill(
        sourceBillId: String,
        balanceChange: Long,
        targetBillId: String,
    ) {
        billApi.transferMoneyToBill(
            TransactionToBillCreationDto(
                sourceBillId = sourceBillId,
                targetBillId = targetBillId,
                amount = balanceChange,
            )
        )
    }

    override suspend fun closeBill(billId: String) {
        billApi.closeBill(billId)
    }

    override fun startObserveBillHistory(billId: String): Flow<BillHistoryItem> {
        val client = OkHttpClient.Builder().apply {
            val logLevel = HttpLoggingInterceptor.Level.BODY
            addInterceptor(HttpLoggingInterceptor().setLevel(logLevel))
            addInterceptor(authInterceptor)
            authenticator(authenticator)
        }.build()

        val request = Request.Builder()
            .url("$TRANSACTION_WEB_SOCKET_URL$billId")
            .build()
        val socketListener = TransactionWebSocketListener()

        socket = client.newWebSocket(request, socketListener)

        client.dispatcher.executorService.shutdown()

        return socketListener.messageFlow
    }

    override fun endObserveBillHistory() {
        socket.close(1000, "")
        Log.e("close", "my close")
    }

    private companion object {
        const val TRANSACTION_WEB_SOCKET_URL = "ws://194.147.90.192:9001/ws/bills/"
    }
}
