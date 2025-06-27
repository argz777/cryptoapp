package com.argz.cryptoapp

import java.text.DecimalFormat

actual fun formatCurrency(value: Double): String {
    return DecimalFormat("#,###.00").format(value)
}