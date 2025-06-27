package com.argz.cryptoapp

import platform.Foundation.NSLocale
import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterDecimalStyle
import platform.Foundation.currentLocale

actual fun formatCurrency(value: Double): String {
    val formatter = NSNumberFormatter()
    formatter.locale = NSLocale.currentLocale
    formatter.numberStyle = NSNumberFormatterDecimalStyle
    formatter.minimumFractionDigits = 0u
    formatter.maximumFractionDigits = 2u
    return formatter.stringFromNumber(NSNumber(value))!!
}