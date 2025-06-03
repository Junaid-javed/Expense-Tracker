package com.example.expensetracker.features.expense

data class ExpenseSummary(
    val type: String,
    val date: String,
    val total_amount: Double
)