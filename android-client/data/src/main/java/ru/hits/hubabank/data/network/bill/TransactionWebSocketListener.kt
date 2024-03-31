package ru.hits.hubabank.data.network.bill

import android.os.Handler
import android.os.Looper
import android.util.Log
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.serialization.json.Json
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import ru.hits.hubabank.data.network.bill.model.BillHistoryItemDto
import ru.hits.hubabank.data.network.bill.model.toDomain
import ru.hits.hubabank.domain.bill.model.BillHistoryItem

class TransactionWebSocketListener : WebSocketListener() {

    private val _message = Channel<BillHistoryItem>(Channel.BUFFERED)
    val messageFlow: Flow<BillHistoryItem> = _message.receiveAsFlow()

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        Log.w("setConnection", "==  I'm connect!!!  ==")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        Log.w("TransactionWebSocketListener", text)
        Handler(Looper.getMainLooper()).post {
            val dto: BillHistoryItemDto = Json.decodeFromString(text)
            _message.trySend(dto.toDomain())
        }
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        Log.w("TransactionWebSocketListener", "close $reason")
        webSocket.close(code, reason)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        Log.w("TransactionWebSocketListener", "WebSocket onFailure: ${t.message}")
        super.onFailure(webSocket, t, response)
    }
}
