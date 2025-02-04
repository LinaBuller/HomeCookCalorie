package com.buller.ckkal.domain.utils

object StringUtil {
    fun filterToNumeric(input: String): String {
        val filteredValue = input.filter { char -> char.isDigit() || char == '.' || char == ',' }
        val cleanNumberString = filteredValue.replace(",", ".")
        val dotCount = cleanNumberString.count { char -> char == '.' }
        if (dotCount > 1) {
            return "0.0"
        }
        return cleanNumberString
    }
}