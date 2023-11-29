package com.example.cryptoapp.network

import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class CustomWebSocketListener: WebSocketListener() {

    var listener: ((String)->Unit)? = null

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        listener!!.invoke(text)
    }
}