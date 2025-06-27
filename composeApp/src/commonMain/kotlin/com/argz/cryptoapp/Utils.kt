package com.argz.cryptoapp

import com.argz.cryptoapp.domain.Transaction
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

expect fun formatCurrency(value: Double): String

fun getCurrentDate(): String {
    val now: Instant = Clock.System.now()
    val today: LocalDate = now.toLocalDateTime(TimeZone.currentSystemDefault()).date
    return "${today.month.name.lowercase().replaceFirstChar(Char::titlecase).substring(0,3)} ${today.dayOfMonth}, ${today.year}"
}

fun computeTotalQuantity(transactions: List<Transaction>): Double {
    var quantity = 0.0

    transactions.forEach {
        if(it.action == "Buy") quantity += it.quantity!!.toDouble()
        else quantity -= it.quantity!!.toDouble()
    }

    return quantity
}

fun computeAveragePrice(transactions: List<Transaction>): Double {
    var value = 0.0
    var quantity = 0.0

    transactions.forEach {
        if(it.action == "Buy") {
            value += it.quantity!!.toDouble() * it.price!!.toDouble()
            quantity += it.quantity!!.toDouble()
        }
        else {
            value -= it.quantity!!.toDouble() * it.price!!.toDouble()
            quantity -= it.quantity!!.toDouble()
        }
    }

    return value / quantity
}


fun computeTotalValue(transactions: List<Transaction>): Double {
    var value = 0.0

    transactions.forEach {
        if(it.action == "Buy") value += it.quantity!!.toDouble() * it.price!!.toDouble()
        else value -= it.quantity!!.toDouble() * it.price!!.toDouble()
    }

    return value
}