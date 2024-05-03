package ru.hits.hubabank

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber

class FirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        Timber.w(
            "onMessageReceived: ${message.notification?.title} ${message.notification?.body}",
        )
        super.onMessageReceived(message)
    }

    override fun onNewToken(token: String) {
        Timber.w("NewToken: $token")

        super.onNewToken(token)
    }
}
