package com.example.cryptoapp.utils

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import com.example.cryptoapp.model.PortfolioCoin
import kotlin.math.roundToInt

class Constants {
    companion object {
        val BASE_URL = "https://api.coincap.io/"
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