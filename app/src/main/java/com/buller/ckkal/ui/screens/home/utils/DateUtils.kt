package com.buller.ckkal.ui.screens.home.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object DateUtils {
    fun formatStringForDate(timestampStr: String): String {
        val cleaned = timestampStr.trim()
        val timestamp = cleaned.toLongOrNull() ?: return "-"
        val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy")
            .withZone(ZoneId.systemDefault())
        return formatter.format(Instant.ofEpochMilli(timestamp))
    }
}