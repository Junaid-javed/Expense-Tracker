package com.example.expensetracker.utils

import java.text.SimpleDateFormat
import java.util.Locale

object Utils {

    fun dateFormatter(dateInMills: Long): String {
        val date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return date.format(dateInMills)
    }


    fun dateFormatterForChart(dateInMills: Long): String {
        val date = SimpleDateFormat("dd-MMM", Locale.getDefault())
        return date.format(dateInMills)
    }

    fun formatToDecimal(value: Double): String {
        return String.format("%.2f", value)
    }

    fun getMillisecondFromDate(date: String): Long {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val parsedDate = dateFormat.parse(date)
        return parsedDate?.time ?: 0L
    }
}


