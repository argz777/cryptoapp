package com.example.cryptoapp.utils

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import com.example.cryptoapp.model.PortfolioCoin
import kotlin.math.roundToInt

class Constants {
    companion object {
        val BASE_URL = "https://api.coincap.io/"
        val WEB_SOCKET_BASE_URL = "wss://ws.coincap.io/prices?assets=bitcoin,ethereum,monero,litecoin,ripple,binance-coin,tether,dogecoin,tron,usd-coin"
    }
}

fun getFraction(
    portfolioCoins: SnapshotStateList<PortfolioCoin>,
    currentPortfolioCoin: PortfolioCoin,
) : Float {
    var total = 0.0
    portfolioCoins.forEach {
        total += it.quantity * it.coin!!.priceUsd!!.toDouble()
    }

    val fraction = currentPortfolioCoin.quantity * currentPortfolioCoin.coin!!.priceUsd!!.toDouble() / total * 100
    return fraction.roundToInt().toFloat()
}

fun getRandomLong() : Long {
    val numbers = listOf(
        0xFF5F0A87,
        0xFFF44336,
        0xFF9C27B0,
        0xFF3F51B5,
        0xFF2196F3,
        0xFF4CAF50,
        0xFFFFEB3B,
        0xFFFF9800,
        0xFFB71C1C,
        0xFF1B5E20,
        0xFF006064,
        0xFF1A237E,
        0xFF827717,
        0xFF004D40,
        0xFFB2FF59,
        0xFF536DFE,
        0xFF64FFDA,
        0xFFE040FB,
        0xFFFF5252,
        0xFF536DFE
    )
    return numbers[(numbers.indices).random()]
}

fun getRandomColor() : Color {
    val colors = listOf(
        Color(0xFF5F0A87),
        Color(0xFFF44336),
        Color(0xFF9C27B0),
        Color(0xFF3F51B5),
        Color(0xFF2196F3),
        Color(0xFF4CAF50),
        Color(0xFFFFEB3B),
        Color(0xFFFF9800),
        Color(0xFFB71C1C),
        Color(0xFF1B5E20),
        Color(0xFF006064),
        Color(0xFF1A237E),
        Color(0xFF827717),
        Color(0xFF004D40),
        Color(0xFFB2FF59),
        Color(0xFF536DFE),
        Color(0xFF64FFDA),
        Color(0xFFE040FB),
        Color(0xFFFF5252),
        Color(0xFF536DFE)
    )
    return colors[(colors.indices).random()]
}