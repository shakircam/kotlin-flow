package com.shakircam.flowtest

import android.util.Log
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessagingService

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.d("tag","fcm token1:$token")
    }


}